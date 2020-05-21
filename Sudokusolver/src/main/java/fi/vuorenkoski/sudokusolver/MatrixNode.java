package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin solmu, joka on linkitetty ylempään, alempaan, vasempaan ja oikeaan
 * @author Lauri Vuorenkoski
 */
public class MatrixNode {
    private MatrixNode up;
    private MatrixNode down;
    private MatrixNode downPermanent;
    private MatrixNode left;
    private MatrixNode right;
    private MatrixNode rightPermanent;
    private ColumnNode column;
    private RowNode row;
    private boolean deleted;

    public MatrixNode(ColumnNode column, RowNode row) {
        this.up = null;
        this.down = null;
        this.downPermanent = null;
        this.left = null;
        this.right = null;
        this.rightPermanent = null;
        this.deleted = false;
        this.column = column;
        this.row = row;
        this.column.increseSize();
    }

    public MatrixNode getUp() {
        return up;
    }

    public MatrixNode getDown() {
        return down;
    }

    public MatrixNode getDownPermanent() {
        return downPermanent;
    }

    public MatrixNode getLeft() {
        return left;
    }

    public MatrixNode getRight() {
        return right;
    }

    public MatrixNode getRightPermanent() {
        return rightPermanent;
    }

    public ColumnNode getColumn() {
        return column;
    }

    public RowNode getRow() {
        return row;
    }
    
    public void setUp(MatrixNode up) {
        this.up = up;
    }

    public void setDown(MatrixNode down) {
        this.down = down;
        this.downPermanent = down;
    }

    public void setLeft(MatrixNode left) {
        this.left = left;
    }

    public void setRight(MatrixNode right) {
        this.right = right;
    }
    
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.column.decreseSize();
        }
    }
    
    public void undelete() {
        if (this.deleted) {
            this.deleted = false;
            this.column.increseSize();
        }
    }
}
