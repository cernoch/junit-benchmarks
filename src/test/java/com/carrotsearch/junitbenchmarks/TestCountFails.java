/*
 * Copyright 2014 Carrot Search s.c..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carrotsearch.junitbenchmarks;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * 
 *
 * @author Radomír Černoch (radomir.cernoch at gmail.com)
 */
public class TestCountFails {

    @Rule
    public TestRule runBenchmarks = new BenchmarkRule();
    
    private int sleep = 0;
    
    @Test(timeout = 150)
    @BenchmarkOptions(
            warmupRounds = 0,
            benchmarkRounds = 3,
            countFails = true)
    public void run() throws InterruptedException {
        sleep += 100;
        Thread.sleep(sleep);
    }
}
