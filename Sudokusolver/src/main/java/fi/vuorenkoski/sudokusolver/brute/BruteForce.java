package fi.vuorenkoski.sudokusolver.brute;

import fi.vuorenkoski.sudokusolver.Grid;
import java.text.DecimalFormat;

/**
 * Luokan metodit toteuttavat brute-force haun.
 * @author Lauri Vuorenkoski
 */
public class BruteForce {
    private static final DecimalFormat DF3 = new DecimalFormat("#.###");
    
    /**
     * Metodi ratkaisee taulukon. Metodi täydentää toisena parametrinaan saamansa taulukon. 
     * 
     * @param grid Ratkaistava ruudukko. 
     * @param completedGrid Metodi täyttää tähän sudokun ratkaisun
     * @return Ratkaisemiseen kulunut aika. Negatiivinen jos ratkaisu ei onnistunut.
     */
    public static double solve(Grid grid, Grid completedGrid) {
        int empty = grid.numberOfEmptyCells();
        for (int i = 1; i <= grid.getGridSize(); i++) {
            for (int j = 1; j <= grid.getGridSize(); j++) {
                completedGrid.setCell(i, j, grid.getCell(i, j));
            }
        }
        double time = (double) System.nanoTime() / 1000000;
        boolean ok = nextCell(completedGrid, 1, 1);
        time = (double) System.nanoTime() / 1000000 - time;
        if (!ok) {
            time = -time;
        }
        return time;
    }
    
    /**
     * Rekursiivisesti kutsuttava metodi, joka kokeilee vaihtoehtoja yksi kerrallaan
     * aloittaen parametrina annetusta solusta.
     */
    private static boolean nextCell(Grid grid, int x, int y) {
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
            if (grid.checkCell(x, y) && nextCell(grid, x, y)) {
                return true;
            }
        }
        grid.setCell(x, y, 0);
        return false;
    }   
}
