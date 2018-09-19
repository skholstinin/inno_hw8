package ru.innopolis.stc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FindingResource {
    private ConcurrentHashMap<String, ArrayList<String>> findingHashMap;
    private CopyOnWriteArrayList<InputStream> inputStreamsList;

    public FindingResource(CopyOnWriteArrayList<InputStream> inputStreams, ConcurrentHashMap findingHashMap) {
        this.inputStreamsList = inputStreams;
        this.findingHashMap = findingHashMap;
    }

    public ConcurrentHashMap<String, ArrayList<String>> getFindingHashMap() {
        return findingHashMap;
    }

    public CopyOnWriteArrayList<InputStream> getInputStreamsList() {
        return inputStreamsList;
    }

    public int getNumberStream() {
        return 10;
    }

    public void setInputStreams(InputStream inputStreams) {
        this.inputStreamsList.add(inputStreams);
    }

    public void setFindingHashMap(String key, ArrayList<String> value) {
        this.findingHashMap.put(key, value);
    }
}
