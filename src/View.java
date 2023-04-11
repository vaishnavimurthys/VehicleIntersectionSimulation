import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public JPanel mainPanel;
    public JPanel addVehiclePanel;
    public JTextField vehicleNumberTextField;
    public JComboBox<VehicleType> vehicleTypeCombobox;
    public JTextField crossTimeTextField;
    public JComboBox<Direction> directionCombobox;
    public JComboBox<CrossStatus> statusCombobox;
    public JTextField lengthTextField;
    public JTextField emissionRateTextField;
    public JButton addVehicleButton;
    public JTable vehicleTable;
    public JComboBox<Segment> segmentComboBox;
    public JTable intersectionTable;
    public JTable statisticTable;
    public JLabel addVehicleLabel;
    public JTextField totalEmissionTextField;
    public JButton closeButton;
    public JLabel currentPhase;
    public JButton startSimulationButton;
    public JButton stopSimulationButton;
    private static final String CURRENT_PHASE_STRING = "Current Phase: ";

    View() {

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1500, 1200));
        mainPanel.setLayout(null);


        vehicleTable = new JTable();
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.setPreferredScrollableViewportSize(new Dimension(600, 300));
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.setBounds(30, 10, 600, 300);
        mainPanel.add(scrollPane);

        intersectionTable = new JTable();
        intersectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        intersectionTable.setPreferredScrollableViewportSize(new Dimension(300, 300));
        JScrollPane intersectionDataScrollPane = new JScrollPane(intersectionTable);
        intersectionDataScrollPane.setBounds(660, 10, 300, 300);
        mainPanel.add(intersectionDataScrollPane);


        statisticTable = new JTable();
        statisticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statisticTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        JScrollPane statisticDataScrollPane = new JScrollPane(statisticTable);
        statisticDataScrollPane.setBounds(980, 10, 500, 300);
        mainPanel.add(statisticDataScrollPane);


        JLabel co2EmissionsLabel = new JLabel("CO2 (kg)");
        co2EmissionsLabel.setBounds(900, 770, 40, 40);
        mainPanel.add(co2EmissionsLabel);

        totalEmissionTextField = new JTextField();
        totalEmissionTextField.setEditable(false);
        totalEmissionTextField.setBounds(900, 800, 100, 40);
        mainPanel.add(totalEmissionTextField);

        currentPhase = new JLabel(CURRENT_PHASE_STRING.concat(Phase.ONE.name()));

        currentPhase.setBounds(500, 770, 200, 40);
        mainPanel.add(currentPhase);


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
        vehicleTypeCombobox = new JComboBox<>(VehicleType.values());
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
        directionCombobox = new JComboBox<>(Direction.values());
        directionCombobox.setBounds(360, 40, 130, 30);
        addVehiclePanel.add(directionCombobox);

        JLabel crossStatusLabel = new JLabel("Cross Status");
        crossStatusLabel.setBounds(490, 10, 130, 30);
        addVehiclePanel.add(crossStatusLabel);
        statusCombobox = new JComboBox<>(CrossStatus.values());
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
        segmentComboBox = new JComboBox<>(Segment.values());
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

        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.setBounds(100, 800, 150, 40);
        mainPanel.add(startSimulationButton);

        stopSimulationButton = new JButton("Stop Simulation");
        stopSimulationButton.setBounds(300, 800, 150, 40);
        mainPanel.add(stopSimulationButton);


        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.setSize(1440, 600);
        this.pack();

        setTitle("Vehicle Inventory");
        setVisible(true);
    }

    public void setCurrentPhaseText(Phase currentPhase) {
        this.currentPhase.setText(CURRENT_PHASE_STRING.concat(currentPhase.name()));
    }

    public void setTotalEmissionText(String totalEmission) {
        this.totalEmissionTextField.setText(totalEmission);
    }

}
