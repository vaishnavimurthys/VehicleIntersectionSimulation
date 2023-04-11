public class SimulationData {

    private SimulationState simulationState;


    public synchronized SimulationState getSimulationState() {
        return simulationState;
    }

    public synchronized void setSimulationState(SimulationState simulationState) {
        this.simulationState = simulationState;
    }
}
