package ru.innopolis.stc;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadSource implements Runnable {
    InputStream inputStream;
    private int currentItemSources;
    private String[] sources;
    FindingResource findingResource;

    public ReadSource(int currentItemSources, String[] sources, FindingResource findingResource) {
        this.currentItemSources = currentItemSources;
        this.sources = sources;
        this.findingResource = findingResource;
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
                        findingResource.setInputStreams(url.openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    findingResource.setInputStreams(new FileInputStream(new File(sources[currentItemSources])));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Source № " + currentItemSources + " read is done");
        //notify();
    }
}
