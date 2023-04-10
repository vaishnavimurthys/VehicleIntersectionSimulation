import java.util.*;

// A class to track traffic statistics.
public class CrossingStatistics {

    public int totalEmissions;

    public int avgWaitingTimeToCross;

    // A map containing statistics for each segment in the intersection.
    public HashMap<Segment, CrossingStatisticsPerSegment> crossingPerSegment;

    // Constructor: Calculate traffic stats for cars and road sections.
    public CrossingStatistics(ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> phases) {
        refreshStatistics(vehicles, phases);
    }

    /// Populates the crossingPerSegment map with an entry for each segment.
    private void inflateCrossingPerSegment() {
        Arrays.stream(Segment.values()).forEach(segment ->
                crossingPerSegment.put(segment, new CrossingStatisticsPerSegment(segment))
        );
    }

    // Refreshes the statistics based on the current state of vehicles and phases.
    public void refreshStatistics(ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> phases) {
        crossingPerSegment = new HashMap<>();
        inflateCrossingPerSegment();

        // Add vehicles to the corresponding segment statistics.
        vehicles.forEach(vehicle ->
                crossingPerSegment.get(vehicle.segment).vehiclesInSegment.add(vehicle)
        );

        int allWaitingTime = 0;

        // Iterate over all segment statistics, refreshing their data and accumulating waiting time and emissions.
        for (CrossingStatisticsPerSegment data : crossingPerSegment.values()) {
            data.refreshCrossingStatistics();
            totalEmissions += data.totalEmissionFromWaiting;
            allWaitingTime += data.avgWaitingTime;
        }

        // Calculate average waiting time to cross.
        avgWaitingTimeToCross = allWaitingTime / crossingPerSegment.size();
    }
}
