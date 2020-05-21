package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;

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
//        printTable(constraintTable);

        // Luodaan taulukon perusteella matriisi joka linkitetty vertikaalisesti ja horisontaalisesti 
        RowNode rowRoot = new RowNode(0);
        ColumnNode columnRoot = new ColumnNode(0);
        RowNode[] rowArray = new RowNode[grid.getGridSize() * grid.getGridSize() * grid.getGridSize()];
        ColumnNode[] columnArray = new ColumnNode[grid.getGridSize() * grid.getGridSize() * 4];
        createMatrix(constraintTable, grid.getGridSize(), rowRoot, columnRoot, rowArray, columnArray);
        
        // Matriisin avulla käydään läpi Algorithmx
       if (solveExactCover(rowRoot, columnRoot, rowArray, columnArray)) {
           System.out.println("  Vastaus löytyi");
       } else {
           System.out.println("  Vastausta ei löytynyt");
       }
        
        // Luodaan vastaus
        fillGrid(completedGrid, grid.getGridSize(), rowArray);
//        System.out.println(solvedGrid);

        time = (double) System.nanoTime() / 1000000 - time;
        if (time < 1000) { 
            System.out.println("  Aika (ms):" + DF3.format(time));
        } else {
            System.out.println("  Aika (sekuntia):" + DF3.format(time / 1000));
        }
        return time;
    }

    // Idea tässä on luoda kahteen suuntaan linkitetty lista niin riveittän kuin sarakkeittain
    private static void createMatrix(int[][] table, int gridSize, RowNode rowRoot, ColumnNode columnRoot, RowNode[] rowArray, ColumnNode[] columnArray) {
        ColumnNode cPrevious = columnRoot;
        RowNode rPrevious = rowRoot;
        MatrixNode rmPrevious = null;
        
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
                    MatrixNode node = new MatrixNode(x, y);
                    
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
                
//                // jos rivi on tyhjä, poistetaan se kokonaan listasta
//                if (rPrevious.getRight() == null) {
//                    rPrevious.delete();
//                }
            }  
        }
    }
    
    private static boolean solveExactCover(RowNode rowRoot, ColumnNode columnRoot, RowNode[] rowArray, ColumnNode[] columnArray) {      
        ColumnNode deletedColumns = new ColumnNode(0);
        ColumnNode deletedColumnPointer = deletedColumns;
        RowNode deletedRows = new RowNode(0);
        RowNode deletedRowPointer = deletedRows;
        //    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        //    2. Otherwise choose a column c (deterministically).
        ColumnNode c = columnRoot.getRight();
        while (c != null && c.isDeleted()) {
            c = c.getRight();
        }
        if (c == null) {
            return true;
        }
        //    3. Choose a row r such that A(r, c) = 1 (nondeterministically).        
        MatrixNode node = c.getDown();
        while (node != null && rowArray[node.getRow()].isDeleted()) {
//            System.out.println(node.getRow() + "," + node.getColumn() + rowArray[node.getRow()].isDeleted()); /////////////////////////////////////
            node = node.getDown();
        }
        int lkm = 0;
        while (node != null) {
            lkm++;
//            System.out.println("testiin("+lkm+"): " + node.getRow() + "," + node.getColumn()); ////////////////////////
            int r = node.getRow();
        //    4. Include row r in the partial solution.
            rowArray[r].setIncluded(true); // otetaan tämä rivi mukaan vastaukseen
        //    5. For each column j such that A(r, j) = 1,
        //        for each row i such that A(i, j) = 1,
        //            delete row i from matrix A.
        //        delete column j from matrix A.
            MatrixNode x = rowArray[r].getRight();
            while (x != null) {
                MatrixNode y = columnArray[x.getColumn()].getDown();
                while (y != null) {
                    if (!rowArray[y.getRow()].isDeleted()) {
                        rowArray[y.getRow()].delete();
                        deletedRowPointer.setNextDeleted(rowArray[y.getRow()]);
                        deletedRowPointer = rowArray[y.getRow()];
                        deletedRowPointer.setNextDeleted(null);
                    }
//                    System.out.println("Delete:" + y.getRow()); ////////////////////////////////////
                    y = y.getDown();
                }
                if (!columnArray[x.getColumn()].isDeleted()) {
                    columnArray[x.getColumn()].delete();
                    deletedColumnPointer.setNextDeleted(columnArray[x.getColumn()]);
                    deletedColumnPointer = columnArray[x.getColumn()];
                    deletedColumnPointer.setNextDeleted(null);
                }
                x = x.getRight();
            }
        //    6. Repeat this algorithm recursively on the reduced matrix A.  
            if (solveExactCover(rowRoot, columnRoot, rowArray, columnArray)) {
                return true;
            } 
        // Vaiheessa 5 poistetut rivit ja sarakkeet palautetaan
            rowArray[r].setIncluded(false);
            
            deletedColumns = deletedColumns.getNextDeleted();
            while (deletedColumns != null) {
                deletedColumns.unDelete();
                deletedColumns = deletedColumns.getNextDeleted();
            }
            deletedColumns = new ColumnNode(0);
            deletedColumnPointer = deletedColumns;
            
            deletedRows = deletedRows.getNextDeleted();
            while (deletedRows != null) {
                deletedRows.unDelete();
                deletedRows = deletedRows.getNextDeleted();
            }
            deletedRows = new RowNode(0);
            deletedRowPointer = deletedRows;
        
            // seuraava rivi.
            node = node.getDown();
//            System.out.println(node); ///////////////////////////////////////////////////
            while (node != null && rowArray[node.getRow()].isDeleted()) {
                node = node.getDown();
            }
        }
//        System.out.println("palautetaan false"); /////////////////////////////////////
        return false;
    }
    
    private static void fillGrid(Grid grid, int gridSize, RowNode[] rowArray) {
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
