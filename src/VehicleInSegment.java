public class VehicleInSegment extends Vehicle implements Runnable {

    int vehiclePosition;
    final RoadIntersection roadIntersection;

    public VehicleInSegment(String vehicleNumber, VehicleType type, int crossTime, Direction direction, CrossStatus status, int length, int emissionRate, Segment segment, int vehiclePosition, RoadIntersection roadIntersection) {
        super(vehicleNumber, type, crossTime, direction, status, length, emissionRate, segment);
        this.vehiclePosition = vehiclePosition;
        this.roadIntersection = roadIntersection;
    }


    @Override
    public void run() {


    }

    public void crossIntersection() {

    }


}
