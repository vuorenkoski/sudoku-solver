package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin sarake, joka on linkitetty vasempaan, oikeaan ja ylimpään soluun
 * @author Lauri Vuorenkoski
 */
public class ColumnNode implements Comparable<ColumnNode> {
    private MatrixNode down;
    private MatrixNode downPermanent;
    private ColumnNode left;
    private ColumnNode right;
    private ColumnNode nextDeleted;
    private boolean deleted;
    private int number;
    private int size;

    public ColumnNode(int number) {
        this.down = null;
        this.downPermanent = null;
        this.left = null;
        this.right = null;
        this.nextDeleted = null;
        this.deleted = false;
        this.number = number;
        this.size = 0;
    }

    public MatrixNode getDown() {
        return down;
    }

    public ColumnNode getRight() {
        return right;
    }

    public ColumnNode getNextDeleted() {
        return nextDeleted;
    }

    public int getSize() {
        return size;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDown(MatrixNode down) {
        this.down = down;
        this.downPermanent = down;
    }

    public void setLeft(ColumnNode left) {
        this.left = left;
    }

    public void setRight(ColumnNode right) {
        this.right = right;
    }

    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            if (this.right != null) {
                this.right.setLeft(this.left);
            }
            this.left.setRight(this.right);
        }
    }

    public void undelete() {
        this.deleted = false;
        if (this.right != null) {
            this.right.setLeft(this);
        }
        this.left.setRight(this);
    }

    public void setNextDeleted(ColumnNode nextDeleted) {
        this.nextDeleted = nextDeleted;
    }
    
    public void increseSize() {
        this.size++;
    }
    
    public void decreseSize() {
        this.size--;
    }

    @Override
    public int compareTo(ColumnNode t) {
        if (this.deleted) {
            if (t.isDeleted()) {
                return 0;
            }
            return 1;
        }
        if (t.isDeleted()) {
            return -1;
        }
        return this.size - t.getSize();
    }
}
