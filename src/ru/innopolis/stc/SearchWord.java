package ru.innopolis.stc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

public class SearchWord implements Runnable {
    InputStream inputStream;
    private Map<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();
    private String[] words;

    public SearchWord(InputStream inputStream, Map<String, ArrayList<String>> findingHashMap, String[] words) {
        this.inputStream = inputStream;
        this.findingHashMap = findingHashMap;
        this.words = words;
    }

    @Override
    public void run() {
        try {
            wait();
            if (inputStream.available() > 0) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String spliter = null;
                    String[] StrArray = null;

                    String newLine = System.getProperty("line.separator");
                    StringBuilder result = new StringBuilder();
                    String line;
                    boolean flag = false;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(flag ? newLine : "").append(line);
                        flag = true;
                    }
                    bufferedReader.close();
                    spliter += result;
                    StrArray = spliter.split("[\\.!?]");
                    for (String w : words) {
                        ArrayList<String> findingString = new ArrayList<>();
                        for (String s : StrArray) {
                            if (s.toLowerCase().contains(w.toLowerCase()))
                                findingString.add(s);
                        }
                        findingHashMap.put(w, findingString);
                    }
                    sleep(1);
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
            }
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
