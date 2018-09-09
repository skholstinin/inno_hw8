package ru.innopolis.stc;

import java.io.IOException;

public class ReadAndParceData extends Thread {
    getOccurencies getOccurencies;

    public ReadAndParceData(getOccurencies getOccurencies, int currentItemSource) {
        this.getOccurencies = getOccurencies;
        this.getOccurencies.setCurrentItemSources(currentItemSource);
    }

    @Override
    public void run() {
        try {

            while (getOccurencies.readParceString()) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
