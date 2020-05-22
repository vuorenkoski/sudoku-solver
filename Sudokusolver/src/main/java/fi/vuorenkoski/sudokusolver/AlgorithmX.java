package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;
import java.util.Arrays;

// https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X
//
//    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
//    2. Otherwise choose a column c (deterministically).
//    3. Choose a row r such that A(r, c) = 1 (nondeterministically).
//    4. Include row r in the partial solution.
//    5. For each column j such that A(r, j) = 1,
//        for each row i such that A(i, j) = 1,
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
     * Metodi ratkaisee taulukon.Metodi täydentää parametrinaan saamansa taulukon. 
     * 
     * @param grid Ratkaistava sudoku 
     * @param completedGrid Metodi täyttää tähän sudokun ratkaisun
     * @return Ratkaisemiseen kulunut aika
     */
    public static double solve(Grid grid, Grid completedGrid) {
        int empty = grid.numberOfEmptyCells();
        System.out.println("Algoritmi: Algorithm X");
        double time = (double) System.nanoTime() / 1000000;

        // Luodaan taulukko Täydellistä peitettä varten
        int[][] constraintTable = createConstraintTable(grid);

        // Luodaan taulukon perusteella matriisi joka linkitetty vertikaalisesti ja horisontaalisesti 
        RowNode rowRoot = new RowNode(0);
        ColumnNode columnRoot = new ColumnNode(0);

        ColumnNode[] columnArray = new ColumnNode[grid.getGridSize() * grid.getGridSize() * 4];
        createMatrix(constraintTable, grid, rowRoot, columnRoot, columnArray);
        
        // Matriisin avulla käydään läpi Algorithmx
        if (solveExactCover(rowRoot, columnRoot, columnArray)) {
            System.out.println("  Vastaus löytyi");
        } else {
            System.out.println("  Vastausta ei löytynyt");
        }
        
        // Luodaan vastaus
        fillGrid(completedGrid, rowRoot);

        time = (double) System.nanoTime() / 1000000 - time;
        if (time < 1000) { 
            System.out.println("  Aika (ms):" + DF3.format(time));
        } else {
            System.out.println("  Aika (sekuntia):" + DF3.format(time / 1000));
        }
        return time;
    }

    /**
     * Metodi luo 2d taulukosta kahteen suuntaan linkitetyn listan niin riveittän kuin sarakkeittain. 
     * 
     */
    private static void createMatrix(int[][] table, Grid grid, RowNode rowRoot, ColumnNode columnRoot, ColumnNode[] columnArray) {
        RowNode[] rowArray = new RowNode[grid.getGridSize() * grid.getGridSize() * grid.getGridSize()];
        ColumnNode cPrevious = columnRoot;
        RowNode rPrevious = rowRoot;
        MatrixNode rmPrevious = null;
        int gridSize = grid.getGridSize();
        
        // luodaan sarakkeista linkitetty lista ja taulukko
        for (int i = 0; i < gridSize * gridSize * 4; i++) {
            cPrevious.setRight(new ColumnNode(i));
            cPrevious.getRight().setLeft(cPrevious);
            cPrevious = cPrevious.getRight();
            columnArray[i] = cPrevious;
        }
        
        // luodaan rivestä linkitetty lista
        for (int i = 0; i < gridSize * gridSize * gridSize; i++) {
            rPrevious.setDown(new RowNode(i));
            rPrevious.getDown().setUp(rPrevious);
            rPrevious = rPrevious.getDown();
            rowArray[i] = rPrevious;
        }
        
        // Luodaan linkitetyt listat riveittäin ja sarakkeittain
        rPrevious = rowRoot;
        for (int y = 0; y < gridSize * gridSize * gridSize; y++) {
            rPrevious = rPrevious.getDown();
            rmPrevious = null;
            for (int x = 0; x < gridSize * gridSize * 4; x++) {
                if (table[y][x] == 1) {
                    MatrixNode node = new MatrixNode(columnArray[x], rowArray[y]);
                    
                    // Rivin linkitys solujen linkitys keskenään
                    if (rmPrevious == null) {
                        rPrevious.setRight(node);
                    } else {
                        rmPrevious.setRight(node);
                        node.setLeft(rmPrevious);
                    }
                    rmPrevious = node;
                    
                    // Sarakkeen solujen linkitys keskenään, haetaan ensin sarakkeen alin solu
                    if (columnArray[x].getDown() == null) {
                        columnArray[x].setDown(node);
                    } else {
                        MatrixNode bottom = columnArray[x].getDown();
                        while (bottom.getDown() != null) {
                            bottom = bottom.getDown();
                        }
                        bottom.setDown(node);
                        node.setUp(bottom);
                    }
                }
            }  
        }
        
        // Valmiiden solujen osalta poistetaan turhat rivit ja lisätään included merkintä
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (grid.getCell(x + 1, y + 1) != 0) {
                    int i = (y * gridSize * gridSize) + (x * gridSize);
                    RowNode r = rowArray[i + grid.getCell(x + 1, y + 1) - 1];
                    r.setIncluded(true);
                    MatrixNode nodex = r.getRight();
                    while (nodex != null) {
                        MatrixNode nodey = nodex.getColumn().getDown();
                        while (nodey != null) {
                            nodey.getRow().delete();
                            nodey = nodey.getDown();
                        }
                        nodex.getColumn().delete();
                        nodex = nodex.getRight();
                    }
                }
            }
        }
    }
    
    /**
     * Metodi ratkaisee linkkimatriisin kuvamaan täydellisen peitteen ongelman.
     * Metodi palauttaa true jos ratkaisu löytyi, muuten false
     * Vastaus on ne rivisolmut, joiden status on  "included"
     */
    private static boolean solveExactCover(RowNode rowRoot, ColumnNode columnRoot, ColumnNode[] columnArray) {      
        ColumnNode deletedColumns = new ColumnNode(0);
        ColumnNode deletedColumnPointer = deletedColumns;
        RowNode deletedRows = new RowNode(0);
        RowNode deletedRowPointer = deletedRows;
        
        //    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        //    2. Otherwise choose a column c (deterministically).
        ColumnNode c = chooseSmallestColumn(columnArray);
        if (c == null) {
            return true;
        }
        
        //    3. Choose a row r such that A(r, c) = 1 (nondeterministically).        
        MatrixNode node = c.getDown();
        int lkm = 0;
        while (node != null) {
            lkm++;
            RowNode r = node.getRow();
            
        //    4. Include row r in the partial solution.
            r.setIncluded(true);
            
        //    5. For each column j such that A(r, j) = 1,
            MatrixNode x = r.getRight();
            while (x != null) {
        //        for each row i such that A(i, j) = 1,
                MatrixNode y = x.getColumn().getDown();
                while (y != null) {
        //            delete row i from matrix A.
                    if (!y.getRow().isDeleted()) {
                        y.getRow().delete();
                        deletedRowPointer.setNextDeleted(y.getRow());
                        deletedRowPointer = y.getRow();
                    }
                    y = y.getDown();
                }
        //        delete column j from matrix A.
                if (!x.getColumn().isDeleted()) {
                    x.getColumn().delete();
                    deletedColumnPointer.setNextDeleted(x.getColumn());
                    deletedColumnPointer = x.getColumn();
                }
                x = x.getRight();
            }
            
        //    6. Repeat this algorithm recursively on the reduced matrix A.  
            if (solveExactCover(rowRoot, columnRoot, columnArray)) {
                return true;
            }
            
        // Vaiheessa 5 poistetut rivit ja sarakkeet palautetaan
            r.setIncluded(false);
                       
            deletedColumnPointer.setNextDeleted(null);
            deletedColumns = deletedColumns.getNextDeleted();
            while (deletedColumns != null) {
                deletedColumns.undelete();
                deletedColumns = deletedColumns.getNextDeleted();
            }
            deletedColumns = new ColumnNode(0);
            deletedColumnPointer = deletedColumns;
            
            deletedRowPointer.setNextDeleted(null);
            deletedRows = deletedRows.getNextDeleted();
            while (deletedRows != null) {
                deletedRows.undelete();
                deletedRows = deletedRows.getNextDeleted();
            }
            deletedRows = new RowNode(0);
            deletedRowPointer = deletedRows;
        
        // valitaan seuraava rivi
            node = node.getDown();
        }
        return false;
    }
    
    /**
     * Metodi palauttaa sarakesolmun, jonka sarakkeessa on vähiten soluja. 
     */
    private static ColumnNode chooseSmallestColumn(ColumnNode[] columnArray) {
        Arrays.sort(columnArray);
        if (columnArray[0].isDeleted()) {
            return null;
        }
        return columnArray[0];
    }
    // kokeilu, joka kuitenkin hitaampi
