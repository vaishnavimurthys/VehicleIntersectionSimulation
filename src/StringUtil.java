import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

    public static String appendNewLineCharacter(String s) {
        return s + "\n";
    }
// Trims whitespaces in each element of the input array and returns a new array with the sanitized elements

    public static String[] sanitiseInput(String[] input) {
        String[] sanitisedInput = new String[input.length];

        for (int i = 0; i < input.length; i++) {
            sanitisedInput[i] = input[i].trim();
        }

        return sanitisedInput;
    }


    public static String formatTable(List<List<String>> input, boolean leftJustifiedRows) {

        /*
         * Convert the input to a 2D array
         */
        String[][] table = input.stream()
                .map(l -> l.toArray(String[]::new))
                .toArray(String[][]::new);
        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(table).forEach(a -> java.util.stream.Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            columnLengths.putIfAbsent(i, 0);
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));


        /*
         * Prepare format String
         */
        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
        formatString.append("|\n");


        StringBuilder toReturn = new StringBuilder();

        /*
         * Print table
         */
        java.util.stream.Stream.iterate(0, (i -> i < table.length), (i -> ++i))
                .forEach(a -> toReturn.append(String.format(formatString.toString(), table[a])));


        return appendNewLineCharacter(toReturn.toString());

    }





}
