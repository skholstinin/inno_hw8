package ru.innopolis.stc;

import java.io.IOException;

public class ReadData extends Thread {
    getOccurencies getOccurencies;

    public ReadData(getOccurencies getOccurencies) {
        this.getOccurencies = getOccurencies;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                getOccurencies.readString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
