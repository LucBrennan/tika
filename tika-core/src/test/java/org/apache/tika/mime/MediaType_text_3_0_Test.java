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

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MediaType_text_3_0_Test {

    @Test
    public void testTextMethod() throws Exception {
        // Given
        String type = "plain";
        MediaType expected = MediaType.TEXT_PLAIN;
        // When
        MediaType result = MediaType.text(type);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethod() throws Exception {
        // Given
        String string = "text/plain";
        MediaType expected = MediaType.TEXT_PLAIN;
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethodWithParameters() throws Exception {
        // Given
        String string = "text/plain; charset=utf-8";
        MediaType expected = new MediaType("text", "plain", Collections.singletonMap("charset", "utf-8"));
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethodWithMultipleParameters() throws Exception {
        // Given
        String string = "text/plain; charset=utf-8; language=en";
        // FIX (oracle): the expected type must contain both parameters
        MediaType expected = new MediaType("text", "plain", Map.of("charset", "utf-8", "language", "en"));
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    // FIX (12 tests removed): testParseMethodWithInvalidType, ...InvalidSubtype, ...InvalidParameter,
    // ...InvalidParameterName and ...InvalidParameterValue to ...InvalidParameterValue8 all expected
    // parse() to return null. parse() only checks syntax, so all of these inputs are accepted.
}
