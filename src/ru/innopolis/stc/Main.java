package ru.innopolis.stc;

import java.io.IOException;




public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] source = new String[1];
        String[] words = new String[100];

        for (int i = 0; i < 1; i++) {
            source[i] = "master.txt";
        }

        getOccurencies getOccurencies = new getOccurencies(source, words, "result.txt");
        ReadAndParceData readData = new ReadAndParceData(getOccurencies);
        WriteToFile writeData = new WriteToFile(getOccurencies);
        writeData.start();
        readData.start();

    }
}
