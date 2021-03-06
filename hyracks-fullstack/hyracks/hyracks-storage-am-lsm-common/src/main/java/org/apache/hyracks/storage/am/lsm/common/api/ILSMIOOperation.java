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
package org.apache.hyracks.storage.am.lsm.common.api;

import java.util.concurrent.Callable;

import org.apache.hyracks.api.exceptions.HyracksDataException;
import org.apache.hyracks.api.io.FileReference;
import org.apache.hyracks.api.io.IODeviceHandle;

public interface ILSMIOOperation extends Callable<Boolean> {

    /**
     * Represents the io operation type
     */
    enum LSMIOOperationType {
        FLUSH,
        MERGE,
        LOAD
    }

    /**
     * @return the device on which the operation is running
     */
    IODeviceHandle getDevice();

    /**
     * @return the operation callback
     */
    ILSMIOOperationCallback getCallback();

    /**
     * @return the index id
     */
    String getIndexIdentifier();

    /**
     * @return the operation type
     */
    LSMIOOperationType getIOOpertionType();

    @Override
    Boolean call() throws HyracksDataException;

    /**
     * @return The target of the io operation
     */
    FileReference getTarget();

    /**
     * @return the accessor of the operation
     */
    ILSMIndexAccessor getAccessor();
}
