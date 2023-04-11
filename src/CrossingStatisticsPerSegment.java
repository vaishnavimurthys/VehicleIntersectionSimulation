import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CrossingStatisticsPerSegment {
    Segment segment;
    int numberOfVehicleWaiting;
    int totalLengthOfWaitingVehicles;
    int avgWaitingTime;
    int avgCrossingTime;
    ArrayList<VehicleInSegment> vehiclesInSegment;

    int totalEmissionFromWaiting;

    HashMap<Phase, ArrayList<VehicleInSegment>> vehiclesCrossedPerPhase;

    HashMap<Phase, Integer> avgWaitTimePerPhase;


    public CrossingStatisticsPerSegment(Segment segment) {
        this.segment = segment;
        this.numberOfVehicleWaiting = 0;
        this.totalLengthOfWaitingVehicles = 0;
        this.avgWaitingTime = 0;
        this.vehiclesInSegment = new ArrayList<>();
    }


    public void refreshCrossingStatistics() {
        AtomicInteger totalWaitTime = new AtomicInteger();
        AtomicInteger totalCrossTime = new AtomicInteger();

        avgWaitTimePerPhase = new HashMap<>();
        vehiclesCrossedPerPhase = new HashMap<>();

        AtomicInteger counter = new AtomicInteger();

        vehiclesInSegment.forEach(vehicle -> {

            Phase phase = PhaseMap.map.get(vehicle.segment.name() + "+" + vehicle.direction.name());

            if (vehicle.status == CrossStatus.WAITING) {
                numberOfVehicleWaiting++;
                totalLengthOfWaitingVehicles += vehicle.length;
            } else if (vehicle.status == CrossStatus.CROSSED) {
                vehiclesCrossedPerPhase.computeIfAbsent(phase, k -> new ArrayList<>());
                vehiclesCrossedPerPhase.get(phase).add(vehicle);
                totalCrossTime.getAndAdd(vehicle.crossTime);
                totalEmissionFromWaiting = vehicle.crossTime * vehicle.emissionRate;
                totalWaitTime.addAndGet(vehicle.waitTime);

                avgWaitTimePerPhase.computeIfAbsent(phase, k -> 0);
                Integer avgWaitingTimeForCurrPhase = avgWaitTimePerPhase.get(vehicle.phase) + vehicle.waitTime / vehiclesCrossedPerPhase.get(phase).size();
                avgWaitTimePerPhase.put(vehicle.phase, avgWaitingTimeForCurrPhase);
            }

            counter.getAndIncrement();
        });

        if (!vehiclesInSegment.isEmpty()) {
            avgCrossingTime = totalCrossTime.get() / vehiclesInSegment.size();
            avgWaitingTime = Integer.parseInt(String.valueOf(totalWaitTime)) / vehiclesInSegment.size();
        }

    }
}



