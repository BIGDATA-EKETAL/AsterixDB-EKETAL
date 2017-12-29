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

public class RequestArenaManager {

    public static final boolean TRACK_ALLOC_ID = false;

    private final int noArenas;
    private final RequestRecordManager[] arenas;
    private ThreadLocal<LocalManager> local;

    static class LocalManager {
        int arenaId;
        RequestRecordManager mgr;
    }

    public RequestArenaManager(final int noArenas, final long txnShrinkTimer) {
        this.noArenas = noArenas;
        arenas = new RequestRecordManager[noArenas];
        for (int i = 0; i < noArenas; ++i) {
            arenas[i] = new RequestRecordManager(txnShrinkTimer);
        }
        local = new ThreadLocal<LocalManager>() {
            private int nextArena = 0;

            @Override
            protected synchronized LocalManager initialValue() {
                RequestRecordManager mgr = arenas[nextArena];
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
        final RequestRecordManager recMgr = localManager.mgr;
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

    public RequestRecordManager get(int i) {
        return arenas[i];
    }

    public RequestRecordManager local() {
        return local.get().mgr;
    }

    public long getResourceId(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getResourceId(localId);
    }

    public void setResourceId(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setResourceId(localId, value);
    }

    public long getJobSlot(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getJobSlot(localId);
    }

    public void setJobSlot(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setJobSlot(localId, value);
    }

    public long getPrevJobRequest(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getPrevJobRequest(localId);
    }

    public void setPrevJobRequest(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setPrevJobRequest(localId, value);
    }

    public long getNextJobRequest(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getNextJobRequest(localId);
    }

    public void setNextJobRequest(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setNextJobRequest(localId, value);
    }

    public long getNextRequest(long slotNum) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getNextRequest(localId);
    }

    public void setNextRequest(long slotNum, long value) {
        if (TRACK_ALLOC_ID) checkAllocId(slotNum);
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setNextRequest(localId, value);
    }

    public int getLockMode(long slotNum) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        return get(arenaId).getLockMode(localId);
    }

    public void setLockMode(long slotNum, int value) {
        final int arenaId = TypeUtil.Global.arenaId(slotNum);
        final int localId = TypeUtil.Global.localId(slotNum);
        get(arenaId).setLockMode(localId, value);
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

        sb.append("\"resource id\" : \"");
        sb = TypeUtil.Global.append(sb, getResourceId(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"job slot\" : \"");
        sb = TypeUtil.Global.append(sb, getJobSlot(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"prev job request\" : \"");
        sb = TypeUtil.Global.append(sb, getPrevJobRequest(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"next job request\" : \"");
        sb = TypeUtil.Global.append(sb, getNextJobRequest(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"next request\" : \"");
        sb = TypeUtil.Global.append(sb, getNextRequest(slotNum));
        sb.append("\"");

        sb.append(", ");

        sb.append("\"lock mode\" : \"");
        sb = TypeUtil.Int.append(sb, getLockMode(slotNum));
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
