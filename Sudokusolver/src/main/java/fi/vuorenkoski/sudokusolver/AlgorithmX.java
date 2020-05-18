package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;

// https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
//
//    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
//    2. Otherwise choose a column c (deterministically).
//    3. Choose a row r such that Ar, c = 1 (nondeterministically).
//    4. Include row r in the partial solution.
//    5. For each column j such that Ar, j = 1,
//        for each row i such that Ai, j = 1,
//            delete row i from matrix A.
//        delete column j from matrix A.
//    6. Repeat this algorithm recursively on the reduced matrix A.


/**
 * Luokan metodit toteuttavat Algotirithm X haun.
 * @author Lauri Vuorenkoski
 */
public class AlgorithmX {
    private static final DecimalFormat DF3 = new DecimalFormat("#.###");

    /**
     * Metodi ratkaisee taulukon. Metodi täydentää parametrinaan saamansa taulukon. 
     * 
     * @param grid Ratkaistava ruudukko. 
     * @return Ratkaisemiseen kulunut aika
     */
    public static double solve(Grid grid) {
        int empty = grid.numberOfEmptyCells();
        System.out.println("Algoritmi: Brute-force\n  Tyhjiä ruutuja: " + empty);
        double time = (double) System.nanoTime() / 1000000;

        // Luodaan taulukko Täydellistä peitettä varten
        int[][] constraintTable = createTable(grid);
//        printTable(constraintTable);

        // Luodaan taulukon perusteella matriisi joka linkitetty vertikaalisesti ja horisontaalisesti 
        RowNode rowRoot = new RowNode();
        ColumnNode columnRoot = new ColumnNode();
        createMatrix(rowRoot, columnRoot);
        
        // Matriisin avulla käydään läpi Algorithmx
        solveExactCover(rowRoot, columnRoot);

        time = (double) System.nanoTime() / 1000000 - time;
        if (time < 1000) { 
            System.out.println("  Aika (ms):" + DF3.format(time));
        } else {
            System.out.println("  Aika (sekuntia):" + DF3.format(time / 1000));
        }
        return time;
    }

    private static void createMatrix(RowNode rowRoot, ColumnNode columnRoot) {
        
    }
    
    private static void solveExactCover(RowNode rowRoot, ColumnNode columnRoot) {
        
    }
    
    private static int[][] createTable(Grid grid) {
        int size = grid.getSize();
        int gridSize = size * size;
        int dataSize = gridSize * gridSize;
        
        int[][] table = new int[dataSize * gridSize][dataSize * 4];
        for (int i = 0; i < dataSize * gridSize; i++) {
            for (int j = 0; j < dataSize * 4; j++) {
                table[i][j] = 0;
            }
        }
        
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int value = 0; value < gridSize; value++) {
                    if (grid.getCell(x + 1, y + 1) == 0 || grid.getCell(x + 1, y + 1) == value + 1) {
                        // rajoite: yksi luku solua kohden
                        table[(y * dataSize) + (x * gridSize) + value][(y * gridSize) + x] = 1;
                        // rajoite: yksi luku riviä kohden
                        table[(y * dataSize) + (x * gridSize) + value][dataSize + (y * gridSize) + value] = 1;
                        // rajoite: yksi luku saraketta kohden
                        table[(y * dataSize) + (x * gridSize) + value][2 * dataSize + (x * gridSize) + value] = 1;
                        // rajoite: yksi luku ryhmää kohden
                        table[(y * dataSize) + (x * gridSize) + value][3 * dataSize + ((y / size) * size + (x / size)) * gridSize + value] = 1;
                    }
                }
            }
        }
        return table;
    }
    
    private static void printTable(int[][] table) {
        for (int i = 0; i < 81 * 9; i++) {
            for (int j = 0; j < 81 * 4; j++) {
                if (table[i][j] == 1) {
                    System.out.print("x");
                } else {
                    System.out.print(".");                    
                }
            }
            System.out.println("");
        }
    }
}
