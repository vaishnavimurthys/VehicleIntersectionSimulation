import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RoadIntersectionBuffer {

    public static final Logger logger = Logger.getInstance();
    Segment segment;
    LinkedList<VehicleInSegment> queue;
    Phase currentPhase;
    IntersectionIsEmptyState intersectionIsEmptyState;

    VehicleInSegment lastEnteredVehicle;

    RoadIntersectionBuffer(Segment segment, IntersectionIsEmptyState intersectionIsEmptyState) {
        this.intersectionIsEmptyState = intersectionIsEmptyState;
        this.segment = segment;
        queue = new LinkedList<>();
    }

    public synchronized void enterIntersection(VehicleInSegment vehicle) {
        boolean entered = false;

        while (!entered) {
            try {
                /**
                 * Here we are checking 3 conditions
                 * 1. The current phase and the vehicle phase matches
                 * 2. The vehicle is indeed the first vehicle to pass for this particular phase
                 *    since we maintain queue by segments, checking the first vehicle in the queu
                 *    for a given phase becomes necessary
                 * 3. The intersection is empty
                 */
                if (!(currentPhase == vehicle.phase && queue.stream().filter(it -> it.phase == currentPhase).findFirst().get() == vehicle && intersectionIsEmptyState.get())) {
                    wait();
                } else {
                    entered = true;
                    intersectionIsEmptyState.set(false);
                    logger.log("[INFO] - [VEHICLE_ENTER_INTERSECTION]" + "[PHASE-" + currentPhase + "] " + vehicle);
                }
            } catch (NoSuchElementException ex) {
                logger.log("[INFO] - No vehicles found for Phase-" + currentPhase);
            } catch (InterruptedException ex) {
                logger.log("[ERROR] - Thread interrupted " + vehicle);
            }
        }
    }

    public synchronized void exitIntersection(VehicleInSegment vehicle) {
        logger.log("[INFO] - [VEHICLE_EXIT_INTERSECTION]" + "[PHASE-" + currentPhase + "] " + vehicle);
        lastEnteredVehicle = queue.poll();
        intersectionIsEmptyState.set(true);
        notifyAll();
    }

    public synchronized void switchPhase(Phase nextPhase) {
        currentPhase = nextPhase;
        notifyAll();
    }
}