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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class MediaType_hasParameters_15_0_Test {

    @Test
    public void testHasParametersWithEmptyString() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", ""));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "1.0;"));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInValue() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKey() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0"));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInBothKeyAndValue() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharactersAndWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharactersAndWhitespaceAndSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }
}
