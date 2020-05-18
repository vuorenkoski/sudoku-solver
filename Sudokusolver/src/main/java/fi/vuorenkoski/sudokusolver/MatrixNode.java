package fi.vuorenkoski.sudokusolver;

/**
 * Matriisin solmu, joka on linkitetty ylempään, alempaan, vasempaan ja oikeaan
 * @author Lauri Vuorenkoski
 */
public class MatrixNode {
    private MatrixNode up;
    private MatrixNode down;
    private MatrixNode left;
    private MatrixNode right;


    public MatrixNode() {
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    public MatrixNode getUp() {
        return up;
    }

    public MatrixNode getDown() {
        return down;
    }

    public MatrixNode getLeft() {
        return left;
    }

    public MatrixNode getRight() {
        return right;
    }

    public void setUp(MatrixNode up) {
        this.up = up;
    }

    public void setDown(MatrixNode down) {
        this.down = down;
    }

    public void setLeft(MatrixNode left) {
        this.left = left;
    }

    public void setRight(MatrixNode right) {
        this.right = right;
    }    
}
