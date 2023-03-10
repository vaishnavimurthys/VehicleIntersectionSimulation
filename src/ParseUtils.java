

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseUtils {


    public static ArrayList<Vehicle> parseVehicles(String fileName, ArrayList<Vehicle> vehicles) {

        try {
            File f = new File(fileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                //read first line and process it
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {//ignored if blank line
                    processVehicleRow(inputLine, vehicles);
                }
            }
        }
        //if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println(fileName + " not found ");
            System.exit(0);
        }

        return null;
    }


    public static void processVehicleRow(String inputLine, ArrayList<Vehicle> vehicles) {
        try {
            String[] parts = StringUtil.sanitiseInput(inputLine.split(","));

            String vehicleNumber = parts[0];
            VehicleType vehicleType = VehicleType.valueOf(parts[1]);
            int crossTime = Integer.parseInt(parts[2]);
            Direction direction = Direction.valueOf(parts[3]);
            CrossStatus crossStatus = CrossStatus.valueOf(parts[4]);
            int length = Integer.parseInt(parts[5]);
            int emissionRate = Integer.parseInt(parts[6]);
            Segment segment = Segment.valueOf(parts[7]);

            vehicles.add(new Vehicle(vehicleNumber, vehicleType, crossTime, direction, crossStatus, length, emissionRate, segment));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public static ArrayList<Intersection> parseIntersections(String fileName, ArrayList<Intersection> intersections) {
        try {
            File f = new File(fileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                //read first line and process it
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {//ignored if blank line
                    processIntersectionsRow(inputLine, intersections);
                }

            }
        }
        //if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println(fileName + " not found ");
            System.exit(0);
        }

        return null;
    }


    public static void processIntersectionsRow(String inputLine, ArrayList<Intersection> intersections) {
        try {
            String[] parts = StringUtil.sanitiseInput(inputLine.split(","));

            int phaseNumber = Integer.parseInt(parts[0]);
            int phaseDuration = Integer.parseInt(parts[1]);

            intersections.add(new Intersection(phaseNumber, phaseDuration));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void writeToFile(String filename, String report) {

        FileWriter fw;
        try {
            fw = new FileWriter(filename);
            fw.write(report);
            fw.close();
        }
        //message and stop if file not found
        catch (FileNotFoundException fnf) {
            System.out.println(filename + " not found ");
            System.exit(0);
        }
        //stack trace here because we don't expect to come here
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }
}


