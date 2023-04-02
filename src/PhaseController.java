import java.util.*;

public class PhaseController implements Runnable {

    Phase currentPhase;
    int numberOfCyclesCompleted;

    ArrayList<Intersection> intersections;

    public PhaseController(ArrayList<Intersection> intersections) {
        this.intersections = intersections;
        currentPhase = Phase.EIGHT;
    }

    @Override
    public void run() {
        while (true) {
            Phase nextPhase = Phase.nextPhase(currentPhase);

            int durationOfNextPhase = intersections
                    .stream()
                    .filter(intersection -> intersection.phaseNumber == nextPhase.ordinal() + 1)
                    .findFirst()
                    .get()
                    .phaseDuration;

            try {
                System.out.println("Switching phase to: " + nextPhase + " for: " + durationOfNextPhase);
                currentPhase = nextPhase;
                Thread.sleep(durationOfNextPhase * 1000L);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
