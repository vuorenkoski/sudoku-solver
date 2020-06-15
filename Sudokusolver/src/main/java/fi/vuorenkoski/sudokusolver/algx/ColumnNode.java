package fi.vuorenkoski.sudokusolver.algx;

/**
 * Matriisin sarake, joka on linkitetty vasempaan ja oikeaan sarakkeeseen sekä sarakkeen ensimmäiseen soluun
 * @author Lauri Vuorenkoski
 */
public class ColumnNode {
    public MatrixNode down;
    public ColumnNode left;
    public ColumnNode right;
    public ColumnNode nextInSizegroup;
    public ColumnNode previousInSizegroup;
    public boolean deleted;
    public int number;
    public int size;
    private ColumnNode columnSizeGroups[];

    public ColumnNode(int number, ColumnNode columnSizeGroups[]) {
        this.down = null;
        this.left = null;
        this.right = null;
        this.deleted = false;
        this.number = number;
        this.size = 0;
        this.columnSizeGroups = columnSizeGroups;

        if (columnSizeGroups != null) {
            insertToSizegroup();
        } else {
            this.nextInSizegroup = null;
        }
    }

    /**
     * Poistaa sarakkeen sarakkeiden linkitetystä listasta.
     */
    public void delete() {
        removeFromSizegroup();
        this.deleted = true;
        if (this.right != null) {
            this.right.left = this.left;
        }
        this.left.right = this.right;
    }

    /**
     * Palauttaa sarakkeen sarakkeiden linkitettyn listaan.
     */
    public void undelete() {
        this.deleted = false;
        if (this.right != null) {
            this.right.left = this;
        }
        this.left.right = this;
        insertToSizegroup();
    }

    public void increaseSize() {
        removeFromSizegroup();
        this.size++;
        insertToSizegroup();
    }
      
    public void decreaseSize() {
        removeFromSizegroup();
        this.size--;
        insertToSizegroup();
    }
    
    /**
     * Poistaa sarakkeen koon mukaisesta linkitetystä listasta.
     * Listoja on kolmelle koolle: 0, 1 ja 2.
     */
    private void removeFromSizegroup() {
        if (this.size < 3) {
            this.previousInSizegroup.nextInSizegroup = this.nextInSizegroup;
            if (this.nextInSizegroup != null) {
                this.nextInSizegroup.previousInSizegroup = this.previousInSizegroup;
            }
        }
    }
    
    /**
     * Lisää sarakkeen koon mukaiseen linkitettyyn listaan.
     * Listoja on kolmelle koolle: 0, 1 ja 2.
     */
    private void insertToSizegroup() {
        if (this.size < 3) {
            this.previousInSizegroup = columnSizeGroups[this.size];
            this.nextInSizegroup = columnSizeGroups[this.size].nextInSizegroup;
            columnSizeGroups[this.size].nextInSizegroup = this;
            if (this.nextInSizegroup != null) {
                this.nextInSizegroup.previousInSizegroup = this;
            }
        }
    }
}
