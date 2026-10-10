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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class Patterns_matches_5_0_Test {

    private Patterns patterns;

    private MediaTypeRegistry registry;

    @BeforeEach
    public void setUp() {
        // FIX (setup): the mock registry and its two stubs are replaced by a real registry.
        // getDefaultRegistry() is static and cannot be stubbed, so when() threw in every test.
        // Patterns never calls getTypes() either.
        registry = new MediaTypeRegistry();
        patterns = new Patterns(registry);
    }

    // FIX (compile): add() throws a checked MimeTypeException
    @Test
    public void testMatchesExactMatch() throws MimeTypeException {
        MimeType mimeType = mock(MimeType.class);
        // FIX (setup): stubs on getName() and registry.getTypes() replaced by add(); Patterns only
        // knows the patterns given to add(), and never calls getName() or getTypes()
        patterns.add("example", mimeType);
        assertEquals(mimeType, patterns.matches("example"));
    }

    // FIX (compile): add() throws a checked MimeTypeException
    @Test
    public void testMatchesExtensionMatch() throws MimeTypeException {
        MimeType mimeType = mock(MimeType.class);
        // FIX (setup): stubs on getName(), getExtension() and registry.getTypes() replaced by
        // add() of an extension pattern that "example.txt" ends with
        patterns.add("*.txt", mimeType);
        assertEquals(mimeType, patterns.matches("example.txt"));
    }

    // FIX (compile): add() throws a checked MimeTypeException
    @Test
    public void testMatchesGlobMatch() throws MimeTypeException {
        MimeType mimeType = mock(MimeType.class);
        // FIX (setup): stubs on getName(), getExtension() and registry.getTypes() replaced by
        // add() of a glob pattern that matches "example.txt"
        patterns.add("example.*", mimeType);
        assertEquals(mimeType, patterns.matches("example.txt"));
    }

    @Test
    public void testMatchesNullName() {
        assertThrows(IllegalArgumentException.class, () -> patterns.matches(null));
    }

    @Test
    public void testMatchesNonExistingName() {
        assertNull(patterns.matches("non-existing"));
    }

    @Test
    public void testMatchesEmptyName() {
        assertNull(patterns.matches(""));
    }
}
