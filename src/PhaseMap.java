import java.util.*;
import java.util.stream.*;

public class PhaseMap {
    public static Map<String, Phases> map = Stream.of(new Object[][]{
            {"S1+LEFT", Phases.ONE},
            {"S3+STRAIGHT", Phases.TWO},
            {"S3+RIGHT", Phases.TWO},
            {"S4+LEFT", Phases.THREE},
            {"S2+STRAIGHT", Phases.FOUR},
            {"S2+RIGHT", Phases.FOUR},
            {"S3+LEFT", Phases.FIVE},
            {"S1+STRAIGHT", Phases.SIX},
            {"S1+RIGHT", Phases.SIX},
            {"S2+LEFT", Phases.SEVEN},
            {"S4+STRAIGHT", Phases.EIGHT},
            {"S4+RIGHT", Phases.EIGHT}
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Phases) data[1]));
}
