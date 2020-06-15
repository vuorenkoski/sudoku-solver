import fi.vuorenkoski.sudokusolver.algx.AlgorithmX;
import fi.vuorenkoski.sudokusolver.brute.BruteForce;
import fi.vuorenkoski.sudokusolver.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Yksikkötestit Sudokusolver -ohjelmaan
 * @author Lauri Vuorenkoski
 */
public class SudokuUnitTest {
    
    public SudokuUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
      
    @Before
    public void setUp() {
    }

    @Test
    public void GridSetOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        grid.setCell(5, 6, 7);
        assertEquals(7, grid.getCell(5, 6));
    }
    
    @Test
    public void GridInsertOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        assertEquals(2, grid.getCell(4, 2));
    }

    @Test
    public void GridEmptyCellsOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        grid.setCell(1, 2, 1);
        grid.setCell(1, 2, 0);
        assertEquals(59, grid.numberOfEmptyCells());
    }

    @Test
    public void GridCheckCellOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        grid.setCell(3, 2, 6);
        assertFalse(grid.checkCell(3, 2));
    }
    
    @Test
    public void BruteForceOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(3);
        BruteForce.solve(grid, completedGrid);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel22_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(completedGrid.equals(grid2));
    }

    @Test
    public void BruteForceOkReturnsPositive() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(3);
        double time = BruteForce.solve(grid, completedGrid);
        
        assertTrue(time>0);
    }
    
    @Test
    public void BruteForceImpossible() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22_mahdoton.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(3);
        double time = BruteForce.solve(grid, completedGrid);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel22_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(time<0);
    }
    
    @Test
    public void AlgorithmXOkLevel22() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel22.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(3);
        AlgorithmX.solve(grid, completedGrid, false);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel22_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(completedGrid.equals(grid2));
    }
    
    @Test
    public void AlgorithmXOkLevel17() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel17.ss"));
        String[] lines = new String[11];
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(3);
        AlgorithmX.solve(grid, completedGrid, false);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel17_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(completedGrid.equals(grid2));
    }


    @Test
    public void AlgorithmXOk16x16() throws FileNotFoundException {
        Grid grid = new Grid(4);
        Scanner fileReader = new Scanner(new File("src/test/4_level148.ss"));
        String[] lines = new String[19];
        for (int i = 0; i < 19; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(4);
        AlgorithmX.solve(grid, completedGrid, false);
        
        assertTrue(completedGrid.checkIntegrity());
    }
    
    @Test
    public void AlgorithmXOk25x25() throws FileNotFoundException {
        Grid grid = new Grid(5);
        Scanner fileReader = new Scanner(new File("src/test/5_sudoku2.ss"));
        String[] lines = new String[29];
        for (int i = 0; i < 29; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(5);
        AlgorithmX.solve(grid, completedGrid, false);
        
        Grid grid2 = new Grid(5);
        fileReader = new Scanner(new File("src/test/5_sudoku2_complete.ss"));
        for (int i = 0; i < 29; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(completedGrid.equals(grid2));
    }

    @Test
    public void GridPrintOk() throws FileNotFoundException {
        Grid grid = new Grid(3);
        Scanner fileReader = new Scanner(new File("src/test/testLevel17.ss"));
        String[] lines = new String[11];
        String output = "";
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
            output = output + lines[i] + "\n";
        }
        fileReader.close();
        grid.insertData(lines);
        assertTrue(grid.toString().equals(output));
    }
    
    @Test
    public void BruteForceOk25x25() throws FileNotFoundException {
        Grid grid = new Grid(5);
        Scanner fileReader = new Scanner(new File("src/test/5_sudoku2.ss"));
        String[] lines = new String[29];
        for (int i = 0; i < 29; i++) {
            lines[i]=fileReader.nextLine();
        }
        fileReader.close();
        grid.insertData(lines);
        Grid completedGrid = new Grid(5);
        BruteForce.solve(grid, completedGrid);
        
        Grid grid2 = new Grid(5);
        fileReader = new Scanner(new File("src/test/5_sudoku2_complete.ss"));
        for (int i = 0; i < 29; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(completedGrid.equals(grid2));
    }
}
