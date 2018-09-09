package ru.innopolis.stc;

public class WriteToFile extends Thread {
    getOccurencies getOccurencies;

    public WriteToFile(getOccurencies getOccurencies) {
        this.getOccurencies = getOccurencies;
    }

    @Override
    public void run() {
        try {

            getOccurencies.writeToFile();
            sleep(1);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
