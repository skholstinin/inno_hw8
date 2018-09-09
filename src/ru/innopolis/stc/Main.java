package ru.innopolis.stc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int BUFFER_SIZE = 10;
        String[] source = new String[BUFFER_SIZE];
        String[] words = new String[10];

        for (int i = 0; i < BUFFER_SIZE; i++) {
            //source[i] = "http://www.gutenberg.org/cache/epub/6130/pg6130.txt";
            source[i] = "master.txt";

        }
        words[0] = "война";
        words[1] = "Мастер";
        words[2] = "Бегемот";
        words[3] = "Берлиоз";
        words[4] = "Никанор";
        words[5] = "Бездомный";
        words[6] = "Вечер";
        words[7] = "День";
        words[8] = "Ночь";
        words[9] = "Аннушка";




        getOccurencies getOccurencies = new getOccurencies(source, words, "result.txt");
        List<Thread> threadReadList = new ArrayList<>();
        List<Thread> threadWriteList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {

            ReadAndParceData readData = new ReadAndParceData(getOccurencies, i);
            WriteToFile writeData = new WriteToFile(getOccurencies);
            writeData.start();
            readData.start();
            threadReadList.add(readData);
            threadWriteList.add(writeData);
        }
        for (Thread thread : threadReadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("\r\nЗатрачено временя: " + endTime + "\r\n");
        System.out.println("The End");
    }
}
