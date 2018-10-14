import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class GetStreamTest {
    InputStream is = new ByteArrayInputStream("test data".getBytes());

    @Test
    void testCall() throws IOException {
        GetStream mocGetStream = spy(new GetStream(new String[]{"http:\\somelink", "http:\\yetanotherlink"}, 0));
        URL mocUrl = mock(URL.class);
        when(mocGetStream.createInputStreamFromUrl(anyString())).thenReturn(is);
//        when(mocGetStream.createInputStreamFromFile(anyString())).thenReturn(is);
//        when(mocGetStream.call()).thenReturn(is);
//        mocGetStream.call();
//        verify(mocGetStream).call();
//        InputStream fileStream = mocGetStream.createInputStreamFromFile("c:\\somepath");
//        InputStream urlStream = mocGetStream.createInputStreamFromUrl("http:\\someurl");
//        verify(mocGetStream,times(1)).createInputStreamFromFile(anyString());
//        verify(mocGetStream,times(1)).createInputStreamFromUrl(anyString());
//        assertEquals(is,fileStream);
//        assertEquals(is,urlStream);

        //verify(mocGetStream,atLeastOnce()).createInputStreamFromUrl(anyString());
        //mocGetStream.call();
        //assertEquals(is,getStream.call());
    }

}
