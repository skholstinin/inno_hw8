import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestSearchWord {
    static final Logger myLogger = Logger.getLogger(Main.class);

    @BeforeAll
    static void initTest() {
        myLogger.info("Init all for TestSearchWord");
    }

    @Test
    void testMethod() {
        myLogger.info("Test passed for TestSearchWord");
    }
}
