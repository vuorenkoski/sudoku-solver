package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin rivi, joka on linkitetty alempaan riviin ja rivin ensimmäiseen solmuun
 * @author Lauri Vuorenkoski
 */
public class RowNode {
    public MatrixNode right;
    private RowNode down;
    private RowNode nextDeleted;
    private boolean deleted;
    private boolean included;
    private int number;

    public RowNode(int number) {
        this.down = null;
        this.right = null;
        this.nextDeleted = null;
        this.deleted = false;
        this.included = false;
        this.number = number;
    }
    
    public MatrixNode getRight() {
        return right;
    }

    public RowNode getDown() {
        return down;
    }

    public RowNode getNextDeleted() {
        return nextDeleted;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isIncluded() {
        return included;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setRight(MatrixNode right) {
        this.right = right;
    }

    public void setDown(RowNode down) {
        this.down = down;
    }

    /**
     * Metodi poistaa kyseisen rivin matriisista. 
     * Metodi samalla poistaa kaikkien rivin solmujen sarakelinkityksen.
     */
    public void delete() {
        this.deleted = true;

        // poistetaan rivin solujen sarake linkitykset
        MatrixNode x = this.right;
        while (x != null) {
            x.delete();
            if (x.down != null) {
                x.down.up = x.up;
            }
            if (x.up != null) {
                x.up.down = x.down;
            } else {
                x.column.down = x.down;
            }

            x = x.right;
        }
    }

    public void undelete() {
        this.deleted = false;

        // palautetaan rivin solujen sarake linkitykset
        MatrixNode x = this.right;
        while (x != null) {
            x.undelete();
            if (x.down != null) {
                x.down.up = x;
            }
            if (x.up != null) {
                x.up.down = x;
            } else {
                x.column.down = x;
            }
            
            x = x.right;
        }
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public void setNextDeleted(RowNode nextDeleted) {
        this.nextDeleted = nextDeleted;
    }
}
