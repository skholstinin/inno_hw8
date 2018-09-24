import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
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

    @Override
    public InputStream call() {
        InputStream inputStream = null;
        myLogger.info("Start read " + currentItemSources + " item");
        if (!sources[currentItemSources].isEmpty()) {
            if ((sources[currentItemSources].regionMatches(true, 0, "ftp", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "www", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "http", 0, 4))) {
                try {
                    URL url = new URL(sources[currentItemSources]);
                    try {
                        inputStream = url.openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    inputStream = new FileInputStream(new File(sources[currentItemSources]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return inputStream;
        } else {
            myLogger.error("File " + "№" + currentItemSources + "not found");
            return null;
        }
    }
}
