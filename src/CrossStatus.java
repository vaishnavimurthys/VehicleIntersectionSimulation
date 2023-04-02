public enum CrossStatus {
    CROSSED, CROSSING, WAITING;

    public static CrossStatus getRandom() {
        return CrossStatus.values()[(int) Utils.generateRandomNumber(0, CrossStatus.values().length)];
    }
}
