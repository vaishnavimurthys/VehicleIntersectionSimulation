import java.util.*;

public class TrafficController {

    // A HashMap representing the queues of vehicles for each segment and phase
    public HashMap<Segment, HashMap<Phase, LinkedList<VehicleInSegment>>> queues;

    int totalTimeElapsed;

    final RoadIntersection roadIntersection;

    final PhaseController phaseController;

    GUIApplication guiApplication;

    ArrayList<VehicleInSegment> masterVehicleList;

    ArrayList<Vehicle> vehiclesFromCsv;

    // TrafficController constructor
    TrafficController(ArrayList<Vehicle> vehicles, ArrayList<VehicleInSegment> vehiclesLinedUp, RoadIntersection roadIntersection, PhaseController phaseController, GUIApplication gui) throws InterruptedException {
        this.roadIntersection = roadIntersection;
        this.phaseController = phaseController;
        this.guiApplication = gui;
        this.masterVehicleList = vehiclesLinedUp;
        this.vehiclesFromCsv = vehicles;

        createQueuePerSegment();

      // Initialize and start the VehicleArrivalSimulator thread

        VehicleArrivalSimulator vehicleArrivalSimulator = new VehicleArrivalSimulator(queues, roadIntersection, phaseController, guiApplication, masterVehicleList, vehiclesFromCsv, this);
        Thread vehicleArrivalSimulatorThread = new Thread(vehicleArrivalSimulator);
        vehicleArrivalSimulatorThread.start();
    }
// Creates a queue for each segment and phase combination
    private void createQueuePerSegment() {
        this.queues = new HashMap<>();

        Arrays.stream(Segment.values()).forEach(segment -> {
            HashMap<Phase, LinkedList<VehicleInSegment>> phaseMap = new HashMap<>();
            PhaseMap.phasesInSegment.get(segment).forEach(phase -> phaseMap.put(phase, new LinkedList<>()));
            queues.put(segment, phaseMap);
        });
    }

    // Removes the given vehicle from the appropriate queue
    public void removeVehicleFromQueue(VehicleInSegment vehicleToBeRemoved) {
        System.out.println("Removing " + vehicleToBeRemoved.vehicleNumber + " from queue.");
        queues.get(vehicleToBeRemoved.segment).get(vehicleToBeRemoved.phase).remove(vehicleToBeRemoved);
    }
}
