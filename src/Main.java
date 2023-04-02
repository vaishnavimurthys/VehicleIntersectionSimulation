import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();
        ParseUtils.parseVehicles("vehicles.csv", vehicles);
        ParseUtils.parseIntersections("intersection.csv", intersections);


        startUpApplication(vehicles, intersections, "output.txt");


    }

    private static void runGuiApplication(ArrayList<Vehicle> vehicles, ArrayList<Intersection> intersections, String outputFile) {
        new GUIApplication(vehicles, intersections, outputFile);
    }


    private static void startUpApplication(ArrayList<Vehicle> vehicles, ArrayList<Intersection> intersections, String outputFile) {

        RoadIntersection roadIntersection = new RoadIntersection();

        PhaseController phaseController = new PhaseController(intersections);
        Thread thread = new Thread(phaseController);
        thread.start();

        System.out.println("here");

        TrafficController trafficController = new TrafficController(vehicles, roadIntersection);

        runGuiApplication(vehicles, intersections, outputFile);

    }

}
