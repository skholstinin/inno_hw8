package ru.innopolis.stc;

import java.io.IOException;




public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] source = new String[100];
        String[] words = new String[100];

        for (int i = 0; i < 100; i++) {
            source[i] = "master.txt";
        }

        getOccurencies getOccurencies = new getOccurencies(source, words, "result.txt");
        ReadData readData = new ReadData(getOccurencies);
        ParceWriteData parceWriteData = new ParceWriteData(getOccurencies);
        readData.start();
        parceWriteData.start();

    }
}
