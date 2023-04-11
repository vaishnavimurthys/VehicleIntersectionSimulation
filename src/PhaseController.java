public class PhaseController implements Runnable {

    private static final Logger logger = Logger.getInstance();
    Phase currentPhase;
    Segment currentSegment;

    ApplicationController applicationController;


    int cyclesCompleted = 100;

    public static final int PHASE_TIME_CALIBRATION_INTERVAL_IN_CYCLES = 1;

    public PhaseController() {
        currentPhase = Phase.EIGHT;
    }

    @Override
    public void run() {

        int counter = 0;


        while (true) {

            if (this.applicationController.getApplicationModel().simulationData.getSimulationState() == SimulationState.START) {

                if (counter == PHASE_TIME_CALIBRATION_INTERVAL_IN_CYCLES * Phase.values().length) {
                    counter = 0;
                    recalibratePhaseDurations();
                }

                Phase nextPhase = Phase.nextPhase(currentPhase);

                int durationOfNextPhase = this.applicationController.getApplicationModel().intersections
                        .stream()
                        .filter(intersection -> intersection.phaseNumber == nextPhase.ordinal() + 1)
                        .findFirst()
                        .get()
                        .phaseDuration;

                logger.log("[INFO] - [PHASE_CHANGE] Switching to Phase-" + nextPhase + " for " + durationOfNextPhase + "s");
                setCurrentPhase(nextPhase);


                PhaseMap.phasesInSegment.forEach((key, value) -> {
                    //if the next phase is present in any of the values then that is the current segment
                    if (value.contains(nextPhase)) {
                        setCurrentSegment(key);
                    }
                });

                //notify all road intersection buffers
                this.applicationController.getApplicationModel().roadIntersection.getAllRoadIntersectionBufferMap().values().forEach(it -> it.switchPhase(nextPhase));


                try {
                    Thread.sleep(durationOfNextPhase * 1000L);

                    /**
                     * Check to ensure that if a vehicle takes more time than neccessaru
                     * to cross then do not change phase until the intersection is free again
                     */
                    while (!this.applicationController.getApplicationModel().roadIntersection.intersectionIsEmptyState.get()) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                counter++;

                if (counter % Phase.values().length == 0) {
                    cyclesCompleted++;
                }
            }
        }
    }

    public synchronized void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public synchronized Phase getCurrentPhase() {
        return this.currentPhase;
    }

    public synchronized void setCurrentSegment(Segment currentSegment) {
        this.currentSegment = currentSegment;
    }

    public synchronized Segment getCurrentSegment() {
        return this.currentSegment;
    }

    public void startPhaseCycles(ApplicationController applicationController) {
        this.applicationController = applicationController;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Recalibrate phase duration depending on the avg wait times for each vehicle
     */
    public void recalibratePhaseDurations() {

        this.applicationController.getApplicationModel().crossingStatistics.crossingPerSegment.forEach((key, value) -> {

            PhaseMap.phasesInSegment.get(key).forEach(phase -> {
                int oldTime = this.applicationController.getApplicationModel().intersections.get(phase.ordinal()).phaseDuration;
                int newTime = 0;
                try {
                    newTime = value.avgWaitTimePerPhase.get(phase);
                } catch (Exception ex) {
                    //do nothing
                }

                if (newTime == 0) {
                    newTime = oldTime;
                } else if (newTime < oldTime) {
                    newTime = oldTime - 2;
                } else {
                    newTime = oldTime + 5;

                    if (newTime > 40) {
                        newTime = 40;
                    }
                }

                this.applicationController.getApplicationModel().intersections.get(phase.ordinal()).phaseDuration = newTime;
            });


        });

    }

}
