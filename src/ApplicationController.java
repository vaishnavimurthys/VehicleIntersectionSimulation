import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationController implements ActionListener, Runnable {

    private final View view;
    private final ApplicationModel applicationModel;

    public View getView() {
        return view;
    }

    public ApplicationModel getApplicationModel() {
        return applicationModel;
    }

    public ApplicationController(View view, ApplicationModel applicationModel) {
        this.view = view;
        this.applicationModel = applicationModel;

        this.view.addVehicleButton.addActionListener(this);
        this.view.closeButton.addActionListener(this);
        this.view.startSimulationButton.addActionListener(this);
        this.view.stopSimulationButton.addActionListener(this);

        //run the refresh thread to refresh screen every second
        Thread uiRefreshThread = new Thread(this);
        uiRefreshThread.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.addVehicleButton) {
            String vehicleNumber = this.view.vehicleNumberTextField.getText();
            VehicleType vehicleType = (VehicleType) this.view.vehicleTypeCombobox.getSelectedItem();
            String crossTime = this.view.crossTimeTextField.getText();
            Direction direction = (Direction) this.view.directionCombobox.getSelectedItem();
            CrossStatus status = (CrossStatus) this.view.statusCombobox.getSelectedItem();
            String length = this.view.lengthTextField.getText();
            String emissionRate = this.view.emissionRateTextField.getText();
            Segment segment = (Segment) this.view.segmentComboBox.getSelectedItem();

            if (vehicleNumber.length() != 0 && vehicleType.name().length() != 0 && crossTime.length() != 0 && direction.name().length() != 0
                    && status.name().length() != 0 && length.length() != 0 && emissionRate.length() != 0 && segment.name().length() != 0) {

                // Create a new vehicle object with the values entered in the text fields
                Vehicle vehicle = new Vehicle(vehicleNumber, vehicleType, Integer.parseInt(crossTime), direction, status, Integer.parseInt(length), Integer.parseInt(emissionRate), segment);

                VehicleInSegment lastVehicleInSegment;

                try {
                    lastVehicleInSegment = this.applicationModel.roadIntersection.getQueueForSegment(vehicle.segment).getLast();
                } catch (Exception ex) {
                    lastVehicleInSegment = null;
                }

                VehicleInSegment vehicleInSegment = VehicleInSegment.createVehicleInSegmentFromVehicle(vehicle, lastVehicleInSegment, this.applicationModel.roadIntersection, this.applicationModel.phaseController, this);
                vehicleInSegment.crossIntersection();
                this.applicationModel.roadIntersection.getQueueForSegment(vehicle.segment).add(vehicleInSegment);

                this.applicationModel.vehicles.add(vehicleInSegment);
                refresh();
            }


            // Clear the text fields
            this.view.vehicleNumberTextField.setText("");
            this.view.vehicleTypeCombobox.setSelectedIndex(0);
            this.view.crossTimeTextField.setText("");
            this.view.directionCombobox.setSelectedIndex(0);
            this.view.crossTimeTextField.setText("");
            this.view.lengthTextField.setText("");
            this.view.emissionRateTextField.setText("");
            this.view.segmentComboBox.setSelectedIndex(0);
        } else if (e.getSource() == this.view.closeButton) {
            String reportContent = createReport();

            Object[][] contentToBeSaved = this.applicationModel.convertVehicleIntoArrayOfArrays();

            String vehicleData = convertToCsv(contentToBeSaved);

            ParseUtils.writeToFile("vehicles.csv", vehicleData);

            ParseUtils.writeToFile("output.txt", reportContent);
            JOptionPane.showMessageDialog(this.view,
                    "Writing report to output file, click ok to close application...");
            System.exit(0);
        } else if (e.getSource() == this.view.startSimulationButton) {
            this.applicationModel.simulationData.setSimulationState(SimulationState.START);
        } else if (e.getSource() == this.view.stopSimulationButton) {
            this.applicationModel.simulationData.setSimulationState(SimulationState.STOP);
        }

    }

    private String createReport() {
        String reportItemOneHeading = "Total number of vehicles crossed per phase:\n";
        String reportItemOne = reportItemOneHeading + getVehicleCrossedPerPhase();

        String reportItemTwo = "Average wait time to cross: " + this.applicationModel.crossingStatistics.avgWaitingTimeToCross + "\n";

        String reportItemThree = "Total emissions: " + this.applicationModel.crossingStatistics.totalEmissions + "\n";

        return reportItemOne + reportItemTwo + reportItemThree;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            if (this.applicationModel.simulationData.getSimulationState() == SimulationState.START) {

                refresh();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void refresh() {
        this.applicationModel.refreshAllData();
        this.view.setCurrentPhaseText(this.applicationModel.getCurrentPhase());
        this.view.setTotalEmissionText(String.valueOf(this.applicationModel.crossingStatistics.totalEmissions));
        this.view.vehicleTable.setModel(this.applicationModel.vehicleTableModel);
        this.view.statisticTable.setModel(this.applicationModel.statisticTableModel);
        this.view.intersectionTable.setModel(this.applicationModel.intersectionTableModel);
    }

    private String getVehicleCrossedPerPhase() {

        List<List<String>> reportContents = new ArrayList<>();

        reportContents.add(new ArrayList<>(Arrays.asList("Phase No.", "Vehicle Crossed")));

        for (CrossingStatisticsPerSegment crossingStats : this.applicationModel.crossingStatistics.crossingPerSegment.values()) {
            if (crossingStats.vehiclesCrossedPerPhase != null) {
                crossingStats.vehiclesCrossedPerPhase.forEach((key, value) ->
                        reportContents.add(new ArrayList<>(Arrays.asList(key.name(),
                                String.valueOf(value.size())))));
            }
        }

        return StringUtil.formatTable(reportContents, false);
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
