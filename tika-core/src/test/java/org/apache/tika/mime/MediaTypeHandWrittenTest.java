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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Hand-written tests for the {@link MediaType} mutants that survived MediaTypeTest and the
 * ChatUniTest-generated tests.
 */
public class MediaTypeHandWrittenTest {

    @Test
    public void audioFactoryProducesAudioWavType() {
        assertEquals("audio/wav", String.valueOf(MediaType.audio("wav")));
    }

    @Test
    public void mediaTypeSetHasNoNullsOrDuplicates() {
        MediaType json = MediaType.parse("application/json");
        Set<MediaType> set = MediaType.set(MediaType.TEXT_PLAIN, null, json,
                MediaType.TEXT_PLAIN);

        assertEquals(2, set.size());
        for (MediaType type : set) {
            assertNotNull(type);
        }
        assertThrows(UnsupportedOperationException.class, () -> set.add(MediaType.OCTET_STREAM));
    }

    @Test
    public void stringSetDropsEntriesWithEmptySubtype() {
        Set<MediaType> set = MediaType.set("text/plain", "text/", "application/json");

        assertEquals(Set.of(MediaType.TEXT_PLAIN, MediaType.parse("application/json")), set);
    }

    @Test
    public void emptyTypeOrSubtypeIsRejected() {
        assertNull(MediaType.parse("/"));
        assertNull(MediaType.parse("/plain"));
        assertNull(MediaType.parse("text/"));
    }

    @Test
    public void charsetFirstFormIsParsedIntoTypeAndParameter() {
        MediaType type = MediaType.parse("charset=windows-1252; application/json");

        assertNotNull(type);
        assertEquals(MediaType.parse("application/json"), type.getBaseType());
        assertEquals("windows-1252", type.getParameters().get("charset"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"APPLICATION/JSON", "Application/Json", "application/JSON"})
    public void asciiUpperCaseIsLowerCased(String input) {
        assertEquals(MediaType.parse("application/json"), MediaType.parse(input));
    }

    @Test
    public void latinOneUpperCaseIsLowerCased() {
        assertEquals("\u00e4\u00f6/\u00fc", String.valueOf(MediaType.parse("\u00c4\u00d6/\u00dc")));
    }

    @Test
    public void charsetConstructorAddsCharsetParameter() {
        MediaType type = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_16);

        assertEquals(MediaType.parse("text/plain; charset=UTF-16"), type);
        assertEquals(1, type.getParameters().size());
    }

    @Test
    public void emptyMapKeepsExistingParameters() {
        MediaType base = MediaType.parse("text/html; charset=UTF-8");

        MediaType same = new MediaType(base, Collections.emptyMap());

        assertEquals("UTF-8", same.getParameters().get("charset"));
        assertEquals(1, same.getParameters().size());
    }

    @Test
    public void mergeKeepsOldAndNewParameters() {
        MediaType base = MediaType.parse("text/plain; charset=UTF-8");

        MediaType merged = new MediaType(base, Map.of("format", "flowed"));

        assertEquals("UTF-8", merged.getParameters().get("charset"));
        assertEquals("flowed", merged.getParameters().get("format"));
        assertEquals(2, merged.getParameters().size());
    }

    @Test
    public void baseTypeStringHasNoParameters() {
        MediaType base = MediaType.parse("application/json; charset=UTF-8").getBaseType();

        assertEquals("application/json", String.valueOf(base));
        assertFalse(base.hasParameters());
    }

    @Test
    public void equalsRejectsOtherKindsOfObject() {
        assertNotEquals(MediaType.TEXT_PLAIN, "text/plain");
        assertFalse(MediaType.TEXT_HTML.equals(null));
    }
}
