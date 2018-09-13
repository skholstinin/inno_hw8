package ru.innopolis.stc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WriteToFile implements Runnable {
    private String[] sources;
    private String[] words;
    private String res;
    private Map<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();

    public WriteToFile(String res, Map<String, ArrayList<String>> findingHashMap) {
        this.res = res;
        this.findingHashMap = findingHashMap;
    }

    public void setFindingHashMap(Map<String, ArrayList<String>> findingHashMap) {
        this.findingHashMap = findingHashMap;
    }

    @Override
    public void run() {
        File newFile = new File(res);
        if (newFile.isFile() && newFile.exists()) {
            newFile.delete();
        }
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(newFile, true))) {
            System.out.println("File is created");
            for (Map.Entry<String, ArrayList<String>> element : findingHashMap.entrySet()) {

                bufferWriter.write("\r\nСлово " + element.getKey() + " содержится в предложениях: \r\n");
                for (String s : element.getValue()) {
                    bufferWriter.write(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
