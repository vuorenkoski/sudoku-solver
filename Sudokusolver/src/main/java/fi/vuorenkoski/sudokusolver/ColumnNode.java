package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan,  oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class ColumnNode {
    private MatrixNode down;
    private ColumnNode left;
    private ColumnNode right;
    private boolean included;

    public ColumnNode() {
        this.down = null;
        this.left = null;
        this.right = null;
        this.included = true;
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

    public boolean isIncluded() {
        return included;
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

    public void setIncluded(boolean included) {
        this.included = included;
    }
}
