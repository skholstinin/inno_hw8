import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class SearchWord implements Callable<Queue> {
    static final Logger myLogger = Logger.getLogger(SearchWord.class);
    private String[] words;
    ArrayList<StringBuilder> listInputStringBuilder = null;
    FindingResource findingResource = null;
    private int currentItem = 0;

    public SearchWord(FindingResource findingResource, ArrayList<StringBuilder> listInputStringBuilder, int currentItem, String[] words) {
        this.findingResource = findingResource;
        this.listInputStringBuilder = listInputStringBuilder;
        this.words = words;
        this.currentItem = currentItem;
    }

    @Override
    public Queue call() {
        String[] StrArray = null;
        String spliter = null;
        if (listInputStringBuilder.get(currentItem).length() > 0) {
            spliter = listInputStringBuilder.get(currentItem).toString();
            StrArray = spliter.split("[\\.!?]");
            for (String w : words) {
                ArrayList<String> findingString = new ArrayList<>();
                for (String s : StrArray) {
                    if (s.toLowerCase().contains(w.toLowerCase())) {
                        myLogger.info("find" + w);
                        findingString.add(s + ".\r\n");
                    }
                }
                findingResource.addItemToQueue(findingString);
            }
        }
        return findingResource.getQueue();
    }
}
