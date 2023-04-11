import java.util.ArrayList;
import java.util.Locale;

public class VehicleArrivalSimulator implements Runnable {

    Logger logger = Logger.getInstance();

    ArrayList<VehicleInSegment> vehicleInSegments;
    Thread vehicleArrivalSimulatorThread;
    RoadIntersection roadIntersection;
    PhaseController phaseController;

    SimulationData simulationData;
    ApplicationController applicationController;

    public VehicleArrivalSimulator(ArrayList<Vehicle> alreadyExistingVehicles, ArrayList<VehicleInSegment> vehiclesInSegments, RoadIntersection roadIntersection, PhaseController phaseController, ApplicationController applicationController, SimulationData simulationData) {
        this.vehicleInSegments = vehiclesInSegments;
        this.roadIntersection = roadIntersection;
        this.applicationController = applicationController;
        this.simulationData = simulationData;
        addVehiclesToQueues(alreadyExistingVehicles);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        while (true) {
            if (simulationData.getSimulationState() == SimulationState.START) {
                Vehicle v = new Vehicle(
                        Utils.generateAlphaNumericString(7).toUpperCase(Locale.ROOT),
                        VehicleType.getRandom(),
                        (int) Utils.generateRandomNumber(0, 2),
                        Direction.getRandom(),
                        CrossStatus.WAITING,
                        (int) Utils.generateRandomNumber(0, 10),
                        (int) Utils.generateRandomNumber(0, 10),
                        Segment.getRandom());


                logger.log("[INFO] - [ADD_VEHICLE] " + v);


                addVehiclesToQueues(new ArrayList<>() {{
                    add(v);
                }});

                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException ex) {
                    logger.log("[ERROR] - [ADD_VEHICLE] " + ex);
                }
            }
        }
    }


    public void addVehiclesToQueues(ArrayList<Vehicle> vehicles) {
        vehicles.forEach(vehicle -> {
            VehicleInSegment lastVehicleInSegment;

            try {
                lastVehicleInSegment = this.roadIntersection.getQueueForSegment(vehicle.segment).getLast();
            } catch (Exception ex) {
                lastVehicleInSegment = null;
            }

            VehicleInSegment vehicleInSegment = VehicleInSegment.createVehicleInSegmentFromVehicle(vehicle, lastVehicleInSegment, roadIntersection, phaseController, applicationController);

            if (vehicleInSegment.status == CrossStatus.WAITING) {
                vehicleInSegment.crossIntersection();
                this.roadIntersection.getQueueForSegment(vehicle.segment).add(vehicleInSegment);
            }

            this.vehicleInSegments.add(vehicleInSegment);
            applicationController.getApplicationModel().refreshAllData();
        });

    }

    public void simulateVehicleArrival() {
        vehicleArrivalSimulatorThread = new Thread(this);
        vehicleArrivalSimulatorThread.start();
    }
}
