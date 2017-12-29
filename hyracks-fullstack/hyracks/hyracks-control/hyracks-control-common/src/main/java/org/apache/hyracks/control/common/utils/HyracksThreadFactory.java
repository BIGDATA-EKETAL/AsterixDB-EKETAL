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
package org.apache.hyracks.control.common.utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HyracksThreadFactory implements ThreadFactory {
    private final String identifier;
    private final AtomicInteger threadId = new AtomicInteger();

    private static final Logger LOGGER = LogManager.getLogger();

    public HyracksThreadFactory(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, "Executor-" + threadId.incrementAndGet() + ":" + identifier);
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOGGER.log(Level.ERROR, "Uncaught exception by " + t.getName(), e);
            }
        });
        return t;
    }
}