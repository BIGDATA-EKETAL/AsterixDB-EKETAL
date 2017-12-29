/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.asterix.transaction.management.service.locking;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ResourceRecordManager {

    public static final boolean CHECK_SLOTS = false;
    public static final boolean TRACK_ALLOC_LOC = false;

    static final int NO_SLOTS = 1000;

    public static int ITEM_SIZE = 56;
    public static int LAST_HOLDER_OFF = 0; // size: 8
    public static int FIRST_WAITER_OFF = 8; // size: 8
    public static int FIRST_UPGRADER_OFF = 16; // size: 8
    public static int NEXT_OFF = 24; // size: 8
    public static int NEXT_FREE_SLOT_OFF = 32; // size: 4
    public static int MAX_MODE_OFF = 36; // size: 4
    public static int DATASET_ID_OFF = 40; // size: 4
    public static int PK_HASH_VAL_OFF = 44; // size: 4
    public static int ALLOC_ID_OFF = 48; // size: 2


    private final long txnShrinkTimer;
    private long shrinkTimer;
    private ArrayList<Buffer> buffers;
    private int current;
    private int occupiedSlots;
    private boolean isShrinkTimerOn;

    int allocCounter;

    public ResourceRecordManager(long txnShrinkTimer) {
        this.txnShrinkTimer = txnShrinkTimer;
        buffers = new ArrayList<Buffer>();
        buffers.add(new Buffer());
        current = 0;

        allocCounter = 0;
    }

    enum SlotSource {
        NON_FULL,
        UNINITIALIZED,
        NEW
    }

    synchronized int allocate() {
        if (buffers.get(current).isFull()) {
            final int size = buffers.size();
            final int start = current + 1;
            SlotSource source = SlotSource.NEW;
            for (int j = start; j < start + size; ++j) {
                // If we find a buffer with space, we use it. Otherwise we
                // remember the first uninitialized one and use that one.
                final int i = j % size;
                final Buffer buffer = buffers.get(i);
                if (buffer.isInitialized() && ! buffer.isFull()) {
                    source = SlotSource.NON_FULL;
                    current = i;
                    break;
                } else if (! buffer.isInitialized() && source == SlotSource.NEW) {
                    source = SlotSource.UNINITIALIZED;
                    current = i;
                }
            }

            switch (source) {
                case NEW:
                    buffers.add(new Buffer());
                    current = buffers.size() - 1;
                    break;
                case UNINITIALIZED:
                    buffers.get(current).initialize();
                case NON_FULL:
                    break;
            }
        }
        ++occupiedSlots;
        return buffers.get(current).allocate() + current * NO_SLOTS;
    }

    synchronized void deallocate(int slotNum) {
        buffers.get(slotNum / NO_SLOTS).deallocate(slotNum % NO_SLOTS);
        --occupiedSlots;

        if (needShrink()) {
            shrink();
        }
    }

    /**
     * Shrink policy:
     * Shrink when the resource under-utilization lasts for a certain amount of time.
     * TODO Need to figure out which of the policies is better
     * case1.
     * buffers status : O x x x x x O (O is initialized, x is deinitialized)
     * In the above status, 'CURRENT' needShrink() returns 'TRUE'
     * even if there is nothing to shrink or deallocate.
     * It doesn't distinguish the deinitialized children from initialized children
     * by calculating totalNumOfSlots = buffers.size() * ChildEntityLockInfoArrayManager.NUM_OF_SLOTS.
     * In other words, it doesn't subtract the deinitialized children's slots.
     * case2.
     * buffers status : O O x x x x x
     * However, in the above case, if we subtract the deinitialized children's slots,
     * needShrink() will return false even if we shrink the buffers at this case.
     *
     * @return
     */
    private boolean needShrink() {
        int size = buffers.size();
        int usedSlots = occupiedSlots;
        if (usedSlots == 0) {
            usedSlots = 1;
        }

        if (size > 1 && size * NO_SLOTS / usedSlots >= 3) {
            if (isShrinkTimerOn) {
                if (System.currentTimeMillis() - shrinkTimer >= txnShrinkTimer) {
                    isShrinkTimerOn = false;
                    return true;
                }
            } else {
                //turn on timer
                isShrinkTimerOn = true;
                shrinkTimer = System.currentTimeMillis();
            }
        } else {
            //turn off timer
            isShrinkTimerOn = false;
        }

        return false;
    }

    /**
     * Shrink() may
     * deinitialize(:deallocates ByteBuffer of child) Children(s) or
     * shrink buffers according to the deinitialized children's contiguity status.
     * It doesn't deinitialze or shrink more than half of children at a time.
     */
    private void shrink() {
        int i;
        int removeCount = 0;
        int size = buffers.size();
        int maxDecreaseCount = size / 2;
        Buffer buffer;

        //The first buffer never be deinitialized.
        for (i = 1; i < size; i++) {
            if (buffers.get(i).isEmpty()) {
                buffers.get(i).deinitialize();
            }
        }

        //remove the empty buffers from the end
        for (i = size - 1; i >= 1; i--) {
            buffer = buffers.get(i);
            if (! buffer.isInitialized()) {
                buffers.remove(i);
                if (++removeCount == maxDecreaseCount) {
                    break;
                }
            } else {
                break;
            }
        }

        //reset allocChild to the first buffer
        current = 0;

        isShrinkTimerOn = false;
    }

    public long getLastHolder(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getLong((slotNum % NO_SLOTS) * ITEM_SIZE + LAST_HOLDER_OFF);
    }

    public void setLastHolder(int slotNum, long value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putLong((slotNum % NO_SLOTS) * ITEM_SIZE + LAST_HOLDER_OFF, value);
    }

    public long getFirstWaiter(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getLong((slotNum % NO_SLOTS) * ITEM_SIZE + FIRST_WAITER_OFF);
    }

    public void setFirstWaiter(int slotNum, long value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putLong((slotNum % NO_SLOTS) * ITEM_SIZE + FIRST_WAITER_OFF, value);
    }

    public long getFirstUpgrader(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getLong((slotNum % NO_SLOTS) * ITEM_SIZE + FIRST_UPGRADER_OFF);
    }

    public void setFirstUpgrader(int slotNum, long value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putLong((slotNum % NO_SLOTS) * ITEM_SIZE + FIRST_UPGRADER_OFF, value);
    }

    public long getNext(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getLong((slotNum % NO_SLOTS) * ITEM_SIZE + NEXT_OFF);
    }

    public void setNext(int slotNum, long value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putLong((slotNum % NO_SLOTS) * ITEM_SIZE + NEXT_OFF, value);
    }

    public int getMaxMode(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getInt((slotNum % NO_SLOTS) * ITEM_SIZE + MAX_MODE_OFF);
    }

    public void setMaxMode(int slotNum, int value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putInt((slotNum % NO_SLOTS) * ITEM_SIZE + MAX_MODE_OFF, value);
    }

    public int getDatasetId(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getInt((slotNum % NO_SLOTS) * ITEM_SIZE + DATASET_ID_OFF);
    }

    public void setDatasetId(int slotNum, int value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putInt((slotNum % NO_SLOTS) * ITEM_SIZE + DATASET_ID_OFF, value);
    }

    public int getPkHashVal(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getInt((slotNum % NO_SLOTS) * ITEM_SIZE + PK_HASH_VAL_OFF);
    }

    public void setPkHashVal(int slotNum, int value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putInt((slotNum % NO_SLOTS) * ITEM_SIZE + PK_HASH_VAL_OFF, value);
    }

    public short getAllocId(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        buf.checkSlot(slotNum % NO_SLOTS);
        final ByteBuffer b = buf.bb;
        return b.getShort((slotNum % NO_SLOTS) * ITEM_SIZE + ALLOC_ID_OFF);
    }

    public void setAllocId(int slotNum, short value) {
        final ByteBuffer b = buffers.get(slotNum / NO_SLOTS).bb;
        b.putShort((slotNum % NO_SLOTS) * ITEM_SIZE + ALLOC_ID_OFF, value);
    }


    public AllocInfo getAllocInfo(int slotNum) {
        final Buffer buf = buffers.get(slotNum / NO_SLOTS);
        if (buf.allocList == null) {
            return null;
        } else {
            return buf.allocList.get(slotNum % NO_SLOTS);
        }
    }

    StringBuilder append(StringBuilder sb) {
        sb.append("+++ current: ")
          .append(current)
          .append(" no occupied slots: ")
          .append(occupiedSlots)
          .append(" +++\n");
        for (int i = 0; i < buffers.size(); ++i) {
            buffers.get(i).append(sb);
            sb.append("\n");
        }
        return sb;
    }

    public String toString() {
        return append(new StringBuilder()).toString();
    }

    public RecordManagerStats addTo(RecordManagerStats s) {
        final int size = buffers.size();
        s.buffers += size;
        s.slots += size * NO_SLOTS;
        s.size += size * NO_SLOTS * ITEM_SIZE;
        for (int i = 0; i < size; ++i) {
            buffers.get(i).addTo(s);
        }
        return s;
    }

    static class Buffer {
        private ByteBuffer bb = null; // null represents 'deinitialized' state.
        private int freeSlotNum;
        private int occupiedSlots;

        ArrayList<AllocInfo> allocList;

        Buffer() {
            initialize();
        }

        void initialize() {
            bb = ByteBuffer.allocate(NO_SLOTS * ITEM_SIZE);
            freeSlotNum = 0;
            occupiedSlots = 0;

            for (int i = 0; i < NO_SLOTS - 1; i++) {
                setNextFreeSlot(i, i + 1);
            }
            setNextFreeSlot(NO_SLOTS - 1, -1); //-1 represents EOL(end of link)

            if (TRACK_ALLOC_LOC) {
                allocList = new ArrayList<AllocInfo>(NO_SLOTS);
                for (int i = 0; i < NO_SLOTS; ++i) {
                    allocList.add(new AllocInfo());
                }
            }
        }

        public void deinitialize() {
            if (TRACK_ALLOC_LOC) allocList = null;
            bb = null;
        }

        public boolean isInitialized() {
            return bb != null;
        }

        public boolean isFull() {
            return freeSlotNum == -1;
        }

        public boolean isEmpty() {
            return occupiedSlots == 0;
        }

        public int allocate() {
            int slotNum = freeSlotNum;
            freeSlotNum = getNextFreeSlot(slotNum);
            bb.putLong(slotNum * ITEM_SIZE + LAST_HOLDER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + FIRST_WAITER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + FIRST_UPGRADER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + NEXT_OFF, 0xdeadbeefdeadbeefl);
            bb.putInt(slotNum * ITEM_SIZE + NEXT_FREE_SLOT_OFF, -1);
            bb.putInt(slotNum * ITEM_SIZE + MAX_MODE_OFF, org.apache.asterix.transaction.management.service.transaction.TransactionManagementConstants.LockManagerConstants.LockMode.NL);
            bb.putInt(slotNum * ITEM_SIZE + DATASET_ID_OFF, 0xdeadbeef);
            bb.putInt(slotNum * ITEM_SIZE + PK_HASH_VAL_OFF, 0xdeadbeef);
            bb.putShort(slotNum * ITEM_SIZE + ALLOC_ID_OFF, (short)0xdead);
            occupiedSlots++;
            if (TRACK_ALLOC_LOC) allocList.get(slotNum).alloc();
            return slotNum;
        }

        public void deallocate(int slotNum) {
            bb.putLong(slotNum * ITEM_SIZE + LAST_HOLDER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + FIRST_WAITER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + FIRST_UPGRADER_OFF, -1);
            bb.putLong(slotNum * ITEM_SIZE + NEXT_OFF, 0xdeadbeefdeadbeefl);
            bb.putInt(slotNum * ITEM_SIZE + NEXT_FREE_SLOT_OFF, -1);
            bb.putInt(slotNum * ITEM_SIZE + MAX_MODE_OFF, org.apache.asterix.transaction.management.service.transaction.TransactionManagementConstants.LockManagerConstants.LockMode.NL);
            bb.putInt(slotNum * ITEM_SIZE + DATASET_ID_OFF, 0xdeadbeef);
            bb.putInt(slotNum * ITEM_SIZE + PK_HASH_VAL_OFF, 0xdeadbeef);
            bb.putShort(slotNum * ITEM_SIZE + ALLOC_ID_OFF, (short)0xdead);
            setNextFreeSlot(slotNum, freeSlotNum);
            freeSlotNum = slotNum;
            occupiedSlots--;
            if (TRACK_ALLOC_LOC) allocList.get(slotNum).free();
        }

        public int getNextFreeSlot(int slotNum) {
            return bb.getInt(slotNum * ITEM_SIZE + NEXT_FREE_SLOT_OFF);
        }

        public void setNextFreeSlot(int slotNum, int nextFreeSlot) {
            bb.putInt(slotNum * ITEM_SIZE + NEXT_FREE_SLOT_OFF, nextFreeSlot);
        }

        StringBuilder append(StringBuilder sb) {
            sb.append("++ free slot: ")
              .append(freeSlotNum)
              .append(" no occupied slots: ")
              .append(occupiedSlots)
              .append(" ++\n");
            sb.append("last holder    | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                long value = bb.getLong(i * ITEM_SIZE + LAST_HOLDER_OFF);
                sb = TypeUtil.Global.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("first waiter   | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                long value = bb.getLong(i * ITEM_SIZE + FIRST_WAITER_OFF);
                sb = TypeUtil.Global.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("first upgrader | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                long value = bb.getLong(i * ITEM_SIZE + FIRST_UPGRADER_OFF);
                sb = TypeUtil.Global.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("next           | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                long value = bb.getLong(i * ITEM_SIZE + NEXT_OFF);
                sb = TypeUtil.Global.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("next free slot | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                int value = bb.getInt(i * ITEM_SIZE + NEXT_FREE_SLOT_OFF);
                sb = TypeUtil.Int.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("max mode       | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                int value = bb.getInt(i * ITEM_SIZE + MAX_MODE_OFF);
                sb = TypeUtil.Int.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("dataset id     | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                int value = bb.getInt(i * ITEM_SIZE + DATASET_ID_OFF);
                sb = TypeUtil.Int.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("pk hash val    | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                int value = bb.getInt(i * ITEM_SIZE + PK_HASH_VAL_OFF);
                sb = TypeUtil.Int.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");
            sb.append("alloc id       | ");
            for (int i = 0; i < NO_SLOTS; ++i) {
                short value = bb.getShort(i * ITEM_SIZE + ALLOC_ID_OFF);
                sb = TypeUtil.Short.appendFixed(sb, value);
                sb.append(" | ");
            }
            sb.append("\n");

            return sb;
        }

        public String toString() {
            return append(new StringBuilder()).toString();
        }

        public void addTo(RecordManagerStats s) {
            if (isInitialized()) {
                s.items += occupiedSlots;
            }
        }

        private void checkSlot(int slotNum) {
            if (! CHECK_SLOTS) {
                return;
            }
            final int itemOffset = (slotNum % NO_SLOTS) * ITEM_SIZE;
            if (bb.getLong(itemOffset + LAST_HOLDER_OFF) == 0xdeadbeefdeadbeefl) {
                String msg = "invalid value in field LAST_HOLDER_OFF of slot " + TypeUtil.Global.toString(slotNum);
                throw new IllegalStateException(msg);
            }
            if (bb.getLong(itemOffset + FIRST_WAITER_OFF) == 0xdeadbeefdeadbeefl) {
                String msg = "invalid value in field FIRST_WAITER_OFF of slot " + TypeUtil.Global.toString(slotNum);
                throw new IllegalStateException(msg);
            }
            if (bb.getLong(itemOffset + FIRST_UPGRADER_OFF) == 0xdeadbeefdeadbeefl) {
                String msg = "invalid value in field FIRST_UPGRADER_OFF of slot " + TypeUtil.Global.toString(slotNum);
                throw new IllegalStateException(msg);
            }
            if (bb.getInt(itemOffset + NEXT_FREE_SLOT_OFF) == 0xdeadbeef) {
                String msg = "invalid value in field NEXT_FREE_SLOT_OFF of slot " + TypeUtil.Global.toString(slotNum);
                throw new IllegalStateException(msg);
            }
            if (bb.getInt(itemOffset + MAX_MODE_OFF) == 0xdeadbeef) {
                String msg = "invalid value in field MAX_MODE_OFF of slot " + TypeUtil.Global.toString(slotNum);
                throw new IllegalStateException(msg);
            }
        }
    }

}
