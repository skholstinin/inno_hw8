package ru.innopolis.stc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class FindingResource {
    Queue<ArrayList<String>> queue = new ConcurrentLinkedQueue<>();
    private CopyOnWriteArrayList<InputStream> inputStreamsList;

    public FindingResource(CopyOnWriteArrayList<InputStream> inputStreams, ConcurrentHashMap findingHashMap) {
        this.inputStreamsList = inputStreams;
    }

    public Queue<ArrayList<String>> getQueue() {
        return queue;
    }

    public void setQueue(Queue<ArrayList<String>> queue) {
        this.queue = queue;
    }

    public ArrayList<String> getQueueItem() {
        ArrayList<String> item;
        item = queue.peek();
        queue.poll();
        return item;
    }

    void addItemToQueue(ArrayList<String> item) {
        this.queue.offer(item);
    }
    public CopyOnWriteArrayList<InputStream> getInputStreamsList() {
        return inputStreamsList;
    }

    public void setInputStreams(InputStream inputStreams) {
        this.inputStreamsList.add(inputStreams);
    }

}
