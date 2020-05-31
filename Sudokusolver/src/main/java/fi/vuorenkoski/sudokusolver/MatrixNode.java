package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin solmu, joka on linkitetty ylempään, alempaan ja oikeaan solmuun
 * @author Lauri Vuorenkoski
 */
public class MatrixNode {
    public MatrixNode up;
    public MatrixNode down;
    public MatrixNode right;
    public ColumnNode column;
    public RowNode row;
    private boolean deleted;

    public MatrixNode(ColumnNode column, RowNode row) {
        this.up = null;
        this.down = null;
        this.right = null;
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

    public MatrixNode getRight() {
        return right;
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
