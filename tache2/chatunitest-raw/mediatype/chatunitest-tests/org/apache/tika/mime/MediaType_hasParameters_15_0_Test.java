package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaType_hasParameters_15_0_Test {

    @Test
    public void testHasParametersWithEmptyString() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", ""));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "1.0;"));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInValue() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKey() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0"));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInBothKeyAndValue() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharactersAndWhitespace() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }

    @Test
    public void testHasParametersWithSpecialCharactersInKeyAndValueAndWhitespaceAndSpecialCharactersAndWhitespaceAndSpecialCharacters() {
        MediaType mediaType = new MediaType("application/json", "v1", Collections.singletonMap("version;1", "1.0;  "));
        assertTrue(mediaType.hasParameters());
    }
}
