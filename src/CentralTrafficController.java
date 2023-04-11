import java.util.ArrayList;

public class CentralTrafficController {

    ArrayList<VehicleInSegment> vehiclesInSegment;
    RoadIntersection roadIntersection;
    PhaseController phaseController;
    VehicleArrivalSimulator vehicleArrivalSimulator;

    ApplicationController applicationController;


    public CentralTrafficController(ArrayList<VehicleInSegment> vehiclesInSegment, RoadIntersection roadIntersection, PhaseController phaseController, VehicleArrivalSimulator vehicleArrivalSimulator, ApplicationController applicationController) {
        this.vehiclesInSegment = vehiclesInSegment;
        this.roadIntersection = roadIntersection;
        this.phaseController = phaseController;
        this.vehicleArrivalSimulator = vehicleArrivalSimulator;
        this.applicationController = applicationController;

        startTrafficController();
    }

    public void startTrafficController() {
        //start phase cycles
        phaseController.startPhaseCycles(applicationController);

        //simulate arrival of vehicles
        vehicleArrivalSimulator.simulateVehicleArrival();


    }
}
