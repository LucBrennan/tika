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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Hand-written tests for {@link Patterns#matches(String)} and the pattern kinds that
 * {@link Patterns#add(String, boolean, MimeType)} creates.
 */
public class PatternsHandWrittenTest {

    private Patterns patterns;

    @BeforeEach
    public void setUp() {
        patterns = new Patterns(new MediaTypeRegistry());
    }

    private static MimeType type(String name) {
        return new MimeType(MediaType.parse(name));
    }

    @Test
    public void exactNameWinsOverExtensionPattern() throws MimeTypeException {
        MimeType xml = type("application/xml");
        MimeType pom = type("application/x-maven-pom");
        patterns.add("*.xml", xml);
        patterns.add("pom.xml", pom);

        assertSame(pom, patterns.matches("pom.xml"));
        assertSame(xml, patterns.matches("other.xml"));
    }

    @Test
    public void conflictingGlobWithUnrelatedTypeThrows() throws MimeTypeException {
        patterns.add("backup*.tar", type("application/x-backup"));

        assertThrows(MimeTypeException.class,
                () -> patterns.add("backup*.tar", type("application/x-archive")));
    }
}
