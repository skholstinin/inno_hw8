package ru.innopolis.stc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToFile {
    private FindingResource findingResource;
    private String res;

    public WriteToFile(String res, FindingResource findingResource) {
        this.res = res;
        this.findingResource = findingResource;
    }

    public void run() {
        File newFile = new File(res);
        if (newFile.isFile() && newFile.exists()) {
            newFile.delete();
        }
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(newFile, true))) {
            System.out.println("File is created");
            ArrayList<String> item;
            while ((item = findingResource.getQueueItem()) != null) {
                for (String s : item) {
                    bufferWriter.write(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
