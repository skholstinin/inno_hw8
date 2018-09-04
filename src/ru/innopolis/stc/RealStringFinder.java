package ru.innopolis.stc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RealStringFinder implements StringFinder {

    BlockingQueue<String> drop = new ArrayBlockingQueue(1, true);

    @Override
    public void getOccurencies(String[] sources, String[] words, String res) {

    }
}
