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

package org.apache.asterix.common.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.asterix.common.api.IDatasetLifecycleManager;
import org.apache.asterix.common.api.INcApplicationContext;
import org.apache.hyracks.api.application.INCServiceContext;
import org.apache.hyracks.storage.am.lsm.common.api.ILSMMergePolicy;
import org.apache.hyracks.storage.am.lsm.common.api.ILSMMergePolicyFactory;

public class CorrelatedPrefixMergePolicyFactory implements ILSMMergePolicyFactory {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "correlated-prefix";
    public static final String KEY_DATASET_ID = "datasetId";
    public static final String KEY_MAX_COMPONENT_SIZE = "max-mergable-component-size";
    public static final String KEY_MAX_COMPONENT_COUNT = "max-tolerance-component-count";

    private static final String[] SET_VALUES = new String[] { KEY_MAX_COMPONENT_SIZE, KEY_MAX_COMPONENT_COUNT };
    private static final Set<String> PROPERTIES_NAMES = new HashSet<>(Arrays.asList(SET_VALUES));

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Set<String> getPropertiesNames() {
        return PROPERTIES_NAMES;
    }

    @Override
    public ILSMMergePolicy createMergePolicy(Map<String, String> configuration, INCServiceContext ctx) {
        IDatasetLifecycleManager dslcManager =
                ((INcApplicationContext) ctx.getApplicationContext()).getDatasetLifecycleManager();
        int datasetId = Integer.parseInt(configuration.get(KEY_DATASET_ID));
        ILSMMergePolicy policy = new CorrelatedPrefixMergePolicy(dslcManager, datasetId);
        policy.configure(configuration);
        return policy;
    }
}