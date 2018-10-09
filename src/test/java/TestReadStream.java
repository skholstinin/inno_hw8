import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestReadStream {
    static final Logger myLogger = Logger.getLogger(Main.class);

    @BeforeAll
    static void initTest() {
        myLogger.info("Init all for TestReadStream");
    }

    @Test
    void testMethod() {
        myLogger.info("Test passed for TestReadStream");
    }
}
