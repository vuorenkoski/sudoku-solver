package fi.vuorenkoski.sudokusolver;

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

    public static void main(String[] args) {
        String fileName;
        if (args.length == 0) {
            fileName = "sudoku.ss";
            System.out.println("Tiedoston nimeä ei ollut argumenttina, käytetään:" + fileName);
        } else {
            fileName = args[0];
            System.out.println("Syöte tiedosto:" + fileName);
        }
        
        File file = new File(fileName);
        try {
            Scanner fileReader = new Scanner(file);
            String line;
            int count = 0;
            double sumBrute = 0;
            double sumX = 0;
            boolean differences = false;
            int size = Integer.valueOf(fileReader.nextLine());
            while (fileReader.hasNextLine()) {
                line = fileReader.nextLine();
                if (!line.isEmpty()) {
                    String[] lines = new String[size * size + size - 1];
                    lines[0] = line;
                    for (int i = 1; i < size * size + size - 1; i++) {
                        line = fileReader.nextLine();
                        lines[i] = line;
                    }
                    Grid grid = new Grid(size);
                    grid.insertData(lines);
                    Grid completedGrid = new Grid(size);
                    count++;
                    // ratkaiseminen
                    System.out.println("SUDOKU " + count);
                    System.out.println(grid);
                    sumX += AlgorithmX.solve(grid, completedGrid);
                    System.out.println(completedGrid);
                    sumBrute += BruteForce.solve(grid);
                    if (grid.equals(completedGrid)) {
                        System.out.println("Ratkaisut on identtiset");
                    } else {
                        differences = true;
                        System.out.println("RATKAISUT EROAVAT");
                    }
                    System.out.println("");
                }
            }
            fileReader.close();
            if (differences) {
                System.out.println("RATKAISUISSA ON EROJA");
            }
            double average = sumX / count;
            if (average < 1000) {
                System.out.println("Keskimääräinen aika (Algorithm X): " + DF3.format(average) + " ms");
            } else {
                System.out.println("Keskimääräinen aika (Algorithm X): " + DF3.format(average / 1000) + " sekuntia");
            }
            average = sumBrute / count;
            if (average < 1000) {
                System.out.println("Keskimääräinen aika (Brute-Force): " + DF3.format(average) + " ms");
            } else {
                System.out.println("Keskimääräinen aika (Brute-Force): " + DF3.format(average / 1000) + " sekuntia");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei löytynyt: " + fileName);
        }
    }    
}
