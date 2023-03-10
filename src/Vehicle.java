public class Vehicle {
    public String vehicleNumber;
    public VehicleType type;
    public int crossTime;
    public Direction direction;
    public CrossStatus status;
    public int length;
    public int emissionRate;

    public Segment segment;

    public Vehicle(String vehicleNumber, VehicleType type, int crossTime, Direction direction, CrossStatus status, int length, int emissionRate, Segment segment) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.crossTime = crossTime;
        this.direction = direction;
        this.status = status;
        this.length = length;
        this.emissionRate = emissionRate;
        this.segment = segment;
    }
}
