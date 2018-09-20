package ru.innopolis.stc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class SearchWord implements Runnable {
    private String[] words;
    private FindingResource findingResource;
    private int currentStream;
    private final int MAX_READ_SIZE = 10_000_000;
    private int finalLength = 0;

    public SearchWord(FindingResource findingResource, String[] words, int currentStream) {
        this.words = words;
        this.findingResource = findingResource;
        this.currentStream = currentStream;
    }

    @Override
    public void run() {
        int length = 0;
        try {
            while (findingResource.getInputStreamsList().size() < currentStream + 1) {
                sleep(1);
            }
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findingResource.getInputStreamsList().get(currentStream)))) {
                length = findingResource.getInputStreamsList().get(currentStream).available();
                while (length > 0) {
                    String spliter = null;
                    String[] StrArray = null;
                    String newLine = System.getProperty("line.separator");
                    StringBuilder result = new StringBuilder();
                    String line;
                    boolean flag = false;
                    while ((line = bufferedReader.readLine()) != null && finalLength < MAX_READ_SIZE) {
                        finalLength += line.length();
                        result.append(flag ? newLine : "").append(line);
                        flag = true;
                    }
                    spliter = result.toString();
                    StrArray = spliter.split("[\\.!?]");
                    for (String w : words) {
                        ArrayList<String> findingString = new ArrayList<>();
                        for (String s : StrArray) {
                            if (s.toLowerCase().contains(w.toLowerCase())) {
                                findingString.add(s);
                            }
                        }
                        findingResource.addItemToQueue(findingString);
                        length -= finalLength;
                        spliter = null;
                    }
                }
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
