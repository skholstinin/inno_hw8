package ru.innopolis.stc;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FindingResource {
    private Queue<ArrayList<String>> queue = new ConcurrentLinkedQueue<>();

    public FindingResource(Queue<ArrayList<String>> queue) {
        this.queue = queue;
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

}
