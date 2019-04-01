public class Coordinate {
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    private int row;
    private int column;

    public Coordinate(char row, int column)
    {
        this.row = (int)row -65;
        this.column = column-1;
    }
}
