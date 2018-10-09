import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;


public class ReadAndSearch implements Callable<Integer> {
    static final Logger myLogger = Logger.getLogger(ReadAndSearch.class);
    private static final int MAX_READ_SIZE = 100_000_000;
    FindingResource findingResource = null;
    ArrayList<StringBuilder> listStringBuilder = new ArrayList<>();
    private InputStream inputStream;
    private String[] words;
    private boolean flagFindWord = false;


    public ReadAndSearch(InputStream inputStream, FindingResource findingResource, String[] words) {
        this.findingResource = findingResource;
        this.words = words;
        this.inputStream = inputStream;
    }

    public int separateBufferToString(BufferedReader bufferedReader, StringBuilder result) throws IOException {
        String newLine = System.getProperty("line.separator");
        String line;
        boolean flag = false;
        int length = 0;
        while (length < MAX_READ_SIZE && (line = bufferedReader.readLine()) != null) {
            length += line.length();
            result.append(flag ? newLine : "").append(line);
            flag = true;
        }
        return length;
    }


    @Override
    public Integer call() {
        int finalLength = 0;
        ArrayList<String> listString = new ArrayList<>();
        int length = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            length = inputStream.available();
            myLogger.info("Read file size " + length);
            while (length > 0) {
                listString.clear();
                String[] strArray;
                String spliter;
                StringBuilder result = new StringBuilder();
                finalLength = separateBufferToString(bufferedReader, result);
                spliter = result.toString();
                strArray = spliter.split("[\\.!?]");
                listString.addAll(Arrays.asList(strArray));
                ArrayList<String> findingString = new ArrayList<>();
                for (String w : words) {
                    for (String s : listString) {
                        if (s.toLowerCase().contains(w.toLowerCase())) {
                            findingString.add(s + ".\r\n");
                            flagFindWord = true;
                        }
                    }
                    if (flagFindWord) {
                        flagFindWord = false;
                        findingResource.addItemToQueue(findingString);
                    }
                }
                if (finalLength == 0) {
                    myLogger.info("End of file");
                    break;
                }
                length -= finalLength;
                myLogger.info("Change file size. New size " + length);
                finalLength = 0;
            }
        } catch (IOException e) {
            myLogger.error(e);
        }
        return 1;
    }
}
