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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Patterns_add_1_0_Test {

    private Patterns patterns;

    private MediaTypeRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = mock(MediaTypeRegistry.class);
        patterns = new Patterns(registry);
    }

    @Test
    public void testAddName() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("text/plain"));
        patterns.add("example", false, type);
        // FIX (access): names is private; matches() returns the exact-name entry
        assertEquals(type, patterns.matches("example"));
    }

    @Test
    public void testAddExtension() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("image/jpeg"));
        patterns.add("jpg", false, type);
        // FIX (access): extensions is private; matches() is used instead. "jpg" has no '*',
        // so it is in fact stored as a name pattern, not as an extension
        assertEquals(type, patterns.matches("jpg"));
    }

    @Test
    public void testAddGlob() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("application/pdf"));
        patterns.add("*.pdf", false, type);
        // FIX (access): globs is private; matches() is used instead. "*.pdf" is in fact
        // stored as the extension ".pdf", which the name "*.pdf" ends with
        assertEquals(type, patterns.matches("*.pdf"));
    }

    @Test
    public void testAddNameConflict() throws MimeTypeException {
        MimeType type1 = new MimeType(MediaType.parse("text/plain"));
        MimeType type2 = new MimeType(MediaType.parse("application/xml"));
        patterns.add("example", false, type1);
        assertThrows(MimeTypeException.class, () -> patterns.add("example", false, type2));
    }

    @Test
    public void testAddExtensionConflict() throws MimeTypeException {
        MimeType type1 = new MimeType(MediaType.parse("image/jpeg"));
        MimeType type2 = new MimeType(MediaType.parse("image/png"));
        patterns.add("jpg", false, type1);
        assertThrows(MimeTypeException.class, () -> patterns.add("jpg", false, type2));
    }

    @Test
    public void testAddGlobConflict() throws MimeTypeException {
        MimeType type1 = new MimeType(MediaType.parse("application/pdf"));
        MimeType type2 = new MimeType(MediaType.parse("application/zip"));
        patterns.add("*.pdf", false, type1);
        assertThrows(MimeTypeException.class, () -> patterns.add("*.pdf", false, type2));
    }

    @Test
    public void testAddNameWithJavaRegex() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("text/plain"));
        patterns.add("(?i)example", true, type);
        // FIX (access): names is private; matches() is used instead. The regex is stored as
        // a glob, and matches() applies it to the name
        assertEquals(type, patterns.matches("example"));
    }

    @Test
    public void testAddExtensionWithJavaRegex() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("image/jpeg"));
        patterns.add("(?i)jpg", true, type);
        // FIX (access): extensions is private; matches() is used instead. The regex is stored
        // as a glob, and matches() applies it to the name
        assertEquals(type, patterns.matches("jpg"));
    }

    @Test
    public void testAddGlobWithJavaRegex() throws MimeTypeException {
        MimeType type = new MimeType(MediaType.parse("application/pdf"));
        patterns.add("(?i)\\.pdf$", true, type);
        // FIX (access + oracle): globs is private, and its key is the regex itself, not "*.pdf".
        // matches() applies the regex to the whole name, so the only name it accepts is ".pdf"
        assertEquals(type, patterns.matches(".pdf"));
    }

    // FIX (4 tests removed): testAddNameWithInvalidRegex ("example*"), ...ExtensionWithInvalidRegex
    // ("jpg*") and ...GlobWithInvalidRegex ("*.pdf*") expected a MimeTypeException, but add()
    // accepts all three as globs and never validates patterns. testAddNameWithEmptyString expected
    // an IllegalArgumentException for "", but add() only rejects null and stores "" as a name.
}
