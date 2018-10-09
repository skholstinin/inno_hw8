import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestGetStream {
    private GetStream getStream;
    private String[] source;
    private String path;


    @BeforeEach
    void initTest() {
        source = new String[1];
        source[0] = "sources";
        path = new String("path");
        getStream = new GetStream(source, path);
    }

    @Test
    void testCreateInputStreamFromFile() {
        try {
            when(getStream.createInputStreamFromFile(anyString())).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateInputStreamFromUrl() {
        try {
            when(getStream.createInputStreamFromUrl(anyString())).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCall() {
        try {
            InputStream is = getStream.call();
            verify(getStream.createInputStreamFromFile(anyString()));
            assertEquals(new ByteArrayInputStream("test data".getBytes()), is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
