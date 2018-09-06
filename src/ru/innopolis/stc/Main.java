package ru.innopolis.stc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int BUFFER_SIZE = 1000;
        String[] source = new String[BUFFER_SIZE];
        String[] words = new String[1];

        for (int i = 0; i < BUFFER_SIZE; i++) {
            source[i] = "master.txt";

        }
        words[0] = "Маргарита";
        //source[1] = "https://ru.wikipedia.org/wiki/Мастер_и_Маргарита";

        getOccurencies getOccurencies = new getOccurencies(source, words, "result.txt");
        //ReadAndParceData readData = new ReadAndParceData(getOccurencies);


        List<Thread> threadReadList = new ArrayList<>();
        List<Thread> threadWriteList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReadAndParceData readData = new ReadAndParceData(getOccurencies);
            WriteToFile writeData = new WriteToFile(getOccurencies);
            readData.start();
            writeData.start();
            threadWriteList.add(writeData);
            threadReadList.add(readData);
        }
        for (Thread thread : threadReadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The End");
    }
}
