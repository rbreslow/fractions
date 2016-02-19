package me.rockybreslow.fractions;

/**
 * Implementation of a mathematical Fraction.
 *
 * @author Rocky Breslow
 * @version 1.0
 */

public class Fraction implements Comparable<Fraction> {
    /** Fraction numerator */
    private int numerator;
    /** Fraction denominator */
    private int denominator;

    /**
     * Constructs a fraction.
     */
    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    /**
     * Constructs a fraction.
     *
     * @param numerator fraction numerator
     * @param denominator fraction denominator
     * 
     * @exception IllegalArgumentException if denominator is equal to zero.
     */
    public Fraction(int numerator, int denominator) {
        if(denominator == 0) {
            throw new IllegalArgumentException("Illegal fraction. 'denominator' cannot be 0.");
        }

        this.numerator = numerator;
        this.denominator = denominator;

        simplify();
    }

    /**
     * Constructs a fraction.
     *
     * @param fraction the fraction to copy
     */
    public Fraction(Fraction fraction) {
        this.numerator = fraction.getNumerator();
        this.denominator = fraction.getDenominator();
    }

    /**
     * Gets the numerator.
     *
     * @return the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Gets the denominator.
     *
     * @return the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Gets the decimal value.
     *
     * @return the decimal value
     */
    public float getValue() {
        return ((float) numerator) / ((float) denominator);
    }

    /**
     * Sets the numerator and simplifies.
     *
     * @param numerator the numerator
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;

        simplify();
    }

    /**
     * Sets the denominator and simplifies.
     *
     * @param denominator the denominator
     * @exception IllegalArgumentException if denominator is equal to zero.
     */
    public void setDenominator(int denominator) {
        if(denominator == 0) {
            throw new IllegalArgumentException("Illegal fraction. 'denominator' cannot be 0.");
        }

        this.denominator = denominator;

        simplify();
    }

    /**
     * Simplifies the fraction.
     */
    private void simplify() {
        // Find the greatest common divisor between the numerator and denominator
        int greatestCommonDivisor = getGreatestCommonDivisor(numerator, denominator);

        // If it is valid then divide the numerator and denominator by it
        if (greatestCommonDivisor > 0) {
            numerator /= greatestCommonDivisor;
            denominator /= greatestCommonDivisor;
        }
    }

    /**
     * Adds a fraction to the existing fraction.
     *
     * @param fraction the fraction to add
     */
    public void add(Fraction fraction) {
        // Find the common denominator between this fraction and the provided fraction
        int commonDenominator = getLowestCommonMultiple(denominator, fraction.getDenominator());

        // Find what each denominator was multiplied by to get the common denominator
        int thisMultiple = commonDenominator / denominator;
        int fractionMultiple = commonDenominator / fraction.getDenominator();

        // Calculate the numerator
        numerator = (numerator * thisMultiple) + (fraction.getNumerator() * fractionMultiple);

        // The new denominator is the common one
        denominator = commonDenominator;

        // Simplify the fraction
        simplify();
    }

    /**
     * Subtracts a fraction to the existing fraction.
     *
     * @param fraction the fraction to subtract
     */
    public void subtract(Fraction fraction) {
        // Add the provided fraction but with a negative numerator
        // This has the same effect as subtracting the fraction
        add(new Fraction(-fraction.getNumerator(), fraction.getDenominator()));
    }

    /**
     * Multiplies a fraction by the existing fraction.
     *
     * @param fraction the fraction to multiply
     */
    public void multiply(Fraction fraction) {
        // Multiply by the fraction
        numerator *= fraction.getNumerator();
        denominator *= fraction.getDenominator();

        // Simplify the fraction
        simplify();
    }

    /**
     * Divides a fraction by the existing fraction.
     *
     * @param fraction the fraction to divide
     */
    public void divide(Fraction fraction) {
        // Flip the fraction and multiply by it
        // This has the same effect as multiplying by the fraction
        multiply(new Fraction(fraction.getDenominator(), fraction.getNumerator()));
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Fraction fraction) {
        // Get the float values of this fraction and the provided fraction
        float thisValue = getValue();
        float fractionValue = fraction.getValue();

        // If this fraction is greater than the provided one, return 1
        if (thisValue > fractionValue) {
            return 1;
        } else if (thisValue < fractionValue) {
            // If it is less than, return -1
            return -1;
        } else {
            // Otherwise they are equal, return 0
            return 0;
        }
    }

    /**
     * This gets a formatted version of the fraction value contained by the object.
     *
     * @return formatted fraction, e.g. 1/2
     */
    @Override
    public String toString() {
        // Return the fraction in the format "numerator/denominator"
        return String.format("%d/%d", numerator, denominator);
    }

    /**
     * Finds the greatest common divisor between two numbers.
     *
     * @param first the first number
     * @param second the second number
     * @return the greatest common divisor
     */
    private static int getGreatestCommonDivisor(int first, int second) {
        if (first == 0 || second == 0) {
            return first + second;
        }

        return getGreatestCommonDivisor(second, first % second);
    }

    /**
     * Finds the lowest common multiple between two numbers.
     *
     * @param first the first number
     * @param second the second number
     * @return the lowest common multiple
     */
    private static int getLowestCommonMultiple(int first, int second) {
        return Math.abs(first * second) / getGreatestCommonDivisor(first, second);
    }
}
