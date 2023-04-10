import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Create lists for vehicles and intersections
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();

        // Parse the vehicles and intersections from the given CSV files
        ParseUtils.parseVehicles("vehicles.csv", vehicles);
        ParseUtils.parseIntersections("intersection.csv", intersections);

        // Start the application with the parsed data and output file
        startUpApplication(vehicles, intersections, "output.txt");
    }

    // Create and return a GUIApplication instance with the given parameters
    private static GUIApplication runGuiApplication(ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> intersections, String outputFile) {
        return new GUIApplication(vehicles, intersections, outputFile);
    }

    // Start the application and set up the necessary components
    private static void startUpApplication(ArrayList<Vehicle> vehicles, ArrayList<Intersection> intersections, String outputFile) throws InterruptedException {

        // Create a list to store the vehicles lined up at the intersection
        ArrayList<VehicleInSegment> vehiclesLinedUp = new ArrayList<VehicleInSegment>();


        RoadIntersection roadIntersection = new RoadIntersection();

        // Create a shared object to manage the intersection and a thread to run it
        RoadIntersectionSharedObject roadIntersectionSharedObject = new RoadIntersectionSharedObject(roadIntersection);
        Thread roadIntersectionThread = new Thread(roadIntersectionSharedObject);
        roadIntersectionThread.start();


        PhaseController phaseController = new PhaseController(intersections, roadIntersection);
        Thread thread = new Thread(phaseController);
        thread.start();

        // Create and run the GUI application with the vehicles, intersections, and output file
        GUIApplication gui = runGuiApplication(vehiclesLinedUp, intersections, outputFile);

        TrafficController trafficController = new TrafficController(vehicles, vehiclesLinedUp, roadIntersection, phaseController, gui);
    }
}
