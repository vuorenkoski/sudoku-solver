package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan,  oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class RowNode {
    private MatrixNode right;
    private ColumnNode up;
    private ColumnNode down;
    private boolean included;

    public RowNode() {
        this.down = null;
        this.up = null;
        this.right = null;
        this.included = true;
    }

    public MatrixNode getRight() {
        return right;
    }

    public ColumnNode getUp() {
        return up;
    }

    public ColumnNode getDown() {
        return down;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setRight(MatrixNode right) {
        this.right = right;
    }

    public void setUp(ColumnNode up) {
        this.up = up;
    }

    public void setDown(ColumnNode down) {
        this.down = down;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }
}
