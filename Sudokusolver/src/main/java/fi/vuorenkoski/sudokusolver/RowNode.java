package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin rivi, joka on linkitetty alempaan riviin ja rivin ensimmäiseen solmuun
 * @author Lauri Vuorenkoski
 */
public class RowNode {
    public MatrixNode right;
    public RowNode down;
    public RowNode nextDeleted;
    public boolean deleted;
    public boolean included;
    public int number;

    public RowNode(int number) {
        this.down = null;
        this.right = null;
        this.nextDeleted = null;
        this.deleted = false;
        this.included = false;
        this.number = number;
    }
    
    /**
     * Metodi poistaa kyseisen rivin matriisista. 
     * Metodi samalla poistaa kaikkien rivin solmujen sarakelinkityksen.
     */
    public void delete() {
        this.deleted = true;

        // poistetaan rivin solut
        MatrixNode x = this.right;
        while (x != null) {
            x.delete();
            x = x.right;
        }
    }

    public void undelete() {
        this.deleted = false;

        // palautetaan rivin solut
        MatrixNode x = this.right;
        while (x != null) {
            x.undelete();           
            x = x.right;
        }
    }
}
