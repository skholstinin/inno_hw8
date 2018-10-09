import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.concurrent.Callable;

public class GetStream implements Callable<InputStream> {
    static final Logger myLogger = Logger.getLogger(GetStream.class);
    private int currentItemSources;
    private String[] sources;

    public GetStream(String[] sources, int currentItemSources) {
        this.currentItemSources = currentItemSources;
        this.sources = sources;
    }

    public GetStream(String[] sources, String path) {
    }

    public InputStream createInputStreamFromUrl(String path) throws IOException {
        return new URL(path).openStream();
    }

    public InputStream createInputStreamFromFile(String path) throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }


    @Override
    public InputStream call() throws IOException {
        myLogger.info("Start read " + currentItemSources + " item");
        if (!sources[currentItemSources].isEmpty()) {
            if ((sources[currentItemSources].regionMatches(true, 0, "ftp", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "www", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "http", 0, 4))) {
                return createInputStreamFromUrl(sources[currentItemSources]);
            } else {
                return createInputStreamFromFile(sources[currentItemSources]);
            }
        } else {
            return null;
        }
    }
}

