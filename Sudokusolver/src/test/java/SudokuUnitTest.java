import fi.vuorenkoski.sudokusolver.BruteForce;
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
        BruteForce.solve(grid);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel22_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertTrue(grid.equals(grid2));
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
        BruteForce.solve(grid);
        
        Grid grid2 = new Grid(3);
        fileReader = new Scanner(new File("src/test/testLevel22_complete.ss"));
        for (int i = 0; i < 11; i++) {
            lines[i]=fileReader.nextLine();
        }
        grid2.insertData(lines);      
        assertFalse(grid.equals(grid2));
    }
}
