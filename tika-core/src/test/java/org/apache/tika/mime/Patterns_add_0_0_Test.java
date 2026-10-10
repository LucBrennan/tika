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
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Patterns_add_0_0_Test {

    private Patterns patterns;

    private MediaTypeRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = mock(MediaTypeRegistry.class);
        patterns = new Patterns(registry);
    }

    @Test
    public void testAddValidPattern() throws MimeTypeException {
        MimeType type = mock(MimeType.class);
        // FIX (oracle): getName()/getExtension() stubs and their verify() calls removed;
        // Patterns.add() never calls them (strict stubs also flagged the stubs as unused)
        patterns.add("text/plain", type);
        // FIX (access): names is private; matches() returns the exact-name entry
        assertEquals(type, patterns.matches("text/plain"));
        // FIX (oracle): extensions assertion removed; "text/plain" has no '*', so it is
        // stored as a name pattern and no "txt" extension exists
    }

    @Test
    public void testAddValidJavaRegexPattern() throws MimeTypeException {
        MimeType type = mock(MimeType.class);
        // FIX (oracle): getName()/getExtension() stubs and their verify() calls removed;
        // Patterns.add() never calls them (strict stubs also flagged the stubs as unused)
        patterns.add("application/pdf", true, type);
        // FIX (access): names is private; matches() is used instead. The pattern is stored
        // as a Java regex glob, which matches() also consults
        assertEquals(type, patterns.matches("application/pdf"));
        // FIX (oracle): extensions assertion removed; a Java regex is never an extension
    }

    @Test
    public void testAddNamePattern() throws MimeTypeException {
        MimeType type = mock(MimeType.class);
        // FIX (oracle): getName()/getExtension() stubs and their verify() calls removed;
        // Patterns.add() never calls them (strict stubs also flagged the stubs as unused)
        patterns.add("image/png", type);
        // FIX (access): names is private; matches() returns the exact-name entry
        assertEquals(type, patterns.matches("image/png"));
        // FIX (oracle): extensions assertion removed; "image/png" is a name pattern
    }

    @Test
    public void testAddExtensionPattern() throws MimeTypeException {
        MimeType type = mock(MimeType.class);
        // FIX (oracle): getName()/getExtension() stubs and their verify() calls removed;
        // Patterns.add() never calls them (strict stubs also flagged the stubs as unused)
        patterns.add("application/octet-stream", type);
        // FIX (access): names is private; matches() returns the exact-name entry
        assertEquals(type, patterns.matches("application/octet-stream"));
        // FIX (oracle): extensions assertion removed; "application/octet-stream" is a name pattern
    }

    @Test
    public void testAddGlobPattern() throws MimeTypeException {
        MimeType type = mock(MimeType.class);
        // FIX (oracle): getName()/getExtension() stubs and their verify() calls removed;
        // Patterns.add() never calls them (strict stubs also flagged the stubs as unused)
        patterns.add("text/html", type);
        // FIX (access): names is private; matches() returns the exact-name entry
        assertEquals(type, patterns.matches("text/html"));
        // FIX (oracle): extensions assertion removed; "text/html" is a name pattern
    }

    // FIX (test removed): testAddPatternWithInvalidCharacter expected a MimeTypeException for
    // "invalid*pattern", but add() accepts it as a glob and never validates patterns. It also
    // passed a String to MimeType(MediaType), which does not compile.

    @Test
    public void testAddPatternWithMissingType() throws MimeTypeException {
        try {
            patterns.add("image/jpeg", null);
            fail("Expected IllegalArgumentException for missing type");
        } catch (IllegalArgumentException e) {
            assertEquals("Pattern and/or mime type is missing", e.getMessage());
        }
    }

    @Test
    public void testAddPatternWithNullPattern() throws MimeTypeException {
        try {
            // FIX (compile): MimeType takes a MediaType, not a String
            patterns.add(null, new MimeType(MediaType.parse("application/json")));
            fail("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException e) {
            assertEquals("Pattern and/or mime type is missing", e.getMessage());
        }
    }
}
