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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MediaType_equals_18_0_Test {

    private MediaType mediaType1;

    private MediaType mediaType2;

    private MediaType mediaType3;

    private MediaType mediaType4;

    private MediaType mediaType5;

    private MediaType mediaType6;

    @BeforeEach
    public void setUp() {
        // Create some test media types
        mediaType1 = new MediaType("application", "octet-stream", Collections.emptyMap());
        mediaType2 = new MediaType("text", "plain", Collections.emptyMap());
        mediaType3 = new MediaType("application", "xml", Collections.emptyMap());
        mediaType4 = new MediaType("application", "zip", Collections.emptyMap());
        mediaType5 = new MediaType("application", "octet-stream", Collections.singletonMap("charset", "UTF-8"));
        mediaType6 = new MediaType("application", "octet-stream", Collections.singletonMap("charset", "ISO-8859-1"));
    }

    @Test
    public void testEqualsSameInstance() {
        assertTrue(mediaType1.equals(mediaType1));
    }

    @Test
    public void testEqualsDifferentInstances() {
        assertFalse(mediaType1.equals(mediaType2));
        assertFalse(mediaType1.equals(mediaType3));
        assertFalse(mediaType1.equals(mediaType4));
    }

    @Test
    public void testEqualsDifferentType() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsDifferentParameters() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeAndParameters() {
        assertTrue(mediaType1.equals(mediaType1));
        // FIX (oracle): 3 assertions removed; they claimed application/octet-stream equals
        // text/plain, application/xml and application/zip, contradicting testEqualsDifferentInstances
    }

    @Test
    public void testEqualsSameTypeDifferentParameters() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParameters() {
        assertTrue(mediaType1.equals(mediaType1));
        // FIX (oracle): 3 assertions removed; they claimed application/octet-stream equals
        // text/plain, application/xml and application/zip, contradicting testEqualsDifferentInstances
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrder() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding2() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding3() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding4() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding5() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding6() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding7() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding8() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding9() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }
}
