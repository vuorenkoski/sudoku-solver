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
    public boolean deleted;

    public MatrixNode(ColumnNode column, RowNode row) {
        this.up = null;
        this.down = null;
        this.right = null;
        this.deleted = false;
        this.column = column;
        this.row = row;
        this.column.increaseSize();
    }

    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.column.decreaseSize();
        }
    }
    
    public void undelete() {
        if (this.deleted) {
            this.deleted = false;
            this.column.increaseSize();
        }
    }
}
