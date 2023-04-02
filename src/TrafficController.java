import java.util.*;

public class TrafficController {

    //this can be a shared resource??
    public HashMap<Segment, HashMap<Phase, LinkedList<VehicleInSegment>>> queues;

    int totalTimeElapsed;

    RoadIntersection roadIntersection;

    TrafficController(ArrayList<Vehicle> vehicles, RoadIntersection roadIntersection) {
        this.roadIntersection = roadIntersection;
        createQueuePerSegment();
        addVehiclesToQueues(vehicles);

        //initiate traffic arrival
        simulateVehicleArrival();

    }

    private void createQueuePerSegment() {
        this.queues = new HashMap<>();

        Arrays.stream(Segment.values()).forEach(segment -> {
            HashMap<Phase, LinkedList<VehicleInSegment>> phaseMap = new HashMap<>();
            PhaseMap.phasesInSegment.get(segment).forEach(phase -> phaseMap.put(phase, new LinkedList<>()));
            queues.put(segment, phaseMap);
        });
    }

    private void addVehiclesToQueues(ArrayList<Vehicle> vehicles) {
        vehicles.forEach(vehicle -> {
            VehicleInSegment vehicleInSegment =
                    new VehicleInSegment(vehicle.vehicleNumber,
                            vehicle.type,
                            vehicle.crossTime,
                            vehicle.direction,
                            vehicle.status,
                            vehicle.length,
                            vehicle.emissionRate,
                            vehicle.segment,
                            0,
                            roadIntersection);


            queues.get(vehicle.segment).get(vehicle.phase).add(vehicleInSegment);
        });
    }


    private void simulateVehicleArrival() {
        Vehicle v = new Vehicle(Utils.generateAlphaNumericString(7),
                VehicleType.getRandom(),
                (int) Utils.generateRandomNumber(0, 30),
                Direction.getRandom(),
                CrossStatus.getRandom(),
                (int) Utils.generateRandomNumber(0, 30),
                (int) Utils.generateRandomNumber(0, 30),
                Segment.getRandom());

        System.out.println("Adding " + v);

        addVehiclesToQueues(new ArrayList<>() {{
            add(v);
        }});

        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }


}
