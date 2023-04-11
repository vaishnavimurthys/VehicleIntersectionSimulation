import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private final String logFile = "log.txt";
    private static Logger logger = null;
    private PrintWriter writer;

    private Logger() {
        try {
            FileWriter fw = new FileWriter(logFile);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {
        }
    }

    public void log(String text) {
        System.out.println(text);
        writer.println(text);
    }

    public static synchronized Logger getInstance() {
        if (logger == null)
            logger = new Logger();
        return logger;
    }

}