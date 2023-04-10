import java.util.*;

public class PhaseController implements Runnable {

    Phase currentPhase; // The current phase of the intersection
    int numberOfCyclesCompleted; // The number of cycles completed by the phase controller

    ArrayList<Intersection> intersections; // List of intersections to be managed by the phase controller

    final RoadIntersection roadIntersection; // The road intersection object to be managed by the phase controller

    // Constructor that initializes the intersections and roadIntersection fields
    public PhaseController(ArrayList<Intersection> intersections, RoadIntersection roadIntersection) {
        this.intersections = intersections;
        this.roadIntersection = roadIntersection;
        currentPhase = Phase.EIGHT;
    }

    // The main loop of the phase controller, which changes the phases and manages their durations
    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                // Determine the next phase based on the current phase
                Phase nextPhase = Phase.nextPhase(currentPhase);

                // Find the duration of the next phase by searching the intersections list
                int durationOfNextPhase = intersections
                        .stream()
                        .filter(intersection -> intersection.phaseNumber == nextPhase.ordinal() + 1)
                        .findFirst()
                        .get()
                        .phaseDuration;

                // Log the change of phase and its duration
                System.out.println("Switching phase to: " + nextPhase + " for: " + durationOfNextPhase);

                // Update the current phase and wait for the next phase's duration
                currentPhase = nextPhase;
                try {
                    this.wait(1000);
                    Thread.sleep(durationOfNextPhase * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
