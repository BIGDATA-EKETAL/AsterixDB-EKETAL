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

public class JobArenaManager {

    public static final boolean TRACK_ALLOC_ID = false;

    private final int noArenas;
    private final JobRecordManager[] arenas;
    private ThreadLocal<LocalManager> local;

    static class LocalManager {
        int arenaId;
        JobRecordManager mgr;
    }

    public JobArenaManager(final int noArenas, final long txnShrinkTimer) {
        this.noArenas = noArenas;
        arenas = new JobRecordManager[noArenas];
        for (int i = 0; i < noArenas; ++i) {
            arenas[i] = new JobRecordManager(txnShrinkTimer);
        }
        local = new ThreadLocal<LocalManager>() {
            private int nextArena = 0;

            @Override
            protected synchronized LocalManager initialValue() {
                JobRecordManager mgr = arenas[nextArena];
                LocalManager res = new LocalManager();
                res.mgr = mgr;
                res.arenaId = nextArena;
                nextArena = (nextArena + 1) % noArenas;
                return res;
            }
        };
    }

    public long allocate() {
        final LocalManager localManager = local.get();
        final JobRecordManager recMgr = localManager.mgr;
        final int allocId = TRACK_ALLOC_ID ? (++recMgr.allocCounter % 0x7fff) : 0;
        final int localId = recMgr.allocate();

        long result = TypeUtil.Global.build(localManager.arenaId, allocId, localId);

        if (TRACK_ALLOC_ID) setAllocId(result, (short) allocId);

        assert TypeUtil.Global.allocId(result) == allocId;
        assert TypeUtil.Global.arenaId(result) == localManager.arenaId;
        assert TypeUtil.Global.localId(result) == localId;
        return result;
    }

    public void deallocate(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        get(arenaId).deallocate(TypeUtil.Global.localId(slotNum));
    }

    public JobRecordManager get(int i) {
        return arenas[i];
    }

    public JobRecordManager local() {
        return local.get().mgr;
    }

    public long getLastHolder(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getLastHolder(localId);
    }

    public void setLastHolder(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setLastHolder(localId, value);
    }

    public long getLastWaiter(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getLastWaiter(localId);
    }

    public void setLastWaiter(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setLastWaiter(localId, value);
    }

    public long getLastUpgrader(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getLastUpgrader(localId);
    }

    public void setLastUpgrader(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setLastUpgrader(localId, value);
    }

    public int getJobId(long slotNum) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getJobId(localId);
    }

    public void setJobId(long slotNum, int value) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setJobId(localId, value);
    }

    public short getAllocId(long slotNum) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getAllocId(localId);
    }

    public void setAllocId(long slotNum, short value) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setAllocId(localId, value);
    }


    private void checkAllocId(long slotNum) {
        final int refAllocId = TypeUtil.Global.allocId(slotNum);
        final short curAllocId = getAllocId(slotNum);
        if (refAllocId != curAllocId) {
            String msg = "reference to slot " + TypeUtil.Global.toString(slotNum)
                + " of arena " + TypeUtil.Global.arenaId(slotNum)
                + " refers to version " + Integer.toHexString(refAllocId)
                + " current version is " + Integer.toHexString(curAllocId);
            AllocInfo a = getAllocInfo(slotNum);
            if (a != null) {
                msg += "\n" + a.toString();
            }
            throw new IllegalStateException(msg);
        }
    }

    public AllocInfo getAllocInfo(long slotNum) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        return get(arenaId).getAllocInfo(TypeUtil.Global.localId(slotNum));
    }

    public StringBuilder appendRecord(StringBuilder sb, long slotNum) {
        sb.append("{ ");

        sb.append("\"last holder\" : \"");
        sb = TypeUtil.Global.append(sb, getLastHolder(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"last waiter\" : \"");
        sb = TypeUtil.Global.append(sb, getLastWaiter(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"last upgrader\" : \"");
        sb = TypeUtil.Global.append(sb, getLastUpgrader(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"job id\" : \"");
        sb = TypeUtil.Int.append(sb, getJobId(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"alloc id\" : \"");
        sb = TypeUtil.Short.append(sb, getAllocId(slotNum));
        sb.append("\"");

        return sb.append(" }");
    }

    public StringBuilder append(StringBuilder sb) {
        for (int i = 0; i < noArenas; ++i) {
            sb.append("++++ arena ").append(i).append(" ++++\n");
            arenas[i].append(sb);
        }
        return sb;
    }

    public String toString() {
        return append(new StringBuilder()).toString();
    }

    public RecordManagerStats addTo(RecordManagerStats s) {
        s.arenas += noArenas;
        for (int i = 0; i < noArenas; ++i) {
            arenas[i].addTo(s);
        }
        return s;
    }
}
