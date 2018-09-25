import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class SearchWord implements Callable<Queue> {
    static final Logger myLogger = Logger.getLogger(SearchWord.class);
    private String[] words;
    StringBuilder inputStringBuilder = null;
    FindingResource findingResource = null;

    public SearchWord(FindingResource findingResource, StringBuilder inputStringBuilder, String[] words) {
        this.findingResource = findingResource;
        this.inputStringBuilder = inputStringBuilder;
        this.words = words;
    }

    @Override
    public Queue call() {
        String[] StrArray = null;
        String spliter = null;
        spliter = inputStringBuilder.toString();
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
        return findingResource.getQueue();
    }
}