//    private static ColumnNode chooseSmallestColumn(ColumnNode rootColumn) {
//        ColumnNode column = rootColumn.getRight();
//        if (column == null) return null;
//        int min = column.getSize();
//        ColumnNode minColumn = column;
//        while (column.getRight() != null) {
//            column = column.getRight();
//            if (column.getSize()<min) {
//                minColumn = column;
//                min = column.getSize();
//            }
//            if (min == 0) return minColumn;
//        }
//        return minColumn;
//    }
    
    /**
     * Metodi täyttää sudoku-ruudokun käyttäen rivisolmu -listaa. 
     */
    private static void fillGrid(Grid grid, RowNode rowRoot) {
        int gridSize = grid.getGridSize();
        RowNode row = rowRoot.getDown();
        while (row != null) {
            if (row.isIncluded()) {
                int value = row.getNumber() % gridSize;
                int i = row.getNumber() / gridSize;
                grid.setCell(i % gridSize + 1, i / gridSize + 1, value + 1);
            }
            row = row.getDown();
        }
    }

    /**
     * Metodi luo sudokusta 2d taulukon, jossa on kuvattu sodukun rajoite-säännöt.
     * Metodi ottaa huomioon sudokun valmiiksi täytetyt ruudut.
     */    
    private static int[][] createConstraintTable(Grid grid) {
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
    
//    private static void printTable(int[][] table) {
//        for (int i = 0; i < 81 * 9; i++) {
//            for (int j = 0; j < 81 * 4; j++) {
//                if (table[i][j] == 1) {
//                    System.out.print("x");
//                } else {
//                    System.out.print(".");                    
//                }
//            }
//            System.out.println("");
//        }
//    }
}
