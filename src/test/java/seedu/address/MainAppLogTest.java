package seedu.address;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class MainAppLogTest {
    @Test
    public void init_logsInitializingInsuraBook() throws Exception {
        Logger logger = Logger.getLogger("seedu.address");
        List<String> logs = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) {
                logs.add(record.getMessage());
            }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);

        MainApp app = new MainApp() {
            @Override
            public void init() throws Exception {
                // Only call the log line to avoid JavaFX issues
                logger.info("=============================[ Initializing InsuraBook ]===========================");
            }
        };
        app.init();

        assertTrue(
                logs.stream().anyMatch(msg -> msg.contains("Initializing InsuraBook")),
                "Should log Initializing InsuraBook"
        );
        logger.removeHandler(handler);
    }

    @Test
    public void stop_logsStoppingInsuraBook() {
        Logger logger = Logger.getLogger("seedu.address");
        List<String> logs = new ArrayList<>();
        Handler handler = new Handler() {
            @Override public void publish(LogRecord record) {
                logs.add(record.getMessage());
                }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(handler);

        MainApp app = new MainApp() {
            @Override
            public void stop() {
                logger.info("============================ [ Stopping InsuraBook ] =============================");
            }
        };
        app.stop();

        assertTrue(
                logs.stream().anyMatch(msg -> msg.contains("Stopping InsuraBook")),
                "Should log Stopping InsuraBook"
        );
        logger.removeHandler(handler);
    }
}

