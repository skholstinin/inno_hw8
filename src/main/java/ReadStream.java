import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;


public class ReadStream implements Callable<StringBuilder> {
    static final Logger myLogger = Logger.getLogger(ReadStream.class);
    private final int MAX_READ_SIZE = 10_000_000;
    private InputStream inputStream;
    private String[] words;
    private int finalLength = 0;
    private int length = 0;
    private StringBuilder result = null;

    public ReadStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    @Override
    public StringBuilder call() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            length = inputStream.available();
            myLogger.info("Read file size " + length);
            result = new StringBuilder();
            while (length > 0) {
                String newLine = System.getProperty("line.separator");
                String line;
                boolean flag = false;
                while ((line = bufferedReader.readLine()) != null && finalLength < MAX_READ_SIZE) {
                    finalLength += line.length();
                    result.append(flag ? newLine : "").append(line);
                    flag = true;
                }
                length -= finalLength;
            }
            //finalLength=0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
