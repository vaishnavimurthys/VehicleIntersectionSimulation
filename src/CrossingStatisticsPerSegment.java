import java.util.*;
import java.util.concurrent.atomic.*;

// A class to track traffic stats for a road section.
public class CrossingStatisticsPerSegment {
    Segment segment;
    int numberOfVehicleWaiting;
    int totalLengthOfWaitingVehicles;
    int avgWaitingTime;
    ArrayList<Vehicle> vehiclesInSegment;

    int totalEmissionFromWaiting;

    HashMap<Phase, ArrayList<Vehicle>> vehiclesCrossedPerPhase;

    // Constructor: Set up stats for the given road section.
    public CrossingStatisticsPerSegment(Segment segment) {
        this.segment = segment;
        this.numberOfVehicleWaiting = 0;
        this.totalLengthOfWaitingVehicles = 0;
        this.avgWaitingTime = 0;
        this.vehiclesInSegment = new ArrayList<>();
    }

    // Update traffic stats for the road section.
    public void refreshCrossingStatistics() {
        AtomicInteger totalWaitTime = new AtomicInteger();
        vehiclesCrossedPerPhase = new HashMap<>();
        AtomicInteger counter = new AtomicInteger();

        // Process each car in the road section.
        vehiclesInSegment.forEach(vehicle -> {
            Phase phase = PhaseMap.map.get(vehicle.segment.name() + "+" + vehicle.direction.name());

            // Update waiting stats if the car is waiting.
            if (vehicle.status == CrossStatus.WAITING) {
                numberOfVehicleWaiting++;
                totalLengthOfWaitingVehicles += vehicle.length;
                totalEmissionFromWaiting += vehicle.emissionRate;
                // Update crossing stats if the car has crossed.
            } else if (vehicle.status == CrossStatus.CROSSED) {
                vehiclesCrossedPerPhase.computeIfAbsent(phase, k -> new ArrayList<>());
                vehiclesCrossedPerPhase.get(phase).add(vehicle);
            }
            totalWaitTime.addAndGet(vehicle.crossTime);
            counter.getAndIncrement();
        });

        // Calculate average waiting time if there are cars in the road section.
        if (vehiclesInSegment.size() > 0) {
            avgWaitingTime = Integer.parseInt(String.valueOf(totalWaitTime)) / vehiclesInSegment.size();
        }
    }
}
