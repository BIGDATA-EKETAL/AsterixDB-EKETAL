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
package org.apache.asterix.runtime.aggregates.scalar;

import org.apache.asterix.runtime.aggregates.base.SingleFieldFrameTupleReference;
import org.apache.hyracks.algebricks.runtime.base.IAggregateEvaluator;
import org.apache.hyracks.algebricks.runtime.base.IScalarEvaluator;
import org.apache.hyracks.algebricks.runtime.base.IUnnestingEvaluator;
import org.apache.hyracks.algebricks.runtime.base.IUnnestingEvaluatorFactory;
import org.apache.hyracks.api.context.IHyracksTaskContext;
import org.apache.hyracks.api.exceptions.HyracksDataException;
import org.apache.hyracks.data.std.api.IPointable;
import org.apache.hyracks.data.std.primitive.VoidPointable;
import org.apache.hyracks.dataflow.common.data.accessors.IFrameTupleReference;

/**
 * Implements scalar aggregates by iterating over a collection with the ScanCollection unnesting function,
 * and applying the corresponding ICopyAggregateFunction to each collection-item.
 */
public class GenericScalarAggregateFunction implements IScalarEvaluator {

    private final IPointable listItemOut = new VoidPointable();
    private final IAggregateEvaluator aggFunc;
    private final IUnnestingEvaluator scanCollection;

    private final SingleFieldFrameTupleReference itemTuple = new SingleFieldFrameTupleReference();

    public GenericScalarAggregateFunction(IAggregateEvaluator aggFunc, IUnnestingEvaluatorFactory scanCollectionFactory,
            IHyracksTaskContext context) throws HyracksDataException {
        this.aggFunc = aggFunc;
        this.scanCollection = scanCollectionFactory.createUnnestingEvaluator(context);
    }

    @Override
    public void evaluate(IFrameTupleReference tuple, IPointable result) throws HyracksDataException {
        scanCollection.init(tuple);
        aggFunc.init();
        while (scanCollection.step(listItemOut)) {
            itemTuple.reset(listItemOut.getByteArray(), listItemOut.getStartOffset(), listItemOut.getLength());
            aggFunc.step(itemTuple);
        }
        aggFunc.finish(result);
    }
}
