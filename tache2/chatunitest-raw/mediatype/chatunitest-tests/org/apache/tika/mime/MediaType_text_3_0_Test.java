package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import java.lang.reflect.Method;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaType_text_3_0_Test {

    @Test
    public void testTextMethod() throws Exception {
        // Given
        String type = "plain";
        MediaType expected = MediaType.TEXT_PLAIN;
        // When
        MediaType result = MediaType.text(type);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethod() throws Exception {
        // Given
        String string = "text/plain";
        MediaType expected = MediaType.TEXT_PLAIN;
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethodWithParameters() throws Exception {
        // Given
        String string = "text/plain; charset=utf-8";
        MediaType expected = new MediaType("text", "plain", Collections.singletonMap("charset", "utf-8"));
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethodWithMultipleParameters() throws Exception {
        // Given
        String string = "text/plain; charset=utf-8; language=en";
        MediaType expected = new MediaType("text", "plain", Collections.singletonMap("charset", "utf-8"));
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testParseMethodWithInvalidType() throws Exception {
        // Given
        String string = "invalid/type";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidSubtype() throws Exception {
        // Given
        String string = "text/invalid";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameter() throws Exception {
        // Given
        String string = "text/plain; invalid=parameter";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterName() throws Exception {
        // Given
        String string = "text/plain; charset=;";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue() throws Exception {
        // Given
        String string = "text/plain; charset=\\";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue2() throws Exception {
        // Given
        String string = "text/plain; charset=\\u";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue3() throws Exception {
        // Given
        String string = "text/plain; charset=\\u00";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue4() throws Exception {
        // Given
        String string = "text/plain; charset=\\u000";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue5() throws Exception {
        // Given
        String string = "text/plain; charset=\\u0000";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue6() throws Exception {
        // Given
        String string = "text/plain; charset=\\u00000";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue7() throws Exception {
        // Given
        String string = "text/plain; charset=\\u000000";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }

    @Test
    public void testParseMethodWithInvalidParameterValue8() throws Exception {
        // Given
        String string = "text/plain; charset=\\u0000000";
        // When
        MediaType result = MediaType.parse(string);
        // Then
        assertNull(result);
    }
}
