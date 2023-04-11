public class VehicleInSegment extends Vehicle implements Runnable {

    public static Logger logger = Logger.getInstance();
    int vehiclePosition;
    int waitTime;
    final RoadIntersection roadIntersection;
    final PhaseController phaseController;
    ApplicationController applicationController;

    VehicleInSegment preceedingVehicle;

    VehicleInSegment followingVehicle;


    public VehicleInSegment(String vehicleNumber, VehicleType type, int crossTime, Direction direction, CrossStatus status, int length, int emissionRate, Segment segment, int vehiclePosition, RoadIntersection roadIntersection, PhaseController phaseController, ApplicationController applicationController, VehicleInSegment preceedingVehicle) {
        super(vehicleNumber, type, crossTime, direction, status, length, emissionRate, segment);
        this.vehiclePosition = vehiclePosition;
        this.roadIntersection = roadIntersection;
        this.phaseController = phaseController;
        this.applicationController = applicationController;
        this.waitTime = 0;
        this.preceedingVehicle = preceedingVehicle;
        this.followingVehicle = null;
    }

    public static VehicleInSegment createVehicleInSegmentFromVehicle(Vehicle v, VehicleInSegment lastVehicleInSegment, RoadIntersection roadIntersection, PhaseController phaseController, ApplicationController applicationController) {
        VehicleInSegment newVehicle = new VehicleInSegment(v.vehicleNumber, v.type, v.crossTime,
                v.direction, v.status, v.length, v.emissionRate, v.segment,
                lastVehicleInSegment == null ? 0 :
                        lastVehicleInSegment.vehiclePosition + lastVehicleInSegment.length,
                roadIntersection, phaseController, applicationController, lastVehicleInSegment);

        if (lastVehicleInSegment != null) {
            lastVehicleInSegment.followingVehicle = newVehicle;
        }

        return newVehicle;
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        this.roadIntersection.getAllRoadIntersectionBufferMap().get(segment).enterIntersection(this);

        try {
            Thread.sleep(this.crossTime * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.roadIntersection.getAllRoadIntersectionBufferMap().get(segment).exitIntersection(this);

        long endTime = System.currentTimeMillis();

        this.status = CrossStatus.CROSSED;

        this.waitTime = (int) ((int) (endTime - startTime) / 1000L);

        applicationController.refresh();
    }


    public void crossIntersection() {
        Thread vehicleThread = new Thread(this);
        vehicleThread.start();
    }

}
