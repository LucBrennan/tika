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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class MediaType_video_4_0_Test {

    @Test
    public void testVideoMethod() throws Exception {
        // Create an instance of MediaType
        MediaType mediaType = MediaType.parse("video/mp4");
        // Get the private method 'video(String)'
        Method videoMethod = MediaType.class.getDeclaredMethod("video", String.class);
        videoMethod.setAccessible(true);
        // Invoke the private method with the argument "mp4"
        MediaType result = (MediaType) videoMethod.invoke(null, "mp4");
        // Verify the result
        assertEquals("video/mp4", result.toString());
        assertTrue(result.getParameters().isEmpty());
    }
}
