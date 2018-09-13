package ru.innopolis.stc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WriteToFile implements Runnable {
    private FindingResource findingResource;
    private String res;

    public WriteToFile(String res, FindingResource findingResource) {
        this.res = res;
        this.findingResource = findingResource;
    }



    @Override
    public void run() {
        File newFile = new File(res);
        if (newFile.isFile() && newFile.exists()) {
            newFile.delete();
        }
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(newFile, true))) {
            System.out.println("File is created");
            for (Map.Entry<String, ArrayList<String>> element : findingResource.getFindingHashMap().entrySet()) {

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
