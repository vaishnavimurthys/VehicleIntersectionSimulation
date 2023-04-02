import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class GUIApplication extends JFrame implements ActionListener {
    // The vehicle list to be displayed in a table.
    private ArrayList<Vehicle> vehicles;

    private ArrayList<Intersection> intersections;

    //GUI components
    private JPanel mainPanel;

    private JPanel addVehiclePanel;
    private JTextField vehicleNumberTextField;

    private JComboBox<VehicleType> vehicleTypeCombobox;
    private JTextField crossTimeTextField;
    private JComboBox<Direction> directionCombobox;
    private JComboBox<CrossStatus> statusCombobox;
    private JTextField lengthTextField;
    private JTextField emissionRateTextField;
    private JButton addVehicleButton;
    private JTable vehicleTable;

    private JComboBox segmentComboBox;

    private JTable intersectionTable;

    private JTable statisticTable;

    private JLabel addVehicleLabel;

    private DefaultTableModel vehicleTableModel;

    private DefaultTableModel statisticTableModel;

    private CrossingStatistics crossingStatistics;

    private JTextField totalEmissionTextField;

    private JButton closeButton;


    static final Object[] vehicleColumnNames = {"Vehicle Number", "Type", "Cross Time", "Direction", "Status", "Length", "Emission Rate", "Segment"};
    static final Object[] intersectionsColumnNames = {"Phase Number", "Phase Duration"};
    static final Object[] statisticColumnsNames = {"Segment No.", "No. of vehicles waiting", "Total wait length", "Avg crossing time"};

    public GUIApplication(ArrayList<Vehicle> vehicles, ArrayList<Intersection> intersections, String outputFile) {


        crossingStatistics = new CrossingStatistics(vehicles, intersections);



        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1500, 1200));
        mainPanel.setLayout(null);

        //process vehicle arraylist and display

        this.vehicles = vehicles;

        //convert the input data into array of arrays
        Object[][] vehicleRowData = convertVehicleIntoArrayOfArrays();

        // Set table model for vehicleTable

        vehicleTableModel = new DefaultTableModel(vehicleRowData, vehicleColumnNames);
        vehicleTable = new JTable(vehicleTableModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.setPreferredScrollableViewportSize(new Dimension(600, 300));
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.setBounds(30, 10, 600, 300);
        mainPanel.add(scrollPane);


        this.intersections = intersections;

        String[][] intersectionRowData = convertIntersectionIntoArrayOfArrays();

        intersectionTable = new JTable(intersectionRowData, intersectionsColumnNames);
        intersectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        intersectionTable.setPreferredScrollableViewportSize(new Dimension(300, 300));
        JScrollPane intersectionDataScrollPane = new JScrollPane(intersectionTable);
        intersectionDataScrollPane.setBounds(660, 10, 300, 300);
        mainPanel.add(intersectionDataScrollPane);


        String[][] statisticData = convertStatisticsIntoArrayOfArrays();
        statisticTableModel = new DefaultTableModel(statisticData, statisticColumnsNames);
        statisticTable = new JTable(statisticTableModel);
        statisticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statisticTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        JScrollPane statisticDataScrollPane = new JScrollPane(statisticTable);
        statisticDataScrollPane.setBounds(980, 10, 500, 300);
        mainPanel.add(statisticDataScrollPane);


        JLabel co2Label = new JLabel("CO2");
        co2Label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        co2Label.setBounds(900, 770, 40, 40);
        mainPanel.add(co2Label);

        totalEmissionTextField = new JTextField();
        totalEmissionTextField.setText(String.valueOf(crossingStatistics.totalEmissions));
        totalEmissionTextField.setEditable(false);
        totalEmissionTextField.setBounds(900, 800, 100, 40);
        mainPanel.add(totalEmissionTextField);


        addVehicleLabel = new JLabel("Add Vehicle");
        addVehicleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        addVehicleLabel.setBounds(80, 350, 100, 100);
        mainPanel.add(addVehicleLabel);

        addVehiclePanel = new JPanel();
        addVehiclePanel.setLayout(null);

        JLabel vehicleNumberLabel = new JLabel("Vehicle Number");
        vehicleNumberLabel.setBounds(10, 10, 100, 30);
        addVehiclePanel.add(vehicleNumberLabel);
        vehicleNumberTextField = new JTextField();
        vehicleNumberTextField.setBounds(10, 40, 100, 30);
        addVehiclePanel.add(vehicleNumberTextField);

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeLabel.setBounds(120, 10, 100, 30);
        addVehiclePanel.add(vehicleTypeLabel);
        vehicleTypeCombobox = new JComboBox(VehicleType.values());
        vehicleTypeCombobox.setBounds(120, 40, 100, 30);
        addVehiclePanel.add(vehicleTypeCombobox);

        JLabel crossTimeLabel = new JLabel("Cross Time");
        crossTimeLabel.setBounds(250, 10, 100, 30);
        addVehiclePanel.add(crossTimeLabel);
        crossTimeTextField = new JTextField(10);
        crossTimeTextField.setBounds(240, 40, 100, 30);
        addVehiclePanel.add(crossTimeTextField);

        JLabel directionLabel = new JLabel("Direction");
        directionLabel.setBounds(370, 10, 130, 30);
        addVehiclePanel.add(directionLabel);
        directionCombobox = new JComboBox(Direction.values());
        directionCombobox.setBounds(360, 40, 130, 30);
        addVehiclePanel.add(directionCombobox);

        JLabel crossStatusLabel = new JLabel("Cross Status");
        crossStatusLabel.setBounds(490, 10, 130, 30);
        addVehiclePanel.add(crossStatusLabel);
        statusCombobox = new JComboBox(CrossStatus.values());
        statusCombobox.setSelectedItem(CrossStatus.WAITING);
        statusCombobox.setBounds(490, 40, 130, 30);
        addVehiclePanel.add(statusCombobox);

        JLabel vehicleLengthLabel = new JLabel("Vehicle length");
        vehicleLengthLabel.setBounds(620, 10, 130, 30);
        addVehiclePanel.add(vehicleLengthLabel);
        lengthTextField = new JTextField();
        lengthTextField.setBounds(620, 40, 130, 30);
        addVehiclePanel.add(lengthTextField);

        JLabel emissionRateLabel = new JLabel("Emission Rate");
        emissionRateLabel.setBounds(770, 10, 130, 30);
        addVehiclePanel.add(emissionRateLabel);
        emissionRateTextField = new JTextField();
        emissionRateTextField.setBounds(760, 40, 130, 30);
        addVehiclePanel.add(emissionRateTextField);

        JLabel segmentLabel = new JLabel("Segment");
        segmentLabel.setBounds(900, 10, 130, 30);
        addVehiclePanel.add(segmentLabel);
        segmentComboBox = new JComboBox(Segment.values());
        segmentComboBox.setBounds(890, 40, 130, 30);
        addVehiclePanel.add(segmentComboBox);

        addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.setBounds(1050, 40, 150, 30);
        addVehiclePanel.add(addVehicleButton);

        addVehiclePanel.setBounds(60, 400, 1400, 400);
        mainPanel.add(addVehiclePanel);


        closeButton = new JButton("Close");
        closeButton.setBounds(1100, 800, 100, 40);
        mainPanel.add(closeButton);


        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.setSize(1440, 600);
        this.pack();

        setTitle("Vehicle Inventory");
        setVisible(true);

        addVehicleButton.addActionListener(this);
        closeButton.addActionListener(this);
    }


    private Object[][] convertVehicleIntoArrayOfArrays() {

        Object[][] rowData = new String[vehicles.size()][];

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            rowData[i] = new String[8];

            rowData[i][0] = String.valueOf(v.vehicleNumber);
            rowData[i][1] = String.valueOf(v.type);
            rowData[i][2] = String.valueOf(v.crossTime);
            rowData[i][3] = String.valueOf(v.direction);
            rowData[i][4] = String.valueOf(v.status);
            rowData[i][5] = String.valueOf(v.length);
            rowData[i][6] = String.valueOf(v.emissionRate);
            rowData[i][7] = v.segment.name();
        }

        return rowData;
    }

    private String[][] convertIntersectionIntoArrayOfArrays() {

        String[][] rowData = new String[intersections.size()][];

        for (int i = 0; i < intersections.size(); i++) {
            Intersection intersection = intersections.get(i);
            rowData[i] = new String[7];

            rowData[i][0] = String.valueOf(intersection.phaseNumber);
            rowData[i][1] = String.valueOf(intersection.phaseDuration);
        }

        return rowData;
    }

    private String[][] convertStatisticsIntoArrayOfArrays() {

        String[][] rowData = new String[crossingStatistics.crossingPerSegment.size()][];

        int itr = 0;

        for (CrossingStatisticsPerSegment crossingStatisticsPerSegment : crossingStatistics.crossingPerSegment.values()) {
            rowData[itr] = new String[4];
            rowData[itr][0] = crossingStatisticsPerSegment.segment.name();
            rowData[itr][1] = String.valueOf(crossingStatisticsPerSegment.numberOfVehicleWaiting);
            rowData[itr][2] = String.valueOf(crossingStatisticsPerSegment.totalLengthOfWaitingVehicles);
            rowData[itr][3] = String.valueOf(crossingStatisticsPerSegment.avgWaitingTime);

            itr++;
        }

        return rowData;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVehicleButton) {
            String vehicleNumber = vehicleNumberTextField.getText();
            VehicleType vehicleType = (VehicleType) vehicleTypeCombobox.getSelectedItem();
            String crossTime = crossTimeTextField.getText();
            Direction direction = (Direction) directionCombobox.getSelectedItem();
            CrossStatus status = (CrossStatus) statusCombobox.getSelectedItem();
            String length = lengthTextField.getText();
            String emissionRate = emissionRateTextField.getText();
            Segment segment = (Segment) segmentComboBox.getSelectedItem();

            if (vehicleNumber.length() != 0 && vehicleType.name().length() != 0 && crossTime.length() != 0 && direction.name().length() != 0
                    && status.name().length() != 0 && length.length() != 0 && emissionRate.length() != 0 && segment.name().length() != 0) {

                // Create a new vehicle object with the values entered in the text fields
                Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType, Integer.parseInt(crossTime), direction, status, Integer.parseInt(length), Integer.parseInt(emissionRate), segment);

                // Add the new vehicle to the list and table model
                vehicles.add(vehicle);
                vehicleTableModel.addRow(new Object[]{vehicleNumber, vehicleType, crossTime, direction, status, length, emissionRate, segment});

                //refresh
                crossingStatistics.refreshStatistics(vehicles, intersections);

                String[][] statisticData = convertStatisticsIntoArrayOfArrays();

                for (int i = 0; i < statisticData.length; i++) {
                    for (int j = 0; j < statisticData[i].length; j++) {
                        statisticTableModel.setValueAt(statisticData[i][j], i, j);
                    }
                }

                // Clear the text fields
                vehicleNumberTextField.setText("");
                vehicleTypeCombobox.setSelectedIndex(0);
                crossTimeTextField.setText("");
                directionCombobox.setSelectedIndex(0);
                crossTimeTextField.setText("");
                lengthTextField.setText("");
                emissionRateTextField.setText("");
                segmentComboBox.setSelectedIndex(0);
            }
        } else if (e.getSource() == closeButton) {
            String reportContent = createReport();

            Object[][] contentToBeSaved = convertVehicleIntoArrayOfArrays();

            String vehicleData = convertToCsv(contentToBeSaved);

            ParseUtils.writeToFile("vehicles.csv", vehicleData);

            ParseUtils.writeToFile("output.txt", reportContent);
            JOptionPane.showMessageDialog(this,
                    "Writing report to output file, click ok to close application...");
            System.exit(0);
        }
    }

    private String createReport() {
        String reportItemOneHeading = "Total number of vehicles crossed per phase:\n";
        String reportItemOne = reportItemOneHeading + getVehicleCrossedPerPhase();

        String reportItemTwo = "Average wait time to cross: " + crossingStatistics.avgWaitingTimeToCross + "\n";

        String reportItemThree = "Total emissions: " + crossingStatistics.totalEmissions + "\n";

        return reportItemOne + reportItemTwo + reportItemThree;
    }

    private String getVehicleCrossedPerPhase() {

        List<List<String>> reportContents = new ArrayList<>();

        reportContents.add(new ArrayList<>(Arrays.asList("Phase No.", "Vehicle Crossed")));

        for (CrossingStatisticsPerSegment crossingStats : crossingStatistics.crossingPerSegment.values()) {
            if (crossingStats.vehiclesCrossedPerPhase != null) {
                crossingStats.vehiclesCrossedPerPhase.forEach((key, value) ->
                        reportContents.add(new ArrayList<>(Arrays.asList(key.name(),
                                String.valueOf(value.size())))));
            }
        }

        String toReturn = StringUtil.formatTable(reportContents, false);

        return toReturn;
    }

    private String convertToCsv(Object[][] data) {

        StringBuilder toReturn = new StringBuilder();

        for (Object[] datum : data) {
            toReturn.append(Arrays.stream(datum).map(String::valueOf).collect(Collectors.joining(",")));
            toReturn.append("\n");
        }

        return toReturn.toString();
    }
}
