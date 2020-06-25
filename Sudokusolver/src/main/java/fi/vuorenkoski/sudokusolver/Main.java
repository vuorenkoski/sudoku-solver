package fi.vuorenkoski.sudokusolver;

import fi.vuorenkoski.sudokusolver.algx.AlgorithmX;
import fi.vuorenkoski.sudokusolver.brute.BruteForce;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Sudoku-solver - Main class
 * @author Lauri Vuorenkoski
 */
public class Main {
    private static final DecimalFormat DF3 = new DecimalFormat("#.###");
    private static String fileName;
    private static boolean algX, algB, showFinal, showOriginal, showBranching, checkAll, differences, solved;
    private static int count;
    private static double bSum, xSum;
    

    public static void main(String[] args) {
        System.out.println("Sudoku-solver v. 1.0");
        fileName = "sudoku.ss";
        algB = false;
        algX = false;
        showFinal = false;
        showOriginal = false;
        showBranching = false;
        checkAll = false;
        boolean noSettings = true;
        for (String arg:args) {
            if (arg.charAt(0) == '-') {
                noSettings = false;
                for (int i = 0; i < arg.length(); i++) {
                    changeSetting(arg.charAt(i));
                    if (arg.charAt(i) == 'h') {
                        showHelp();
                        return;
                    }
                }
            } else {
                fileName = arg;
            }
        }
        if (noSettings) {
            algB = true;
            algX = true;
            settings();
        } else {
            showSettings();
        }
        
        File file = new File(fileName);
        try {
            count = 0;
            bSum = 0;
            xSum = 0;
            differences = false;
            solved = true;

            Scanner fileReader = new Scanner(file);
            while (true) {
                Grid sudoku = readNextSudoku(fileReader);
                if (sudoku == null) {
                    break;
                }
                solveSudokus(sudoku);
            }
            fileReader.close();
            
            showSummary();

        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei löytynyt: " + fileName);
        }
    }  
    
    private static String printTime(double time) {
        if (time < 1000) { 
            return "" + DF3.format(time) + " ms";
        } else {
            return "" + DF3.format(time / 1000) + " sekuntia";
        }
    }

    private static Grid readNextSudoku(Scanner fileReader) {
        if (!fileReader.hasNextLine()) {
            return null;
        }

        int size = Integer.valueOf(fileReader.nextLine());
        String[] lines = new String[size * size + size - 1];
        for (int i = 0; i < size * size + size - 1; i++) {
            lines[i] = fileReader.nextLine();
        }
        Grid grid = new Grid(size);
        grid.insertData(lines);
        return grid;
    }
    
    private static void solveSudokus(Grid sudoku) {
        count++;
        System.out.println("\nSUDOKU " + count);
        System.out.println("Sudokun koko: " + sudoku.getGridSize() + "x" + sudoku.getGridSize());  
        System.out.println("Tyhjat ruudut: " + sudoku.numberOfEmptyCells());
        Grid bCompleted = new Grid(sudoku.getSize());
        Grid xCompleted = new Grid(sudoku.getSize());

        if (showOriginal) {
            System.out.println("\n" + sudoku);
        }
        if (algX) {
            System.out.println("Algoritmi: Algorithm X");
            Double time = AlgorithmX.solve(sudoku, xCompleted, showBranching);
            if (time < 0) {
                System.out.println("  VASTAUSTA EI LÖYTYNYT");
                time = -time;
                solved = false;
            } else if (checkAll && !xCompleted.checkIntegrity()) {
                System.out.println("  VASTAUS ON VÄÄRIN");
                solved = false;
            }
            System.out.println("  aika: " + printTime(time));
            xSum += time;
        }
        if (algB) {
            System.out.println("Algoritmi: Brute-force");
            Double time = BruteForce.solve(sudoku, bCompleted);
            if (time < 0) {
                System.out.println("  VASTAUSTA EI LÖYTYNYT");
                time = -time;
                solved = false;
            } else if (checkAll && !bCompleted.checkIntegrity()) {
                System.out.println("  VASTAUS ON VÄÄRIN");
                solved = false;
            }
            System.out.println("  aika: " + printTime(time));
            bSum += time;
        }

        if (algB && algX && !bCompleted.equals(xCompleted)) {
            differences = true;
            System.out.println("RATKAISUT EROAVAT");
        }

        if (showFinal) {
            if (algX) {
                System.out.println("\n" + xCompleted);
            } else {
                System.out.println("\n" + bCompleted);
            }
        }
    }
    
