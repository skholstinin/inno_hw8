package ru.innopolis.stc;

public class ParceWriteData extends Thread {
    getOccurencies getOccurencies;

    public ParceWriteData(getOccurencies getOccurencies) {
        this.getOccurencies = getOccurencies;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                getOccurencies.findWord();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
