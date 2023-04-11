import java.util.ArrayList;

public class Main {

    private static Logger logger = Logger.getInstance();

    public static void main(String[] args) throws InterruptedException {
        logger.log("<------------APPLICATION STARTING UP--------------->");

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();


        ParseUtils.parseVehicles("vehicles.csv", vehicles);
        ParseUtils.parseIntersections("intersection.csv", intersections);

        startUpApplication(vehicles, intersections, "output.txt");
    }


    public static void startUpApplication(ArrayList<Vehicle> alreadyExistingVehicles, ArrayList<Intersection> intersections, String outputFile) {
        ArrayList<VehicleInSegment> vehiclesInSegments = new ArrayList<>();
        SimulationData simulationData = new SimulationData();
        RoadIntersection roadIntersection = new RoadIntersection();
        PhaseController phaseController = new PhaseController();

        View view = new View();
        ApplicationModel applicationModel = new ApplicationModel(phaseController, vehiclesInSegments, intersections, simulationData, roadIntersection);
        ApplicationController applicationController = new ApplicationController(view, applicationModel);

        VehicleArrivalSimulator vehicleArrivalSimulator = new VehicleArrivalSimulator(alreadyExistingVehicles, vehiclesInSegments, roadIntersection, phaseController, applicationController, simulationData);
        new CentralTrafficController(vehiclesInSegments, roadIntersection, phaseController, vehicleArrivalSimulator, applicationController);
    }


}