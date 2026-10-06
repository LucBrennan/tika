package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class MediaType_image_2_0_Test {

    @InjectMocks
    private MediaType mediaType;

    @Mock
    private Map<String, String> parameters;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testImageWithValidType() throws Exception {
        // Arrange
        String type = "png";
        MediaType expected = MediaType.parse("image/png");
        // Act
        MediaType result = (MediaType) invokePrivateMethod(mediaType, "image", type);
        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void testImageWithEmptyType() throws Exception {
        // Arrange
        String type = "";
        MediaType expected = MediaType.parse("image/x-empty");
        // Act
        MediaType result = (MediaType) invokePrivateMethod(mediaType, "image", type);
        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void testImageWithInvalidType() throws Exception {
        // Arrange
        String type = "invalid";
        // Act
        MediaType result = (MediaType) invokePrivateMethod(mediaType, "image", type);
        // Assert
        assertNull(result);
    }

    @Test
    public void testImageWithNullType() throws Exception {
        // Arrange
        String type = null;
        // Act
        MediaType result = (MediaType) invokePrivateMethod(mediaType, "image", type);
        // Assert
        assertNull(result);
    }

    private Object invokePrivateMethod(Object obj, String methodName, Object... args) throws Exception {
        Class<?> clazz = obj.getClass();
        java.lang.reflect.Method method = clazz.getDeclaredMethod(methodName, args.getClass());
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
}
