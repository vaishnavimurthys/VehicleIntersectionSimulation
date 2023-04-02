import java.util.*;

public class Utils {


    public static double generateRandomNumber(int min, int max) {
        return Math.random() * (max - min + 1) + min;
    }

    public static String generateAlphaNumericString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

}
