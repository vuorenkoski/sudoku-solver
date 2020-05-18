package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;

/**
 * Luokan metodit toteuttavat brute-force haun.
 * @author Lauri Vuorenkoski
 */
public class BruteForce {
    private static final DecimalFormat DF3 = new DecimalFormat("#.###");
    
    /**
     * Metodi ratkaisee taulukon.Metodi tyäydentää parametrinaan saamansa taulukon. 
     * 
     * @param grid Ratkaistava ruudukko. 
     * @return Ratkaisemiseen kulunut aika
     */
    public static double solve(Grid grid) {
        int empty = grid.numberOfEmptyCells();
        System.out.println("Algoritmi: Brute-force\n  Tyhjiä ruutuja: " + empty);
        double time = (double) System.nanoTime() / 1000000;
        if (next(grid, 1, 1)) {
            System.out.println("  Vastaus löytyi");
        } else {
            System.out.println("  Vastausta ei löytynyt");
        }
        time = (double) System.nanoTime() / 1000000 - time;
        if (time < 1000) { 
            System.out.println("  Aika (ms):" + DF3.format(time));
        } else {
            System.out.println("  Aika (sekuntia):" + DF3.format(time / 1000));
        }
        return time;
    }
    
    private static boolean next(Grid grid, int x, int y) {
        if (grid.numberOfEmptyCells() == 0) {
            return true;
        }
        while (grid.getCell(x, y) != 0) {
            x++;
            if (x == grid.getGridSize() + 1) {
                x = 1;
                y++;
            }
        }
        for (int i = 1; i <= grid.getGridSize(); i++) {
            grid.setCell(x, y, i);
            if (grid.checkCell(x, y) && next(grid, x, y)) {
                return true;
            }
        }
        grid.setCell(x, y, 0);
        return false;
    }   
}
