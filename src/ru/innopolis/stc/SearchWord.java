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

    public SearchWord(FindingResource findingResource, String[] words, int currentStream) {
        this.words = words;
        this.findingResource = findingResource;
        this.currentStream = currentStream;
    }

    @Override
    public void run() {
        try {
            //wait();
            try {
                if (findingResource.getInputStreams().get(currentStream).available() > 0) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(findingResource.getInputStreams().get(currentStream)))) {
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
                            findingResource.setFindingHashMap(w, findingString);
                            System.out.println("Find one string");
                        }
                        sleep(1);
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                } else {
                    sleep(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
