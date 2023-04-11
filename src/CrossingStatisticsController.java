import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CrossingStatisticsController {


    Logger logger = Logger.getInstance();
    public int totalEmissions;

    public int avgWaitingTimeToCross;
    public HashMap<Segment, CrossingStatisticsPerSegment> crossingPerSegment;

    public CrossingStatisticsController(ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> phases) {
        refreshStatistics(vehicles, phases);
    }

    private void inflateCrossingPerSegment() {
        Arrays.stream(Segment.values()).forEach(segment ->
                crossingPerSegment.put(segment, new CrossingStatisticsPerSegment(segment))
        );
    }

    public void refreshStatistics(ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> phases) {

        try {
            crossingPerSegment = new HashMap<>();
            inflateCrossingPerSegment();

            vehicles.forEach(vehicle ->
                    crossingPerSegment.get(vehicle.segment).vehiclesInSegment.add(vehicle)
            );

            int allWaitingTime = 0;

            totalEmissions = 0;

            /**
             * loop through all the segments and recalculate statistics
             */
            for (CrossingStatisticsPerSegment data : crossingPerSegment.values()) {
                data.refreshCrossingStatistics();
                totalEmissions += data.totalEmissionFromWaiting;
                allWaitingTime += data.avgWaitingTime;
            }

            avgWaitingTimeToCross = allWaitingTime / crossingPerSegment.size();
        } catch (Exception ex) {
            logger.log("[ERROR] - [CROSSING_STATISTICS] " + ex);
        }
    }
}
