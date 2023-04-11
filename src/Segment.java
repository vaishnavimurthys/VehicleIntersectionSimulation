public enum Segment {
    S1, S2, S3, S4;

    public static Segment getRandom() {
        return Segment.values()[(int) Utils.generateRandomNumber(0, Segment.values().length - 1)];
    }
}
