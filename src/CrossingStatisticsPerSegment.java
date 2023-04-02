import java.util.*;
import java.util.concurrent.atomic.*;

public class CrossingStatisticsPerSegment {
    Segment segment;
    int numberOfVehicleWaiting;
    int totalLengthOfWaitingVehicles;
    int avgWaitingTime;
    ArrayList<Vehicle> vehiclesInSegment;

    int totalEmissionFromWaiting;

    HashMap<Phase, ArrayList<Vehicle>> vehiclesCrossedPerPhase;


    public CrossingStatisticsPerSegment(Segment segment) {
        this.segment = segment;
        this.numberOfVehicleWaiting = 0;
        this.totalLengthOfWaitingVehicles = 0;
        this.avgWaitingTime = 0;
        this.vehiclesInSegment = new ArrayList<>();
    }


    public void refreshCrossingStatistics() {
        AtomicInteger totalWaitTime = new AtomicInteger();

        vehiclesCrossedPerPhase = new HashMap<>();

        AtomicInteger counter = new AtomicInteger();


        vehiclesInSegment.forEach(vehicle -> {

            Phase phase = PhaseMap.map.get(vehicle.segment.name() + "+" + vehicle.direction.name());

            if (vehicle.status == CrossStatus.WAITING) {
                numberOfVehicleWaiting++;
                totalLengthOfWaitingVehicles += vehicle.length;
                totalEmissionFromWaiting += vehicle.emissionRate;
            } else if (vehicle.status == CrossStatus.CROSSED) {
                vehiclesCrossedPerPhase.computeIfAbsent(phase, k -> new ArrayList<>());
                vehiclesCrossedPerPhase.get(phase).add(vehicle);
            }
            totalWaitTime.addAndGet(vehicle.crossTime);
            counter.getAndIncrement();
        });

        if (vehiclesInSegment.size() > 0) {
            avgWaitingTime = Integer.parseInt(String.valueOf(totalWaitTime)) / vehiclesInSegment.size();
        }

    }
}



