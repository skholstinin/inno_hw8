package ru.innopolis.stc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FindingResource {
    private ConcurrentHashMap<String, ArrayList<String>> findingHashMap;
    private CopyOnWriteArrayList<InputStream> inputStreams;

    public FindingResource(CopyOnWriteArrayList<InputStream> inputStreams, ConcurrentHashMap findingHashMap) {
        this.inputStreams = inputStreams;
        this.findingHashMap = findingHashMap;
    }

    public ConcurrentHashMap<String, ArrayList<String>> getFindingHashMap() {
        return findingHashMap;
    }

    public CopyOnWriteArrayList<InputStream> getInputStreams() {
        return inputStreams;
    }

    public void setInputStreams(InputStream inputStreams) {
        this.inputStreams.add(inputStreams);
    }

    public void setFindingHashMap(String key, ArrayList<String> value) {
        this.findingHashMap.put(key, value);
    }
}
