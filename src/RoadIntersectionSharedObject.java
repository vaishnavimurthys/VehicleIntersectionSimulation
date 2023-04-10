public class RoadIntersectionSharedObject implements Runnable {

    // Declare a RoadIntersection instance
    public RoadIntersection roadIntersection;

    // Constructor that takes a RoadIntersection instance as an argument
    public RoadIntersectionSharedObject(RoadIntersection roadIntersection) {
        this.roadIntersection = roadIntersection;
    }

    // Method that keeps the intersection open by waiting on the roadIntersection object
    public void openIntersection() throws InterruptedException {
        synchronized (this.roadIntersection) {
            while (true) {
                this.roadIntersection.wait();
            }
        }
    }

    // Overrides the run method of the Runnable interface
    // Calls the openIntersection method and handles InterruptedException
    @Override
    public void run() {
        try {
            openIntersection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
