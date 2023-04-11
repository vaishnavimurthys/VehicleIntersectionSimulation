public enum VehicleType {
    CAR, TRUCK, BUS;

    public static VehicleType getRandom() {
        return VehicleType.values()[(int) Utils.generateRandomNumber(0, VehicleType.values().length - 1)];
    }
}
