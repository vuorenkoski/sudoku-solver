package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan,  oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class RowNode {
    private MatrixNode right;
    private MatrixNode rightPermanent;
    private RowNode up;
    private RowNode down;
    private RowNode nextDeleted;
    private boolean deleted;
    private boolean included;
    private int number;

    public RowNode(int number) {
        this.down = null;
        this.up = null;
        this.right = null;
        this.rightPermanent = null;
        this.nextDeleted = null;
        this.deleted = false;
        this.included = false;
        this.number = number;
    }
    
    public MatrixNode getRight() {
        return right;
    }

    public RowNode getUp() {
        return up;
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
        this.rightPermanent = right;
    }

    public void setUp(RowNode up) {
        this.up = up;
    }

    public void setDown(RowNode down) {
        this.down = down;
    }

    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            if (this.down != null) {
                this.down.setUp(this.up);
            }
            this.up.setDown(this.down);

            // poistetaan rivin solujen sarake linkitykset
            MatrixNode x = this.rightPermanent;
            while (x != null) {
                x.delete();
                if (x.getDown() != null) {
                    x.getDown().setUp(x.getUp());
                }
                if (x.getUp() != null) {
                    x.getUp().setDown(x.getDown());
                } else {
                    x.getColumn().setDown(x.getDown());
                }
                               
                x = x.getRightPermanent();
            }
        }
    }

    public void undelete() {
        this.deleted = false;
        if (this.down != null) {
            this.down.setUp(this);
        }
        this.up.setDown(this);

        // palautetaan rivin solujen sarake linkitykset
        MatrixNode x = this.rightPermanent;
        while (x != null) {
            x.undelete();
            if (x.getDown() != null) {
                x.getDown().setUp(x);
            }
            if (x.getUp() != null) {
                x.getUp().setDown(x);
            } else {
                x.getColumn().setDown(x);
            }
            
            x = x.getRightPermanent();
        }
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public void setNextDeleted(RowNode nextDeleted) {
        this.nextDeleted = nextDeleted;
    }
       
}
