import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestWriteToFile {
    static final Logger myLogger = Logger.getLogger(Main.class);

    @BeforeAll
    static void initTest() {
        myLogger.info("Init all for TestWriteToFile");
    }

    @Test
    void testMethod() {
        myLogger.info("Test passed for TestWriteToFile");
    }
}
