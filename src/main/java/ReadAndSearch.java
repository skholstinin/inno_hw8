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
    private static int cntMatch = 0;


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

    public int searchCurrentWord(String word, ArrayList<String> listString) {
        ArrayList<String> findingString = new ArrayList<>();
        for (String s : listString) {
            if (s.toLowerCase().contains(word.toLowerCase())) {
                findingString.add(s + ".\r\n");
                flagFindWord = true;
                cntMatch++;
            }
        }
        if (flagFindWord) {
            flagFindWord = false;
            findingResource.addItemToQueue(findingString);
        }
        return cntMatch;
    }

    public ArrayList<String> separateLongStringToListSentence(StringBuilder result) {
        ArrayList<String> stringList = new ArrayList<>();
        String spliter;
        String[] strArray;
        spliter = result.toString();
        strArray = spliter.split("[\\.!?]");
        stringList.addAll(Arrays.asList(strArray));
        return stringList;
    }

    @Override
    public Integer call() {
        int finalLength = 0;
        int cntMatchCurrent;
        ArrayList<String> stringList = new ArrayList<>();
        int length = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            length = inputStream.available();
            myLogger.info("Read file size " + length);
            while (length > 0) {
                stringList.clear();
                StringBuilder result = new StringBuilder();
                finalLength = separateBufferToString(bufferedReader, result);
                stringList.addAll(separateLongStringToListSentence(result));
                for (String w : words) {
                    cntMatchCurrent = searchCurrentWord(w, stringList);
                    myLogger.info("Match " + cntMatchCurrent);
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
