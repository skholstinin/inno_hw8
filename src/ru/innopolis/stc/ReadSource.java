package ru.innopolis.stc;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadSource implements Runnable {
    InputStream inputStream;
    private int currentItemSources;
    private String[] sources;
    private Map<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();

    public ReadSource(int currentItemSources, String[] sources) {
        this.currentItemSources = currentItemSources;
        this.sources = sources;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void run() {
        if (!sources[currentItemSources].isEmpty()) {
            if ((sources[currentItemSources].regionMatches(true, 0, "ftp", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "www", 0, 3)) ||
                    (sources[currentItemSources].regionMatches(true, 0, "http", 0, 4))) {
                try {
                    URL url = new URL(sources[currentItemSources]);
                    try {
                        inputStream = url.openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    inputStream = new FileInputStream(new File(sources[currentItemSources]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Source № " + currentItemSources + " read is done");
        notifyAll();

    }
}
