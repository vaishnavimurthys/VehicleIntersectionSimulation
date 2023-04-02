public enum Phase {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

    public static Phase nextPhase(Phase currentPhase) {
        int currentPhaseOrdinal = currentPhase.ordinal();
        int nextPhaseOrdinal = currentPhaseOrdinal + 1 == Phase.values().length ? 0 : currentPhaseOrdinal + 1;
        return Phase.values()[nextPhaseOrdinal];
    }
}
