import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseUtils {

    static Logger logger = Logger.getInstance();


    public static void parseVehicles(String fileName, ArrayList<Vehicle> vehicles) {

        try {
            URL urlToDictionary = Main.class.getResource("/" + fileName);
            assert urlToDictionary != null;
            InputStream is = urlToDictionary.openStream();
            Scanner scanner = new Scanner(is);
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
            logger.log("[ERROR] - [PARSER] - " + fileName + " not found");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            logger.log("[ERROR] [PARSER] " + e.getMessage());
            System.exit(0);
        }
    }

    public static void parseIntersections(String fileName, ArrayList<Intersection> intersections) {
        try {
            URL urlToDictionary = Main.class.getResource("/" + fileName);
            InputStream is = urlToDictionary.openStream();
            Scanner scanner = new Scanner(is);
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
            logger.log("[ERROR] - [PARSER] - " + fileName + " not found");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void processIntersectionsRow(String inputLine, ArrayList<Intersection> intersections) {
        try {
            String[] parts = StringUtil.sanitiseInput(inputLine.split(","));

            int phaseNumber = Integer.parseInt(parts[0]);
            int phaseDuration = Integer.parseInt(parts[1]);

            intersections.add(new Intersection(phaseNumber, phaseDuration));

        } catch (Exception e) {
            logger.log("[ERROR] - [PARSER] - Error while parsing Intersection row");
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
            logger.log("[ERROR] - [PARSER] - " + filename + " not found");
            System.exit(0);
        }
        //stack trace here because we don't expect to come here
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }
}


