import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class RoadIntersection {

    public HashMap<Segment, RoadIntersectionBuffer> allRoadIntersectionBufferMap;

    public IntersectionIsEmptyState intersectionIsEmptyState;


    RoadIntersection() {
        intersectionIsEmptyState = new IntersectionIsEmptyState();
        allRoadIntersectionBufferMap = new HashMap<>();
        Arrays.stream(Segment.values()).forEach(segment ->
                allRoadIntersectionBufferMap.put(segment, new RoadIntersectionBuffer(segment, intersectionIsEmptyState
                )));
    }


    public synchronized HashMap<Segment, RoadIntersectionBuffer> getAllRoadIntersectionBufferMap() {
        return this.allRoadIntersectionBufferMap;
    }

    public LinkedList<VehicleInSegment> getQueueForSegment(Segment segment) {
        return getAllRoadIntersectionBufferMap().get(segment).queue;
    }

}