    private static void showSummary() {
        System.out.println("\nYHTEENVETO");
        if (!solved) {
            System.out.println("VÄHINTÄÄN YHTÄ SUDOKUA EI VOITU RATKAISTA TAI RATKAISU OLI VÄÄRIN");
        }
        if (algX) {
            System.out.println("Keskimääräinen aika (Algorithm X): " + printTime(xSum / count));
        }
        if (algB) {
            System.out.println("Keskimääräinen aika (Brute-Force): " + printTime(bSum / count));
        }
        if (algB && algX) {
            System.out.println("Brute-force vei " +  DF3.format(bSum / xSum) + " kertaa enemmän aikaa");
            if (differences) {
                System.out.println("RATKAISUISSA ON EROJA");
            } else {
                System.out.println("Kaikki ratkaisut ovat identtisiä");
            }
        }
    }
    
    private static void settings() {
        Scanner reader = new Scanner(System.in);
        System.out.println("");
        String line = " ";
        while (!line.isEmpty()) {
            showSettings();
            System.out.println("Muuta asetusta painamalla suluissa olevaa kirjainta ja enter.");
            System.out.println("Algoritmi käynnistyy painamalla vain enter.");
            System.out.println("");
            System.out.print("\nAnna asetus: ");
            line = reader.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'l' || line.charAt(i) == 'L') {
                    System.out.println("Syötä tiedoston nimi:");
                    fileName = reader.nextLine();
                } else {
                    changeSetting(line.charAt(i));
                }
            }
            System.out.println("");
        }
    }
    
    private static char checkmark(boolean check) {
        if (check) {
            return 'X';
        }
        return ' ';
    }
    
    private static void showSettings() {
        System.out.println("");
        System.out.println("Asetukset");
        System.out.println("[" + checkmark(algB) + "] Brute-Force (B)               [" + checkmark(algX) + "] AlgorithmX (X)");
        System.out.println("[" + checkmark(showOriginal) + "] Tulosta sudoku (S)            [" + checkmark(showFinal) + "] Tulosta ratkaistu sudoku (R)");
        System.out.println("[" + checkmark(checkAll) + "] Tarkista valmiit sudokut (T)  [" + checkmark(showBranching) + "] Tulosta haarautumadata (D)");
        System.out.println("");
        System.out.println("Lähdetiedosto(L): " + fileName);
        System.out.println("");
    }
    
    private static void changeSetting(char c) {
        switch (c) {
            case 'b':
                algB = !algB;
                break;
            case 'B':
                algB = !algB;
                break;
            case 'x':
                algX = !algX;
                break;
            case 'X':
                algX = !algX;
                break;
            case 's':
                showOriginal = !showOriginal;
                break;
            case 'S':
                showOriginal = !showOriginal;
                break;
            case 'r':
                showFinal = !showFinal;
                break;
            case 'R':
                showFinal = !showFinal;
                break;
            case 't':
                checkAll = !checkAll;
                break;
            case 'T':
                checkAll = !checkAll;
                break;
            case 'd':
                showBranching = !showBranching;
                break;
            case 'D':
                showBranching = !showBranching;
                break;        
        }
    }
    
    private static void showHelp() {
        System.out.println("");
        System.out.println("Käyttö: sudokusolver [VALITSIMET] [TIEDOSTO]");
        System.out.println("Valitsimet:");
        System.out.println("x - Käytä algorithm X");
        System.out.println("b - Käytä Brute-Force");
        System.out.println("s - Tulosta alkuperäinen sudoku");
        System.out.println("r - Tulosta ratkaisu");
        System.out.println("t - Tarkista kaikki valmiit sudokut");
        System.out.println("d - Tulostaa dataa haarautumisesta (haarautumisen analyysiä varten)");
        System.out.println("");
    }
}
