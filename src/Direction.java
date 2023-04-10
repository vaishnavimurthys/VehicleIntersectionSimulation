public enum Direction {
    STRAIGHT, RIGHT, LEFT;

    public static Direction getRandom() {
        return Direction.values()[(int) Utils.generateRandomNumber(0, Direction.values().length - 1)];
    }
}
