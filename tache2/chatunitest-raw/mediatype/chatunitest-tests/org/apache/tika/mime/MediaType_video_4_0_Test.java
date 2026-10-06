package org.apache.tika.mime;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.tika.mime.MediaType;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
