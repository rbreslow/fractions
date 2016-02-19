package me.rockybreslow.fractions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public static void main(String[] args) {
        /** User input */
        Scanner s = new Scanner(System.in);

        /** List of fractions to manipulate */
        List<Fraction> fractions = new ArrayList<>();

        /** The file which numbers are read from */
        File numberListFile = new File("numberslist.txt");

        // Attempt to read the file if possible and add fractions to the list
        if(!numberListFile.exists()) {
            System.out.println("`numberslist.txt` does not exist.");
        } else if(!numberListFile.canRead()) {
            System.out.println("You do not have permission to access `numberslist.txt`.");
        } else {
            try {
                // Read in all lines of the file to a list
                List<String> lines = Files.readAllLines(numberListFile.toPath());

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
            } catch(IOException e) {
                System.out.println("Error reading `numberlist.txt`.");
            }
        }

        // Ask for user input until an illegal fraction (division by zero) occurs
        boolean cont = true;
        do {
            System.out.print("Enter fraction (e.g. 1/2). Enter an illegal fraction (e.g. 0/0) to finish. ");

            try {
                fractions.add(getFraction(s));
            } catch(IllegalArgumentException e) {
                cont = false;
            }
        } while(cont);

        // Dump the fractions that we've collected
        for(int i = 0; i < fractions.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + fractions.get(i));
        }

        // Manipulate fractions
        System.out.print("\nEnter two fraction IDs to manipulate: ");

        // Retrieve fraction indexes
        int indexA = 0;
        do {
            indexA = getInteger(s) - 1;
        } while(indexA < 0 || indexA >= fractions.size());

        Fraction fractionA = fractions.get(indexA);

        int indexB = 0;
        do {
            indexB = getInteger(s) - 1;
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
        switch(getInteger(s)) {
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
    }

    /**
     * Gets and enforces a user inputted integer
     *
     * @param s Scanner to get data with
     * @return integer user input
     */
    public static int getInteger(Scanner s) {
        // Wait until user inputs a valid integer
        while (!s.hasNextInt()) {
            s.next();
        }

        return s.nextInt();
    }


    /**
     * Checks if a String is parsable as an Integer
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
     * Gets and enforces a user inputted fraction
     *
     * @param s Scanner to get data with
     * @return fraction user input
     * @exception IllegalArgumentException if denominator entered is equal to zero.
     */
    public static Fraction getFraction(Scanner s) throws IllegalArgumentException {
        String[] strings;

        // Wait until a valid fraction was entered
        do {
            String str = s.nextLine();

            strings = str.split("/");
        } while(strings.length != 2 || !isInteger(strings[0]) || !isInteger(strings[1]));

        return new Fraction(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
    }
}
