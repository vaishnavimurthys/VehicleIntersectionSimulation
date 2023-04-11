public class IntersectionIsEmptyState {
    private Boolean value;

    IntersectionIsEmptyState() {
        value = true;
    }

    public synchronized Boolean get() {return value;}

    public synchronized void set(Boolean value) {this.value = value;}
}
