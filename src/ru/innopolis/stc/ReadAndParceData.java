package ru.innopolis.stc;

import java.io.IOException;

public class ReadAndParceData extends Thread {
    getOccurencies getOccurencies;

    public ReadAndParceData(getOccurencies getOccurencies) {
        this.getOccurencies = getOccurencies;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                getOccurencies.readParceString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
