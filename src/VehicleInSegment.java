public class VehicleInSegment extends Vehicle implements Runnable {

    int vehiclePosition;

    int waitTime;
    final RoadIntersection roadIntersection;

    final PhaseController phaseController;

    GUIApplication guiApplication;

    TrafficController trafficController;


    public VehicleInSegment(String vehicleNumber, VehicleType type, int crossTime, Direction direction, CrossStatus status, int length, int emissionRate, Segment segment, int vehiclePosition, RoadIntersection roadIntersection, PhaseController phaseController, GUIApplication guiApplication, TrafficController trafficController) {
        // constructor to initialize the class variables

        super(vehicleNumber, type, crossTime, direction, status, length, emissionRate, segment);
        this.vehiclePosition = vehiclePosition;
        this.roadIntersection = roadIntersection;
        this.phaseController = phaseController;
        this.guiApplication = guiApplication;
        this.trafficController = trafficController;
        this.waitTime = 0;
    }


    @Override
    public void run() {
        System.out.println("Starting " + this);// print a message indicating the vehicle has started

        long startTime = System.currentTimeMillis();

        synchronized (this.roadIntersection) {

            while (this.status != CrossStatus.CROSSED) {
                System.out.println("VehicleStat: " + this);
                System.out.println("Phase: " + phaseController.currentPhase);

                VehicleInSegment nextVehicle = this.trafficController.queues.get(segment).get(phase).getFirst();

                if (phaseController.currentPhase == phase && nextVehicle == this) {
                    crossIntersection();
                } else {
                    long currTime = System.currentTimeMillis();
                    this.waitTime = (int) (currTime - startTime);
                    this.guiApplication.refresh();
                    this.roadIntersection.notifyAll();
                }
                try {
                    this.roadIntersection.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("Stopping " + this);
    }
    // method to cross the intersection
    public void crossIntersection() {
        System.out.println("Crossing: " + this);
        this.roadIntersection.isEmpty = false;
        this.roadIntersection.currentVehicle = this;

        //do processing here

        try {
            System.out.println("Vehicle no. " + vehicleNumber + " crossing in " + crossTime);
            Thread.sleep(crossTime * 1000L);
            System.out.println("Vehicle no. " + vehicleNumber + " crossed in" + crossTime);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }


        this.status = CrossStatus.CROSSED;
        this.roadIntersection.isEmpty = true;

        this.roadIntersection.notifyAll();
        this.guiApplication.refresh();

        trafficController.removeVehicleFromQueue(this);


        Thread t = new Thread(this);
        t.interrupt();
    }


}
