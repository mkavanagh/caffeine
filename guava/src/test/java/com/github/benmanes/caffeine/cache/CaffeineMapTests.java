/*
 * Copyright 2015 Ben Manes. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.guava.CaffeinatedGuava;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Guava testlib map tests for the {@link Cache#asMap()} view.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public final class CaffeineMapTests extends TestCase {

  public static Test suite() throws Exception {
    TestSuite suite = new TestSuite();
    addGuavaViewTests(suite);
    addUnboundedTests(suite);
    addBoundedTests(suite);
    return suite;
  }

  private static void addUnboundedTests(TestSuite suite) throws Exception {
    suite.addTest(MapTestFactory.suite("UnboundedCache", () -> {
      Cache<String, String> cache = Caffeine.newBuilder().build();
      return cache.asMap();
    }));
    suite.addTest(MapTestFactory.suite("UnboundedAsyncCache", () -> {
      AsyncLoadingCache<String, String> cache = Caffeine.newBuilder().buildAsync(key -> null);
      return cache.synchronous().asMap();
    }));
  }

  private static void addBoundedTests(TestSuite suite) throws Exception {
    suite.addTest(MapTestFactory.suite("BoundedCache", () -> {
      Cache<String, String> cache = Caffeine.newBuilder().maximumSize(Long.MAX_VALUE).build();
      return cache.asMap();
    }));
    suite.addTest(MapTestFactory.suite("BoundedAsyncCache", () -> {
      AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
          .maximumSize(Long.MAX_VALUE)
          .buildAsync(key -> null);
      return cache.synchronous().asMap();
    }));
  }

  private static void addGuavaViewTests(TestSuite suite) throws Exception {
    suite.addTest(MapTestFactory.suite("GuavaView", () -> {
      com.google.common.cache.Cache<String, String> cache = CaffeinatedGuava.build(
          Caffeine.newBuilder().maximumSize(Long.MAX_VALUE));
      return cache.asMap();
    }));
    suite.addTest(MapTestFactory.suite("GuavaLoadingView", () -> {
      com.google.common.cache.Cache<String, String> cache = CaffeinatedGuava.build(
          Caffeine.newBuilder().maximumSize(Long.MAX_VALUE), key -> null);
      return cache.asMap();
    }));
  }
}
