package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;

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
        System.out.println("Algoritmi: Algorithm X");
        double time = (double) System.nanoTime() / 1000000;

        // Valmistellaan matriisi
        RowNode rowRoot = new RowNode(0);
        ColumnNode columnRoot = new ColumnNode(0);
        createMatrix(grid, rowRoot, columnRoot);
        System.out.println("  Valmistelu (ms):" + DF3.format((double) System.nanoTime() / 1000000 - time));
        
        // Matriisin avulla käydään läpi Algorithmx
        if (solveExactCover(rowRoot, columnRoot)) {
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
     * Metodi luo Sudoku-ruudukon perusteella täydellisen peitteen matriisi, jonka solut linkitetty vertikaalisesti ja horisontaalisesti.
     * 
     */
    private static void createMatrix(Grid grid, RowNode rowRoot, ColumnNode columnRoot) {
        ColumnNode[] columnArray = new ColumnNode[grid.getGridSize() * grid.getGridSize() * 4];
        MatrixNode[] columnBottomArray = new MatrixNode[grid.getGridSize() * grid.getGridSize() * 4];
        RowNode[] rowArray = new RowNode[grid.getGridSize() * grid.getGridSize() * grid.getGridSize()];
        ColumnNode cPrevious = columnRoot;
        RowNode rPrevious = rowRoot;

        int size = grid.getSize();
        int gridSize = size * size;
        int dataSize = gridSize * gridSize;
        
        // luodaan sarakkeista kahteen suuntaan linkitetty lista ja aputaulukko
        for (int i = 0; i < gridSize * gridSize * 4; i++) {
            cPrevious.setRight(new ColumnNode(i));
            cPrevious.getRight().setLeft(cPrevious);
            cPrevious = cPrevious.getRight();
            columnArray[i] = cPrevious;
        }
        
        // luodaan riveistä yhteen suuntaan linkitetty lista ja aputaulukko
        for (int i = 0; i < gridSize * gridSize * gridSize; i++) {
            rPrevious.setDown(new RowNode(i));
            rPrevious = rPrevious.getDown();
            rowArray[i] = rPrevious;
        }
        
        rPrevious = rowRoot;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int value = 0; value < gridSize; value++) {
                    rPrevious = rPrevious.getDown();
                    if (grid.getCell(x + 1, y + 1) == 0 || grid.getCell(x + 1, y + 1) == value + 1) {
                        // rajoite: yksi luku solua kohden
                        rPrevious.setRight(new MatrixNode(columnArray[(y * gridSize) + x], rPrevious));
                        insertColumnLinks(rPrevious.getRight(), columnBottomArray);
                        // rajoite: yksi luku riviä kohden
                        MatrixNode node = rPrevious.getRight();
                        node.setRight(new MatrixNode(columnArray[dataSize + (y * gridSize) + value], rPrevious));
                        insertColumnLinks(node.getRight(), columnBottomArray);
                        // rajoite: yksi luku saraketta kohden
                        node = node.getRight();
                        node.setRight(new MatrixNode(columnArray[2 * dataSize + (x * gridSize) + value], rPrevious));
                        insertColumnLinks(node.getRight(), columnBottomArray);
                        // rajoite: yksi luku ryhmää kohden
                        node = node.getRight();
                        node.setRight(new MatrixNode(columnArray[3 * dataSize + ((y / size) * size + (x / size)) * gridSize + value], rPrevious));
                        insertColumnLinks(node.getRight(), columnBottomArray);
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
                    MatrixNode nodeX = r.getRight();
                    while (nodeX != null) {
                        MatrixNode nodeY = nodeX.getColumn().getDown();
                        while (nodeY != null) {
                            nodeY.getRow().delete();
                            nodeY = nodeY.getDown();
                        }
                        nodeX.getColumn().delete();
                        nodeX = nodeX.getRight();
                    }
                }
            }
        }
    }
    
    private static void insertColumnLinks(MatrixNode node, MatrixNode[] columnBottomArray) {
        int column = node.getColumn().getNumber();
        if (columnBottomArray[column] == null) {
            node.getColumn().setDown(node);
            columnBottomArray[column] = node;
        } else {
            columnBottomArray[column].setDown(node);
            node.setUp(columnBottomArray[column]);
            columnBottomArray[column] = node;
        }
    }
    
    /**
     * Metodi ratkaisee linkkimatriisin kuvamaan täydellisen peitteen ongelman.
     * Metodi palauttaa true jos ratkaisu löytyi, muuten false
     * Täydellisen peitteen muodostaa ne rivisolmut, joiden status on  "included"
     */
    private static boolean solveExactCover(RowNode rowRoot, ColumnNode columnRoot) {      
        RowNode deletedRows = new RowNode(0);
        RowNode deletedRowPointer = deletedRows;
        
        //    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        //    2. Otherwise choose a column c (deterministically).
        ColumnNode c = chooseSmallestColumn(columnRoot);
        if (c == null) {
            return true;
        }
        
        //    3. Choose a row r such that A(r, c) = 1 (nondeterministically).        
        MatrixNode node = c.getDown();
        while (node != null) {
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
                } 
                x = x.getRight();
            }
            
        //    6. Repeat this algorithm recursively on the reduced matrix A.  
            if (solveExactCover(rowRoot, columnRoot)) {
                return true;
            }
            
        // Vaiheessa 5 poistetut rivit ja sarakkeet palautetaan
            r.setIncluded(false);
            
            x = r.getRight();
            while (x != null) {
                if (x.getColumn().isDeleted()) {
                    x.getColumn().undelete();
                } 
                x = x.getRight();
            }
           
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
    private static ColumnNode chooseSmallestColumn(ColumnNode rootColumn) {
        ColumnNode column = rootColumn.getRight();
        int min = 9999;
        ColumnNode minColumn = null;
        while (column != null) {
            if (column.size < min) {
                minColumn = column;
                min = column.size;
            }
            column = column.right;
        }
        return minColumn;
    }

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
}
