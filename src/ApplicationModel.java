import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ApplicationModel {

    Logger logger = Logger.getInstance();

    public PhaseController phaseController;
    public ArrayList<VehicleInSegment> vehicles;
    public ArrayList<Intersection> intersections;
    public SimulationData simulationData;
    public RoadIntersection roadIntersection;
    public CrossingStatisticsController crossingStatistics;

    public DefaultTableModel vehicleTableModel;
    public DefaultTableModel statisticTableModel;
    public DefaultTableModel intersectionTableModel;


    static final Object[] vehicleColumnNames = {"Vehicle Number", "Type", "Cross Time", "Direction", "Status", "Length", "Emission Rate", "Segment", "Phase"};
    static final Object[] intersectionsColumnNames = {"Phase Number", "Phase Duration"};
    static final Object[] statisticColumnsNames = {"Segment No.", "No. of waiting vehicles", "Avg waiting time", "Total wait length", "Avg crossing time"};


    public ApplicationModel(PhaseController phaseController, ArrayList<VehicleInSegment> vehicles, ArrayList<Intersection> intersections, SimulationData simulationData, RoadIntersection roadIntersection) {
        this.vehicles = vehicles;
        this.intersections = intersections;
        this.roadIntersection = roadIntersection;
        this.simulationData = simulationData;
        this.phaseController = phaseController;
        this.crossingStatistics = new CrossingStatisticsController(vehicles, intersections);
    }

    public void refreshAllData() {
        refreshStatistics();
        refreshTableModels();
    }


    private void refreshStatistics() {
        this.crossingStatistics.refreshStatistics(this.vehicles, this.intersections);
    }

    private void refreshTableModels() {
        setVehicleTableModel();
        setStatisticTableModel();
        setIntersectionTableModel();
    }

    public void setVehicleTableModel() {
        Object[][] vehicleRowData = convertVehicleIntoArrayOfArrays();
        this.vehicleTableModel = new DefaultTableModel(vehicleRowData, vehicleColumnNames);
    }

    public void setStatisticTableModel() {
        String[][] statisticData = convertStatisticsIntoArrayOfArrays();
        statisticTableModel = new DefaultTableModel(statisticData, statisticColumnsNames);
    }

    public void setIntersectionTableModel() {
        String[][] intersectionRowData = convertIntersectionIntoArrayOfArrays();
        intersectionTableModel = new DefaultTableModel(intersectionRowData, intersectionsColumnNames);
    }

    public Phase getCurrentPhase() {
        return this.phaseController.getCurrentPhase();
    }

    public synchronized Object[][] convertVehicleIntoArrayOfArrays() {

        Object[][] rowData = new String[vehicles.size()][];

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            rowData[i] = new String[9];

            rowData[i][0] = String.valueOf(v.vehicleNumber);
            rowData[i][1] = String.valueOf(v.type);
            rowData[i][2] = String.valueOf(v.crossTime);
            rowData[i][3] = String.valueOf(v.direction);
            rowData[i][4] = String.valueOf(v.status);
            rowData[i][5] = String.valueOf(v.length);
            rowData[i][6] = String.valueOf(v.emissionRate);
            rowData[i][7] = v.segment.name();
            rowData[i][8] = v.phase.name();
        }

        return rowData;
    }

    private synchronized String[][] convertIntersectionIntoArrayOfArrays() {

        String[][] rowData = new String[intersections.size()][];

        for (int i = 0; i < intersections.size(); i++) {
            Intersection intersection = intersections.get(i);
            rowData[i] = new String[7];

            rowData[i][0] = String.valueOf(intersection.phaseNumber);
            rowData[i][1] = String.valueOf(intersection.phaseDuration);
        }

        return rowData;
    }

    private synchronized String[][] convertStatisticsIntoArrayOfArrays() {


        String[][] rowData = new String[crossingStatistics.crossingPerSegment.size()][];

        try {

            int itr = 0;

            for (CrossingStatisticsPerSegment crossingStatisticsPerSegment : crossingStatistics.crossingPerSegment.values()) {
                rowData[itr] = new String[5];
                rowData[itr][0] = crossingStatisticsPerSegment.segment.name();
                rowData[itr][1] = String.valueOf(crossingStatisticsPerSegment.numberOfVehicleWaiting);
                rowData[itr][2] = String.valueOf(crossingStatisticsPerSegment.avgWaitingTime);
                rowData[itr][3] = String.valueOf(crossingStatisticsPerSegment.totalLengthOfWaitingVehicles);
                rowData[itr][4] = String.valueOf(crossingStatisticsPerSegment.avgCrossingTime);

                itr++;
            }
        } catch (Exception ex) {
            logger.log("[ERROR] [REFRESH_STATISTIC]");
        }

        return rowData;


    }


}
