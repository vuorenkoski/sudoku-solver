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
            // poistetaan rivin solujen sarake linkitykset
            if (this.down != null) {
                this.down.up = this.up;
            }
            if (this.up != null) {
                this.up.down = this.down;
            } else {
                this.column.down = this.down;
            }
        }
    }
    
    public void undelete() {
        if (this.deleted) {
            this.deleted = false;
            this.column.increaseSize();
            // palautetaan rivin solujen sarake linkitykset
            if (this.down != null) {
                this.down.up = this;
            }
            if (this.up != null) {
                this.up.down = this;
            } else {
                this.column.down = this;
            }
        }
    }
}
