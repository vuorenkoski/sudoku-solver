package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan,  oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class RowNode {
    private MatrixNode right;
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
    }

    public void setUp(RowNode up) {
        this.up = up;
    }

    public void setDown(RowNode down) {
        this.down = down;
    }

    public void delete() {
        this.deleted = true;
    }

    public void unDelete() {
        this.deleted = false;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public void setNextDeleted(RowNode nextDeleted) {
        this.nextDeleted = nextDeleted;
    }
       
}
