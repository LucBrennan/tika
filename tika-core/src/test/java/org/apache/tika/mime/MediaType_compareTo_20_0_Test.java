/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.mime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MediaType_compareTo_20_0_Test {

    // FIX (setup): @InjectMocks/@Mock and openMocks() removed; @InjectMocks cannot build a
    // MediaType, which made every test fail before it started
    private MediaType mediaType;

    private MediaType otherMediaType;

    @BeforeEach
    public void setUp() throws Exception {
        mediaType = new MediaType("text/plain", "UTF-8");
        otherMediaType = new MediaType("text/plain", "UTF-8");
    }

    @Test
    public void testCompareTo() throws Exception {
        assertEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentType() throws Exception {
        otherMediaType = new MediaType("application/xml", "UTF-8");
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentSubtype() throws Exception {
        otherMediaType = new MediaType("text/plain", "ISO-8859-1");
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentParameters() throws Exception {
        otherMediaType = new MediaType("text/plain", "UTF-8", Map.of("charset", "ISO-8859-1"));
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentOrderParameters() throws Exception {
        otherMediaType = new MediaType("text/plain", "UTF-8", Map.of("charset", "ISO-8859-1", "name", "value"));
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }
}
