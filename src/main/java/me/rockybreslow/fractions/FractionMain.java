package me.rockybreslow.fractions;

import java.util.ArrayList;
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
        Scanner s = new Scanner(System.in);
        ArrayList<Fraction> fractions = new ArrayList<Fraction>();

        // Add 10 randomly generated fractions to the ArrayList
        for(int i = 0; i < 10; i++) {
            fractions.add(new Fraction((int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1));
        }

        boolean cont = true;

        do {
            System.out.print("Enter fraction (e.g. 1/2). Enter an illegal fraction (e.g. 0/0) to finish. ");

            // Continue program if fraction is illegal
            try {
                fractions.add(getFraction(s));
            } catch(IllegalArgumentException e) {
                cont = false;
            }
        } while(cont);

        for(int i = 0; i < fractions.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + fractions.get(i));
        }

        System.out.print("\nEnter two fraction IDs to manipulate: ");

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
                "[1] Add" + "\n" +
                "[2] Subtract" + "\n" +
                "[3] Multiply" + "\n" +
                "[4] Divide" + "\n" +
                "[5] Compare" + "\n" +
                "\n" +
                "Enter an operation ID to perform: "
        );

        // We need to copy fractionA so we can print our results
        Fraction fractionC = new Fraction(fractionA);

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
