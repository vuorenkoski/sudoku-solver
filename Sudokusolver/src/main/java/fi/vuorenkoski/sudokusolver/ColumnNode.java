package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan,  oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class ColumnNode {
    private MatrixNode down;
    private ColumnNode left;
    private ColumnNode right;
    private ColumnNode nextDeleted;
    private boolean deleted;
    private int number;

    public ColumnNode(int number) {
        this.down = null;
        this.left = null;
        this.right = null;
        this.nextDeleted = null;
        this.deleted = false;
        this.number = number;
    }

    public MatrixNode getDown() {
        return down;
    }

    public ColumnNode getLeft() {
        return left;
    }

    public ColumnNode getRight() {
        return right;
    }

    public ColumnNode getNextDeleted() {
        return nextDeleted;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDown(MatrixNode down) {
        this.down = down;
    }

    public void setLeft(ColumnNode left) {
        this.left = left;
    }

    public void setRight(ColumnNode right) {
        this.right = right;
    }

    public ColumnNode delete() {
        this.deleted = true;
        return this;
    }

    public void unDelete() {
        this.deleted = false;
    }

    public void setNextDeleted(ColumnNode nextDeleted) {
        this.nextDeleted = nextDeleted;
    }
    
    
}
