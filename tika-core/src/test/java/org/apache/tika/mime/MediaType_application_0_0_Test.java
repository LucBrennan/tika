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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MediaType_application_0_0_Test {

    @Test
    public void testApplication() {
        MediaType type = MediaType.application("json");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }

    @Test
    public void testParse() {
        MediaType type = MediaType.parse("application/json");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("application/json; charset=utf-8");
        // FIX (oracle): toString() keeps the parameters
        assertEquals("application/json; charset=utf-8", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("utf-8", type.getParameters().get("charset"));
    }

    @Test
    public void testParseSimple() {
        MediaType type = MediaType.parse("application/octet-stream");
        assertEquals("application/octet-stream", type.toString());
        assertEquals("octet-stream", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("text/plain");
        assertEquals("text/plain", type.toString());
        assertEquals("plain", type.getSubtype());
        assertEquals("text", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }

    @Test
    public void testParseWithParameters() {
        MediaType type = MediaType.parse("application/json; charset=utf-8; foo=bar");
        // FIX (oracle): toString() keeps the parameters
        assertEquals("application/json; charset=utf-8; foo=bar", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("utf-8", type.getParameters().get("charset"));
        assertEquals("bar", type.getParameters().get("foo"));
    }

    @Test
    public void testParseInvalid() {
        assertNull(MediaType.parse(null));
        assertNull(MediaType.parse(""));
        assertNull(MediaType.parse("application/"));
        assertNull(MediaType.parse("application/json/"));
        // FIX (oracle): 3 assertions removed; parse() deliberately accepts a trailing ';'
        // and a parameter without a value (see MediaTypeTest.testParseNoParamsWithSemi)
    }

    @Test
    public void testParseWithDuplicateParameters() {
        MediaType type = MediaType.parse("application/json; charset=utf-8; charset=utf-16");
        // FIX (oracle): toString() keeps the parameters, and the last occurrence wins
        assertEquals("application/json; charset=utf-16", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        // FIX (oracle): the last occurrence wins, not the first
        assertEquals("utf-16", type.getParameters().get("charset"));
    }

    // FIX (test removed): testParseWithSpecialCharacters escaped the quotes twice in its input,
    // so its expected values could not match any parser behaviour

    @Test
    public void testParseWithEmptyParameters() {
        MediaType type = MediaType.parse("application/json; ");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("application/json; ; ");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }
}
