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
     * Metodi ratkaisee taulukon. Metodi täydentää parametrinaan saamansa taulukon. 
     * 
     * @param grid Ratkaistava ruudukko. 
     * @return Ratkaisemiseen kulunut aika
     */
    public static double solve(Grid grid, Grid completedGrid) {
        int empty = grid.numberOfEmptyCells();
        System.out.println("Algoritmi: Algorithm X");
        double time = (double) System.nanoTime() / 1000000;

        // Luodaan taulukko Täydellistä peitettä varten
        int[][] constraintTable = createTable(grid);

        // Luodaan taulukon perusteella matriisi joka linkitetty vertikaalisesti ja horisontaalisesti 
        RowNode rowRoot = new RowNode(0);
        ColumnNode columnRoot = new ColumnNode(0);
        RowNode[] rowArray = new RowNode[grid.getGridSize() * grid.getGridSize() * grid.getGridSize()];
        ColumnNode[] columnArray = new ColumnNode[grid.getGridSize() * grid.getGridSize() * 4];
        createMatrix(constraintTable, grid, rowRoot, columnRoot, rowArray, columnArray);
        
        // Matriisin avulla käydään läpi Algorithmx
        if (solveExactCover(rowRoot, columnRoot, columnArray)) {
            System.out.println("  Vastaus löytyi");
        } else {
            System.out.println("  Vastausta ei löytynyt");
        }
        
        // Luodaan vastaus
        fillGrid(completedGrid, rowArray);

        time = (double) System.nanoTime() / 1000000 - time;
        if (time < 1000) { 
            System.out.println("  Aika (ms):" + DF3.format(time));
        } else {
            System.out.println("  Aika (sekuntia):" + DF3.format(time / 1000));
        }
        return time;
    }

    // Idea tässä on luoda kahteen suuntaan linkitetty lista niin riveittän kuin sarakkeittain
    private static void createMatrix(int[][] table, Grid grid, RowNode rowRoot, ColumnNode columnRoot, RowNode[] rowArray, ColumnNode[] columnArray) {
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
        
        // luodaan rivestä linkitetty lista ja taulukko
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
                    
                    // Rivin linkitys solujen linkityts keskenään
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
    
    private static boolean solveExactCover(RowNode rowRoot, ColumnNode columnRoot, ColumnNode[] columnArray) {      
        ColumnNode deletedColumns = new ColumnNode(0);
        ColumnNode deletedColumnPointer = deletedColumns;
        RowNode deletedRows = new RowNode(0);
        RowNode deletedRowPointer = deletedRows;
        //    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        //    2. Otherwise choose a column c (deterministically).
        ColumnNode c = chooseSmallestColumn(columnArray);
        if (c.isDeleted()) {
            return true;
        }
        //    3. Choose a row r such that A(r, c) = 1 (nondeterministically).        
        MatrixNode node = c.getDown();
        int lkm = 0;
        while (node != null) {
            lkm++;
            RowNode r = node.getRow();
        //    4. Include row r in the partial solution.
            r.setIncluded(true); // otetaan tämä rivi mukaan vastaukseen
        //    5. For each column j such that A(r, j) = 1,
        //        for each row i such that A(i, j) = 1,
        //            delete row i from matrix A.
        //        delete column j from matrix A.
            MatrixNode x = r.getRight();
            while (x != null) {
                MatrixNode y = x.getColumn().getDown();
                while (y != null) {
                    if (!y.getRow().isDeleted()) {
                        y.getRow().delete();
                        deletedRowPointer.setNextDeleted(y.getRow());
                        deletedRowPointer = y.getRow();
                    }
                    y = y.getDown();
                }
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
        
            // seuraava rivi.
            node = node.getDown();
        }
        return false;
    }
    
    private static ColumnNode chooseSmallestColumn(ColumnNode[] columnArray) {
        Arrays.sort(columnArray);
        return columnArray[0];
    }
       
    private static void fillGrid(Grid grid, RowNode[] rowArray) {
        int gridSize = grid.getGridSize();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int i = 0; i < gridSize; i++) {
                    if (rowArray[y * gridSize * gridSize + x * gridSize + i].isIncluded()) {
                        grid.setCell(x + 1, y + 1, i + 1);
                    }
                }
            }
        }
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
    
//    private static void fillGrid(Grid grid, RowNode rowRoot) {
//        RowNode row = rowRoot.getDown();
//        int gridSize = grid.getGridSize();
//        while (row != null) {
//            if (row.isIncluded()) {
//                System.out.println("sfd");
//                int i = row.getNumber();
//                int value = i % gridSize;
//                i = i / gridSize;
//                grid.setCell(i % gridSize, i / gridSize, value);
//            }
//            row = row.getDown();
//        }
//    }
}
