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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Hand-written tests for {@link Patterns#matches(String)} and the pattern kinds that
 * {@link Patterns#add(String, boolean, MimeType)} creates (name, extension, glob, Java regex).
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
    public void exactNameBeatsExtension() throws MimeTypeException {
        MimeType text = type("text/plain");
        MimeType cmake = type("text/x-cmake");
        patterns.add("*.txt", text);
        patterns.add("CMakeLists.txt", cmake);

        assertSame(cmake, patterns.matches("CMakeLists.txt"));
        assertSame(text, patterns.matches("notes.txt"));
    }

    @Test
    public void longestExtensionWins() throws MimeTypeException {
        MimeType gzip = type("application/gzip");
        MimeType tarGzip = type("application/x-gtar");
        patterns.add("*.gz", gzip);
        patterns.add("*.tar.gz", tarGzip);

        assertSame(tarGzip, patterns.matches("backup.tar.gz"));
        assertSame(gzip, patterns.matches("backup.gz"));
    }

    @Test
    public void shortestExtensionIsStillTriedAndShorterNamesGiveNull() throws MimeTypeException {
        MimeType gzip = type("application/gzip");
        patterns.add("*.gz", gzip);
        patterns.add("*.html", type("text/html"));

        // ".gz" has the minimum extension length (3): it must still be tried
        assertSame(gzip, patterns.matches("a.gz"));
        // names shorter than every extension must not throw
        assertNull(patterns.matches("gz"));
        assertNull(patterns.matches(""));
    }

    @Test
    public void noPatternsGiveNull() {
        assertNull(patterns.matches("anything.txt"));
        assertNull(patterns.matches(""));
    }

    @Test
    public void questionMarkMatchesExactlyOneCharacter() throws MimeTypeException {
        MimeType log = type("text/x-log");
        patterns.add("app.log.?", log);

        assertSame(log, patterns.matches("app.log.1"));
        assertNull(patterns.matches("app.log."));
        assertNull(patterns.matches("app.log.10"));
    }

    @Test
    public void starMatchesAnyNumberOfCharacters() throws MimeTypeException {
        MimeType readme = type("text/x-readme");
        patterns.add("README*", readme);

        assertSame(readme, patterns.matches("README"));
        assertSame(readme, patterns.matches("README.md"));
        // the glob is anchored at the start of the name
        assertNull(patterns.matches("MY-README"));
    }

    @Test
    public void regexCharactersInAGlobAreMatchedLiterally() throws MimeTypeException {
        MimeType data = type("application/x-data");
        patterns.add("data.?", data);
        MimeType odd = type("application/x-odd");
        patterns.add("a\\b[c]^d.e-f$g+h(i){j}|k?", odd);

        assertSame(data, patterns.matches("data.1"));
        // '.' is not "any character"
        assertNull(patterns.matches("dataX1"));
        assertSame(odd, patterns.matches("a\\b[c]^d.e-f$g+h(i){j}|kZ"));
    }

    @Test
    public void javaRegexIsUsedAsIs() throws MimeTypeException {
        MimeType log = type("text/x-log");
        patterns.add("[a-z]+\\.log\\.[0-9]+", true, log);

        assertSame(log, patterns.matches("server.log.12"));
        assertNull(patterns.matches("Server.log.12"));
        assertNull(patterns.matches("server.log"));
    }

    @Test
    public void sameStringAddedAsAGlobIsMatchedLiterally() throws MimeTypeException {
        MimeType log = type("text/x-log");
        patterns.add("[a-z]+\\.log\\.[0-9]+", false, log);

        assertNull(patterns.matches("server.log.12"));
        assertSame(log, patterns.matches("[a-z]+\\.log\\.[0-9]+"));
    }

    @Test
    public void conflictingPatternsThrow() throws MimeTypeException {
        // with a real registry, two application/* types are unrelated (both only extend
        // application/octet-stream), so neither can replace the other
        patterns.add("README", type("application/x-readme"));
        patterns.add("*.doc", type("application/msword"));
        patterns.add("draft-?.doc", type("application/x-draft"));

        assertThrows(MimeTypeException.class,
                () -> patterns.add("README", type("application/x-other")));
        assertThrows(MimeTypeException.class,
                () -> patterns.add("*.doc", type("application/x-other")));
        assertThrows(MimeTypeException.class,
                () -> patterns.add("draft-?.doc", type("application/x-other")));
    }

    @Test
    public void samePatternAndSameTypeTwiceIsNotAConflict() throws MimeTypeException {
        MimeType draft = type("application/x-draft");
        patterns.add("draft-?.doc", draft);
        patterns.add("draft-?.doc", draft);

        assertSame(draft, patterns.matches("draft-1.doc"));
    }

    @Test
    public void matchingIsCaseSensitive() throws MimeTypeException {
        MimeType text = type("text/plain");
        patterns.add("*.txt", text);
        patterns.add("Makefile", type("text/x-makefile"));

        assertSame(text, patterns.matches("notes.txt"));
        // the lower-case retry is done by MimeTypes, not by Patterns
        assertNull(patterns.matches("NOTES.TXT"));
        assertNull(patterns.matches("makefile"));
    }
}
