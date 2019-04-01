public class RelationPair {
    public RelationPair(Coordinate smaller, Coordinate higher) {
        this.smaller = smaller;
        this.higher = higher;
    }

    public Coordinate getSmaller() {
        return smaller;
    }

    public void setSmaller(Coordinate smaller) {
        this.smaller = smaller;
    }

    public Coordinate getHigher() {
        return higher;
    }

    public void setHigher(Coordinate higher) {
        this.higher = higher;
    }

    private Coordinate smaller;
    private Coordinate higher;

    public String toString()
    {
        return smaller.getRow() + " " + smaller.getColumn()
                + " < " + higher.getRow() + " " + higher.getColumn();
    }

}
