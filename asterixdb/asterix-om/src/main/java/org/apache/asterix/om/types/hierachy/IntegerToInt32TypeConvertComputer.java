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
package org.apache.asterix.om.types.hierachy;

import java.io.DataOutput;
import java.io.IOException;

import org.apache.asterix.om.base.IAObject;
import org.apache.asterix.om.types.ATypeTag;
import org.apache.hyracks.api.exceptions.HyracksDataException;

public class IntegerToInt32TypeConvertComputer extends AbstractIntegerTypeConvertComputer {

    private static final IntegerToInt32TypeConvertComputer INSTANCE_STRICT =
            new IntegerToInt32TypeConvertComputer(true);

    private static final IntegerToInt32TypeConvertComputer INSTANCE_LAX = new IntegerToInt32TypeConvertComputer(false);

    private IntegerToInt32TypeConvertComputer(boolean strict) {
        super(strict);
    }

    public static IntegerToInt32TypeConvertComputer getInstance(boolean strict) {
        return strict ? INSTANCE_STRICT : INSTANCE_LAX;
    }

    @Override
    public void convertType(byte[] data, int start, int length, DataOutput out) throws IOException {
        convertIntegerType(data, start, length, out, ATypeTag.INTEGER, 4);
    }

    @Override
    public IAObject convertType(IAObject sourceObject) throws HyracksDataException {
        return convertIntegerType(sourceObject, ATypeTag.INTEGER);
    }
}