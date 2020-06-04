package fi.vuorenkoski.sudokusolver;

import java.text.DecimalFormat;

/**
 * Luokan metodit toteuttavat Algotirithm X haun.
 * @author Lauri Vuorenkoski
 */
public class AlgorithmX {
    private static final DecimalFormat DF3 = new DecimalFormat("#.###");

    /**
     * Metodi ratkaisee sudokun. Metodi täydentää parametrinaan saamansa sudokun. 
     * 
     * @param grid Ratkaistava sudoku 
     * @param completedGrid Metodi täyttää tähän sudokun ratkaisun
     * @return Ratkaisemiseen kulunut aika
     */
    public static double solve(Grid grid, Grid completedGrid) {
        System.out.println("Algoritmi: Algorithm X");
        double time = (double) System.nanoTime() / 1000000;

        // Valmistellaan matriisi
        ColumnNode columnSizeGroups[] = new ColumnNode[3];
        for (int i=0;i<3;i++) {
            columnSizeGroups[i]=new ColumnNode(0, null);
        }
        RowNode rowRoot = new RowNode(0);
        ColumnNode columnRoot = new ColumnNode(0, null);
        createMatrix(grid, rowRoot, columnRoot, columnSizeGroups);
        System.out.println("  Valmistelu (ms):" + DF3.format((double) System.nanoTime() / 1000000 - time));
        
        // Matriisin avulla käydään läpi Algorithmx
        if (solveExactCover(rowRoot, columnRoot, columnSizeGroups, 0)) {
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
    private static void createMatrix(Grid grid, RowNode rowRoot, ColumnNode columnRoot, ColumnNode columnSizes[]) {
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
            cPrevious.right = new ColumnNode(i, columnSizes);
            cPrevious.right.left = cPrevious;
            cPrevious = cPrevious.right;
            columnArray[i] = cPrevious;
        }
        
        // luodaan riveistä yhteen suuntaan linkitetty lista ja aputaulukko
        for (int i = 0; i < gridSize * gridSize * gridSize; i++) {
            rPrevious.down = new RowNode(i);
            rPrevious = rPrevious.down;
            rowArray[i] = rPrevious;
        }
        
        rPrevious = rowRoot;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int value = 0; value < gridSize; value++) {
                    rPrevious = rPrevious.down;
                    if (grid.getCell(x + 1, y + 1) == 0 || grid.getCell(x + 1, y + 1) == value + 1) {
                        // rajoite: yksi luku solua kohden
                        rPrevious.right = new MatrixNode(columnArray[(y * gridSize) + x], rPrevious);
                        insertColumnLinks(rPrevious.right, columnBottomArray);
                        // rajoite: yksi luku riviä kohden
                        MatrixNode node = rPrevious.right;
                        node.right = new MatrixNode(columnArray[dataSize + (y * gridSize) + value], rPrevious);
                        insertColumnLinks(node.right, columnBottomArray);
                        // rajoite: yksi luku saraketta kohden
                        node = node.right;
                        node.right = new MatrixNode(columnArray[2 * dataSize + (x * gridSize) + value], rPrevious);
                        insertColumnLinks(node.right, columnBottomArray);
                        // rajoite: yksi luku ryhmää kohden
                        node = node.right;
                        node.right = new MatrixNode(columnArray[3 * dataSize + ((y / size) * size + (x / size)) * gridSize + value], rPrevious);
                        insertColumnLinks(node.right, columnBottomArray);
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
                    r.included = true;
                    MatrixNode nodeX = r.right;
                    while (nodeX != null) {
                        MatrixNode nodeY = nodeX.column.down;
                        while (nodeY != null) {
                            nodeY.row.delete();
                            nodeY = nodeY.down;
                        }
                        nodeX.column.delete();
                        nodeX = nodeX.right;
                    }
                }
            }
        }
    }
    
    private static void insertColumnLinks(MatrixNode node, MatrixNode[] columnBottomArray) {
        int column = node.column.number;
        if (columnBottomArray[column] == null) {
            node.column.down = node;
            columnBottomArray[column] = node;
        } else {
            columnBottomArray[column].down = node;
            node.up = columnBottomArray[column];
            columnBottomArray[column] = node;
        }
    }
    
    /**
     * Metodi ratkaisee linkkimatriisin kuvamaan täydellisen peitteen ongelman.Metodi palauttaa true jos ratkaisu löytyi, muuten false
 Täydellisen peitteen muodostaa ne rivisolmut, joiden status on  "included"
     * @param rowRoot Rivisolmujen juuri
     * @param columnRoot Sarakesolmujen juuri
     * @param columnSizeGroups Sarakesolmujen kokojoukkojen juurisolmut 
     * @return 
     */
    private static boolean solveExactCover(RowNode rowRoot, ColumnNode columnRoot, ColumnNode columnSizeGroups[], int syvyys) {      
        RowNode deletedRowsRoot = new RowNode(0);
        RowNode deletedRowPointer = deletedRowsRoot;

        //    1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        if (columnRoot.right == null) {
            return true;
        }
        //    2. Otherwise choose a column c (deterministically).
        
        ColumnNode c;
        if (columnSizeGroups[0].nextInSizegroup!=null) {
            return false;
        }
        if (columnSizeGroups[1].nextInSizegroup!=null) {
            c = columnSizeGroups[1].nextInSizegroup;
        } else {
//            System.out.println(syvyys);
            c = columnSizeGroups[2].nextInSizegroup;
//            if (c==null) System.out.println("ONGELMA!");
        }
        
        //    3. Choose a row r such that A(r, c) = 1 (nondeterministically).        
        MatrixNode node = c.down;
        while (node != null) {
            RowNode r = node.row;
        //    4. Include row r in the partial solution.
            r.included = true;
            
        //    5. For each column j such that A(r, j) = 1,
            MatrixNode x = r.right;
            while (x != null) {
        //        for each row i such that A(i, j) = 1,
                MatrixNode y = x.column.down;
                while (y != null) {
        //            delete row i from matrix A.
                    if (!y.row.deleted) {
                        y.row.delete();
                        deletedRowPointer.nextDeleted = y.row;
                        deletedRowPointer = y.row;
                    }
                    y = y.down;
                }
        //        delete column j from matrix A.
                if (!x.column.deleted) {
                    x.column.delete();
                } 
                x = x.right;
            }
            
        //    6. Repeat this algorithm recursively on the reduced matrix A.  
            if (solveExactCover(rowRoot, columnRoot, columnSizeGroups, syvyys+1)) {
                return true;
            }
            
        // Vaiheessa 5 poistetut rivit ja sarakkeet palautetaan
            r.included = false;
            
            x = r.right;
            while (x != null) {
                if (x.column.deleted) {
                    x.column.undelete();
                } 
                x = x.right;
            }
           
            deletedRowPointer.nextDeleted = null;
            deletedRowPointer = deletedRowsRoot;
            deletedRowPointer = deletedRowPointer.nextDeleted;
            while (deletedRowPointer != null) {
                deletedRowPointer.undelete();
                deletedRowPointer = deletedRowPointer.nextDeleted;
            }
            deletedRowPointer = deletedRowsRoot;
        
        // valitaan seuraava rivi
            node = node.down;
        }
        return false;
    }
       
    /**
     * Metodi täyttää sudoku-ruudokun käyttäen rivisolmu -listaa. 
     */
    private static void fillGrid(Grid grid, RowNode rowRoot) {
        int gridSize = grid.getGridSize();
        RowNode row = rowRoot.down;
        while (row != null) {
            if (row.included) {
                int value = row.number % gridSize;
                int i = row.number / gridSize;
                grid.setCell(i % gridSize + 1, i / gridSize + 1, value + 1);
            }
            row = row.down;
        }
    }
}
