public class RoadIntersection {
    public boolean isEmpty;
    public VehicleInSegment currentVehicle;

    public int crossingTime;

    RoadIntersection() {
        isEmpty = true;
    }

    public void moveVehicleToIntersection(VehicleInSegment vehicle) {
        this.isEmpty = false;
        this.currentVehicle = vehicle;
        this.crossingTime = vehicle.crossTime;
    }
}
