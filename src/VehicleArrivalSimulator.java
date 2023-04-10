import java.util.*;

public class VehicleArrivalSimulator implements Runnable {

    public HashMap<Segment, HashMap<Phase, LinkedList<VehicleInSegment>>> queues;

    final RoadIntersection roadIntersection;

    final PhaseController phaseController;

    GUIApplication guiApplication;

    ArrayList<VehicleInSegment> masterVehicleList;

    TrafficController trafficController;

    ArrayList<Vehicle> vehiclesFromCsv;

    // Constructor for VehicleArrivalSimulator
    public VehicleArrivalSimulator(HashMap<Segment, HashMap<Phase, LinkedList<VehicleInSegment>>> queues, RoadIntersection roadIntersection, PhaseController phaseController, GUIApplication guiApplication, ArrayList<VehicleInSegment> masterVehicleList, ArrayList<Vehicle> vehicleList, TrafficController trafficController) {
        this.queues = queues;
        this.roadIntersection = roadIntersection;
        this.phaseController = phaseController;
        this.guiApplication = guiApplication;
        this.masterVehicleList = masterVehicleList;
        this.trafficController = trafficController;
        this.vehiclesFromCsv = vehicleList;

        addVehiclesToQueues(vehicleList, trafficController);
    }

    @Override
    public void run() {

        try {
            System.out.println("Starting vehicle arrival simulation...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

// Continuously generate new vehicles and add them to the queues

        while (true) {
            Vehicle v = new Vehicle(Utils.generateAlphaNumericString(7),
                    VehicleType.getRandom(),
                    (int) Utils.generateRandomNumber(0, 30),
                    Direction.getRandom(),
                    CrossStatus.WAITING,
                    (int) Utils.generateRandomNumber(0, 30),
                    (int) Utils.generateRandomNumber(0, 30),
                    Segment.getRandom());

            System.out.println("Adding Vehicle to queue: " + v);

            addVehiclesToQueues(new ArrayList<>() {{
                add(v);
            }}, trafficController);

            guiApplication.refresh();


            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

// Add vehicles to the appropriate queues based on their segment and phase

    public void addVehiclesToQueues(ArrayList<Vehicle> vehicles, TrafficController trafficController) {

        ArrayList<Vehicle> vehiclesInSegments = (ArrayList<Vehicle>) vehicles.clone();

        try {
            vehiclesInSegments.forEach(vehicle -> {

                VehicleInSegment lastVehicleInSegment;

                try {
                    lastVehicleInSegment = queues.get(vehicle.segment).get(vehicle.phase).getLast();
                } catch (Exception ex) {
                    lastVehicleInSegment = null;
                }

                  // Create a new VehicleInSegment instance and start its thread

                VehicleInSegment vehicleInSegment =
                        new VehicleInSegment(vehicle.vehicleNumber,
                                vehicle.type,
                                vehicle.crossTime,
                                vehicle.direction,
                                vehicle.status,
                                vehicle.length,
                                vehicle.emissionRate,
                                vehicle.segment,
                                lastVehicleInSegment == null ? 0 :
                                        lastVehicleInSegment.vehiclePosition + lastVehicleInSegment.length,
                                roadIntersection
                                , phaseController
                                , guiApplication,
                                trafficController);

                Thread vehicleThread = new Thread(vehicleInSegment);
                vehicleThread.start();

                queues.get(vehicle.segment).get(vehicle.phase).add(vehicleInSegment);
                masterVehicleList.add(vehicleInSegment);
                guiApplication.refresh();
            });
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
