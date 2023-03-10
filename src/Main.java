import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();
        ParseUtils.parseVehicles("vehicles.csv", vehicles);
        ParseUtils.parseIntersections("intersection.csv", intersections);
        runGuiApplication(vehicles, intersections, "output.txt");
    }

    private static void runGuiApplication(ArrayList<Vehicle> vehicles, ArrayList<Intersection> intersections, String outputFile) {
        new GUIApplication(vehicles, intersections, outputFile);
    }
}
