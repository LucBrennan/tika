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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Hand-written tests for the {@link MediaType} mutants that survived MediaTypeTest and the
 * ChatUniTest-generated tests (see tache2/after-generated-unkilled.txt).
 */
public class MediaTypeHandWrittenTest {

    @Test
    public void setOfMediaTypesSkipsNullAndDuplicates() {
        Set<MediaType> set = MediaType.set(MediaType.TEXT_PLAIN, null, MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN);

        assertEquals(Set.of(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML), set);
        assertThrows(UnsupportedOperationException.class, () -> set.add(MediaType.OCTET_STREAM));
    }

    @Test
    public void setOfStringsSkipsValuesThatCannotBeParsed() {
        Set<MediaType> set = MediaType.set("text/plain", "no-slash", "text/html");

        assertEquals(Set.of(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML), set);
        assertThrows(UnsupportedOperationException.class, () -> set.add(MediaType.OCTET_STREAM));
    }

    @Test
    public void addingAParameterToATypeWithoutParameters() {
        MediaType expected = MediaType.parse("text/plain; charset=UTF-8");

        assertEquals(expected, new MediaType(MediaType.TEXT_PLAIN, "charset", "UTF-8"));
        assertEquals(expected, new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8));
        assertEquals(expected, new MediaType(MediaType.TEXT_PLAIN, Map.of("charset", "UTF-8")));
    }

    @Test
    public void addingAParameterToATypeWithParametersKeepsBoth() {
        MediaType base = MediaType.parse("text/plain; charset=UTF-8");

        MediaType merged = new MediaType(base, "format", "flowed");

        assertEquals(Map.of("charset", "UTF-8", "format", "flowed"), merged.getParameters());
    }

    @Test
    public void newParameterValueReplacesTheOldOne() {
        MediaType base = MediaType.parse("text/plain; charset=ISO-8859-1; format=fixed");

        MediaType changed = new MediaType(base, StandardCharsets.UTF_8);

        assertEquals("text/plain; charset=UTF-8; format=fixed", changed.toString());
    }

    @Test
    public void addingNoParametersKeepsTheExistingOnes() {
        MediaType base = MediaType.parse("text/plain; charset=UTF-8");

        assertEquals(base, new MediaType(base, Collections.emptyMap()));
    }

    @Test
    public void baseTypeDropsTheParameters() {
        MediaType base = MediaType.parse("text/html; charset=UTF-8").getBaseType();

        assertEquals(MediaType.TEXT_HTML, base);
        assertFalse(base.hasParameters());
        assertEquals(MediaType.TEXT_HTML, MediaType.TEXT_HTML.getBaseType());
    }

    @Test
    public void audioFactoryBuildsAnAudioType() {
        MediaType mpeg = MediaType.audio("mpeg");

        assertEquals("audio", mpeg.getType());
        assertEquals("mpeg", mpeg.getSubtype());
        assertEquals(MediaType.parse("audio/mpeg"), mpeg);
    }

    @Test
    public void charsetBeforeTheTypeIsAccepted() {
        // TIKA-350: some web servers send "charset=...; type/subtype"
        MediaType type = MediaType.parse("charset=UTF-8; text/html");

        assertEquals("text/html; charset=UTF-8", String.valueOf(type));
    }

    @Test
    public void typeWithoutParametersHasNoParameters() {
        assertFalse(MediaType.TEXT_PLAIN.hasParameters());
        assertFalse(MediaType.parse("application/zip").hasParameters());
        assertTrue(MediaType.parse("text/plain; charset=UTF-8").hasParameters());
    }

    @Test
    public void mediaTypeIsNeverEqualToAnotherKindOfObject() {
        assertFalse(MediaType.TEXT_PLAIN.equals("text/plain"));
        assertFalse(MediaType.TEXT_PLAIN.equals(null));
    }

    /**
     * Upper case and surrounding spaces must be normalised. isSimpleName() is a fast path
     * for names that need no normalisation, so it must reject these inputs.
     */
    @ParameterizedTest
    @ValueSource(strings = {"TEXT/PLAIN", "TEXT/plain", "Text/Plain", " text/plain "})
    public void typeAndSubtypeAreNormalised(String input) {
        assertEquals(MediaType.TEXT_PLAIN, MediaType.parse(input));
    }

    /**
     * U+00C0 is above 'z'. The fast path must reject it so that the slow path lower-cases it.
     */
    @Test
    public void nonAsciiUpperCaseLettersAreLowerCased() {
        assertEquals("à/à", String.valueOf(MediaType.parse("À/À")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "text/", "/plain", "textplain"})
    public void typeOrSubtypeMissingGivesNull(String input) {
        assertNull(MediaType.parse(input));
    }
}
