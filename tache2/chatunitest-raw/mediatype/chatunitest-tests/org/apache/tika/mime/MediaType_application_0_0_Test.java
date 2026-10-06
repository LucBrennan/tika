package org.apache.tika.mime;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class MediaType_application_0_0_Test {

    @Test
    public void testApplication() {
        MediaType type = MediaType.application("json");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }

    @Test
    public void testParse() {
        MediaType type = MediaType.parse("application/json");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("application/json; charset=utf-8");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("utf-8", type.getParameters().get("charset"));
    }

    @Test
    public void testParseSimple() {
        MediaType type = MediaType.parse("application/octet-stream");
        assertEquals("application/octet-stream", type.toString());
        assertEquals("octet-stream", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("text/plain");
        assertEquals("text/plain", type.toString());
        assertEquals("plain", type.getSubtype());
        assertEquals("text", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }

    @Test
    public void testParseWithParameters() {
        MediaType type = MediaType.parse("application/json; charset=utf-8; foo=bar");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("utf-8", type.getParameters().get("charset"));
        assertEquals("bar", type.getParameters().get("foo"));
    }

    @Test
    public void testParseInvalid() {
        assertNull(MediaType.parse(null));
        assertNull(MediaType.parse(""));
        assertNull(MediaType.parse("application/"));
        assertNull(MediaType.parse("application/json/"));
        assertNull(MediaType.parse("application/json;"));
        assertNull(MediaType.parse("application/json; charset=utf-8;"));
        assertNull(MediaType.parse("application/json; charset=utf-8; foo"));
    }

    @Test
    public void testParseWithDuplicateParameters() {
        MediaType type = MediaType.parse("application/json; charset=utf-8; charset=utf-16");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("utf-8", type.getParameters().get("charset"));
    }

    @Test
    public void testParseWithSpecialCharacters() {
        MediaType type = MediaType.parse("application/json; foo=bar; baz=\\\"qux\\\"");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertEquals("bar", type.getParameters().get("foo"));
        assertEquals("\"qux\"", type.getParameters().get("baz"));
    }

    @Test
    public void testParseWithEmptyParameters() {
        MediaType type = MediaType.parse("application/json; ");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
        type = MediaType.parse("application/json; ; ");
        assertEquals("application/json", type.toString());
        assertEquals("json", type.getSubtype());
        assertEquals("application", type.getType());
        assertTrue(type.getParameters().isEmpty());
    }
}
