package org.apache.tika.mime;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class MediaType_compareTo_20_0_Test {

    @InjectMocks
    private MediaType mediaType;

    @Mock
    private MediaType otherMediaType;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mediaType = new MediaType("text/plain", "UTF-8");
        otherMediaType = new MediaType("text/plain", "UTF-8");
    }

    @Test
    public void testCompareTo() throws Exception {
        assertEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentType() throws Exception {
        otherMediaType = new MediaType("application/xml", "UTF-8");
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentSubtype() throws Exception {
        otherMediaType = new MediaType("text/plain", "ISO-8859-1");
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentParameters() throws Exception {
        otherMediaType = new MediaType("text/plain", "UTF-8", Map.of("charset", "ISO-8859-1"));
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }

    @Test
    public void testCompareToDifferentOrderParameters() throws Exception {
        otherMediaType = new MediaType("text/plain", "UTF-8", Map.of("charset", "ISO-8859-1", "name", "value"));
        assertNotEquals(0, mediaType.compareTo(otherMediaType));
    }
}
