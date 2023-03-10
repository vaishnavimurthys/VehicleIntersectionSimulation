import java.util.*;

public class CrossingStatistics {
    public int totalEmissions;

    public int avgWaitingTimeToCross;
    public HashMap<Segment, CrossingStatisticsPerSegment> crossingPerSegment;

    public CrossingStatistics(ArrayList<Vehicle> vehicles, ArrayList<Intersection> phases) {
        refreshStatistics(vehicles, phases);
    }

    private void inflateCrossingPerSegment() {
        Arrays.stream(Segment.values()).forEach(segment ->
                crossingPerSegment.put(segment, new CrossingStatisticsPerSegment(segment))
        );
    }

    public void refreshStatistics(ArrayList<Vehicle> vehicles, ArrayList<Intersection> phases) {
        crossingPerSegment = new HashMap<>();
        inflateCrossingPerSegment();

        vehicles.forEach(vehicle ->
                crossingPerSegment.get(vehicle.segment).vehiclesInSegment.add(vehicle)
        );

        int allWaitingTime = 0;

        for (CrossingStatisticsPerSegment data : crossingPerSegment.values()) {
            data.refreshCrossingStatistics();
            totalEmissions += data.totalEmissionFromWaiting;
            allWaitingTime += data.avgWaitingTime;
        }

        avgWaitingTimeToCross = allWaitingTime / crossingPerSegment.size();
    }
}
