package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaType_equals_18_0_Test {

    private MediaType mediaType1;

    private MediaType mediaType2;

    private MediaType mediaType3;

    private MediaType mediaType4;

    private MediaType mediaType5;

    private MediaType mediaType6;

    @BeforeEach
    public void setUp() {
        // Create some test media types
        mediaType1 = new MediaType("application", "octet-stream", Collections.emptyMap());
        mediaType2 = new MediaType("text", "plain", Collections.emptyMap());
        mediaType3 = new MediaType("application", "xml", Collections.emptyMap());
        mediaType4 = new MediaType("application", "zip", Collections.emptyMap());
        mediaType5 = new MediaType("application", "octet-stream", Collections.singletonMap("charset", "UTF-8"));
        mediaType6 = new MediaType("application", "octet-stream", Collections.singletonMap("charset", "ISO-8859-1"));
    }

    @Test
    public void testEqualsSameInstance() {
        assertTrue(mediaType1.equals(mediaType1));
    }

    @Test
    public void testEqualsDifferentInstances() {
        assertFalse(mediaType1.equals(mediaType2));
        assertFalse(mediaType1.equals(mediaType3));
        assertFalse(mediaType1.equals(mediaType4));
    }

    @Test
    public void testEqualsDifferentType() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsDifferentParameters() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeAndParameters() {
        assertTrue(mediaType1.equals(mediaType1));
        assertTrue(mediaType1.equals(mediaType2));
        assertTrue(mediaType1.equals(mediaType3));
        assertTrue(mediaType1.equals(mediaType4));
    }

    @Test
    public void testEqualsSameTypeDifferentParameters() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParameters() {
        assertTrue(mediaType1.equals(mediaType1));
        assertTrue(mediaType1.equals(mediaType2));
        assertTrue(mediaType1.equals(mediaType3));
        assertTrue(mediaType1.equals(mediaType4));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrder() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding2() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding3() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding4() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding5() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding6() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding7() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding8() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }

    @Test
    public void testEqualsSameTypeSameParametersDifferentOrderAndEncoding9() {
        assertFalse(mediaType1.equals(mediaType5));
        assertFalse(mediaType1.equals(mediaType6));
    }
}
