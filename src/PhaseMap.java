import java.util.*;
import java.util.stream.*;

public class PhaseMap {
    public static Map<String, Phase> map = Stream.of(new Object[][]{
            {"S1+LEFT", Phase.ONE},
            {"S3+STRAIGHT", Phase.TWO},
            {"S3+RIGHT", Phase.TWO},
            {"S4+LEFT", Phase.THREE},
            {"S2+STRAIGHT", Phase.FOUR},
            {"S2+RIGHT", Phase.FOUR},
            {"S3+LEFT", Phase.FIVE},
            {"S1+STRAIGHT", Phase.SIX},
            {"S1+RIGHT", Phase.SIX},
            {"S2+LEFT", Phase.SEVEN},
            {"S4+STRAIGHT", Phase.EIGHT},
            {"S4+RIGHT", Phase.EIGHT}
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Phase) data[1]));


    public static HashMap<Segment, Set<Phase>> phasesInSegment = new HashMap<>() {{
        put(Segment.S1, new HashSet<>(Arrays.asList(Phase.ONE, Phase.SIX)));
        put(Segment.S2, new HashSet<>(Arrays.asList(Phase.FOUR, Phase.SEVEN)));
        put(Segment.S3, new HashSet<>(Arrays.asList(Phase.TWO, Phase.FIVE)));
        put(Segment.S4, new HashSet<>(Arrays.asList(Phase.ONE, Phase.SIX)));
    }};


}
