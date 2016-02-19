package me.rockybreslow.fractions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Demonstration class for Fraction class
 *
 * @author Rocky Breslow
 * @version 1.0
 * @see Fraction
 */

public class FractionMain {
    /**
     * Reads and enforces a user inputted integer.
     *
     * @param scanner Scanner to read data with
     * @return integer user input
     */
    public static int readInteger(Scanner scanner) {
        // Wait until user inputs a valid integer
        while (!scanner.hasNextInt()) {
            scanner.next();
        }

        return scanner.nextInt();
    }


    /**
     * Checks if a String is parsable as an Integer.
     *
     * @param string String to check
     * @return is parsable
     */
    public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Reads and enforces a user inputted fraction.
     *
     * @param scanner Scanner to read data with
     * @return fraction user input
     * @exception IllegalArgumentException if denominator passed is equal to zero
     */
    public static Fraction readFraction(Scanner scanner) throws IllegalArgumentException {
        String[] strings;

        // Wait until a valid fraction was entered
        do {
            String str = scanner.nextLine();

            strings = str.split("/");
        } while(strings.length != 2 || !isInteger(strings[0]) || !isInteger(strings[1]));

        return new Fraction(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
    }

    /**
     * Deserialize fractions from text file.
     *
     * @param path Path to the file
     * @return List of fractions
     * @throws IOException
     */
    public static List<Fraction> loadFractionsFromFile(Path path) throws IOException {
        // List of fractions to manipulate
        List<Fraction> fractions = new ArrayList<>();

        // File object to check for existence
        File numberListFile = path.toFile();

        // Attempt to read the file if possible and add fractions to the list
        if(!numberListFile.exists()) {
            throw new FileNotFoundException();
        } else if(!numberListFile.canRead()) {
            throw new AccessDeniedException(path.toString());
        } else {
            // Read in all lines of the file to a list
            List<String> lines = Files.readAllLines(path);

            // Every other number we input create a new fraction (numerator, then denominator)
            int numerator = 0;
            boolean doSetDenominator = false;
            for(String line : lines) {
                // We don't want an integer
                if(!isInteger(line)) {
                    continue;
                }

                // Either we're storing our numerator or instantiating our fraction with the denominator
                if(doSetDenominator) {
                    fractions.add(new Fraction(numerator, Integer.parseInt(line)));
                    doSetDenominator = false;
                } else {
                    numerator = Integer.parseInt(line);
                    doSetDenominator = true;
                }
            }
        }

        return fractions;
    }

    public static void main(String[] args) {
        // User input
        Scanner scanner = new Scanner(System.in);

        // List of fractions to manipulate
        List<Fraction> fractions;

        // Try to deserialize fractions, if failure then we handle exception and create empty List
        try {
            fractions = loadFractionsFromFile(FileSystems.getDefault().getPath("numberslist.txt"));
        } catch (IOException e) {
            System.err.println(e.toString() + " occurred reading fractions from file.");
            fractions = new ArrayList<>();
        }

        // Ask for user input until an illegal fraction (division by zero) occurs
        boolean cont = true;
        do {
            System.out.print("Enter fraction (e.g. 1/2). Enter an illegal fraction (e.g. 0/0) to finish. ");

            try {
                fractions.add(readFraction(scanner));
            } catch(IllegalArgumentException e) {
                cont = false;
            }
        } while(cont);

        // Exit if we have no fractions at all
        if(fractions.size() < 1) {
            System.out.println("No fractions read. Exiting...");
            System.exit(0);
        }

        // Dump the fractions that we've collected
        for(int i = 0; i < fractions.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + fractions.get(i));
        }

        // Manipulate fractions
        System.out.print("\nEnter two fraction IDs to manipulate: ");

        // Retrieve fraction indexes
        int indexA = 0;
        do {
            indexA = readInteger(scanner) - 1;
        } while(indexA < 0 || indexA >= fractions.size());

        Fraction fractionA = fractions.get(indexA);

        int indexB = 0;
        do {
            indexB = readInteger(scanner) - 1;
        } while(indexB < 0 || indexB >= fractions.size());
        Fraction fractionB = fractions.get(indexB);

        System.out.print(
                "[1] Add"      + "\n" +
                "[2] Subtract" + "\n" +
                "[3] Multiply" + "\n" +
                "[4] Divide"   + "\n" +
                "[5] Compare"  + "\n" +
                "\n" +
                "Enter an operation ID to perform: "
        );

        // We need to copy fractionA so we can print our results
        Fraction fractionC = new Fraction(fractionA);

        // Do it
        switch(readInteger(scanner)) {
            case 1:
                fractionC.add(fractionB);
                System.out.print("\n" + fractionA + " + " + fractionB + " = " + fractionC);
                break;
            case 2:
                fractionC.subtract(fractionB);
                System.out.print("\n" + fractionA + " - " + fractionB + " = " + fractionC);
                break;
            case 3:
                fractionC.multiply(fractionB);
                System.out.print("\n" + fractionA + " * " + fractionB + " = " + fractionC);
                break;
            case 4:
                fractionC.divide(fractionB);
                System.out.print("\n" + fractionA + " / " + fractionB + " = " + fractionC);
                break;
            case 5:
                int comparison = fractionA.compareTo(fractionB);

                if(comparison == 1) {
                    System.out.print("\n" + fractionA + " > " + fractionB);
                } else if(comparison == -1) {
                    System.out.print("\n" + fractionA + " < " + fractionB);
                } else {
                    System.out.print("\n" + fractionA + " = " + fractionB);
                }
        }

        // Close our scanner
        scanner.close();
    }

}
