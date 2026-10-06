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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class MediaType_image_2_0_Test {

    // FIX (setup): Mockito fields and setUp() removed; @InjectMocks cannot build a MediaType,
    // which made every test fail before it started
    @Test
    public void testImageWithValidType() throws Exception {
        // Arrange
        String type = "png";
        MediaType expected = MediaType.parse("image/png");
        // Act
        MediaType result = (MediaType) MediaType.image(type);
        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void testImageWithEmptyType() throws Exception {
        // Arrange
        String type = "";
        // Act
        MediaType result = (MediaType) MediaType.image(type);
        // Assert
        // FIX (oracle): "image/" has an empty subtype, so parse() returns null (no "image/x-empty")
        assertNull(result);
    }

    // FIX (test removed): testImageWithInvalidType expected null, but Tika does not check subtypes
    // against a registry: image("invalid") returns image/invalid

    // FIX (test removed): testImageWithNullType expected null, but image(null) returns image/null
    // (string concatenation in Tika); recorded as a finding, not asserted

    // FIX (call): reflection helper removed; image(String) is public and is now called directly
}
