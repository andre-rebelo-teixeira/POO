package ExponentialDistribution;

import java.util.Random;

/**
 * ExponentialDistribution.java
 *
 * This class models an exponential distribution, providing methods to interact
 * with
 * the distribution such as getting the mean, probability density function
 * (PDF),
 * cumulative distribution function (CDF), and generating random values.
 *
 * Created on 16/06/2023
 *
 * @version 1.0
 * @since 1.0
 * @see java.util.Random
 * @see ExponentialDistributionInterface
 *
 * @author André Rebelo Teixeira
 */
public class ExponentialDistribution implements ExponentialDistributionInterface {
    private double mean = 0.0f;
    private final Random rand = new Random();

    /**
     * Constructor for the ExponentialDistribution class.
     *
     * @param mean The mean parameter of the exponential distribution.
     */
    public ExponentialDistribution(double mean) {
        this.mean = mean;
    }

    /**
     * Gets the mean of the distribution.
     *
     * @return The mean of the distribution.
     */
    public double getMean() {
        return mean;
    }

    /**
     * Sets the mean of the distribution.
     *
     * @param mean The mean of the distribution.
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * Gets the probability density function (PDF) of the distribution at a given
     * value.
     *
     * @param x The value to evaluate the PDF at.
     * @return The value of the PDF at x.
     */
    public double getPdf(double x) {
        return mean * Math.exp(-mean * x);
    }

    /**
     * Gets the cumulative distribution function (CDF) of the distribution at a
     * given value.
     *
     * @param x The value to evaluate the CDF at.
     * @return The value of the CDF at x.
     */
    public double getCdf(double x) {
        return 1 - Math.exp(-x / this.mean);
    }

    /**
     * Gets a random value from the distribution for a given probability.
     *
     * @param x The probability value.
     * @return A random value from the distribution corresponding to the
     *         probability.
     */
    public double getExponentialVal(double x) {
        if (x < 0) {
            return 0;
        } else if (x > 1) {
            return Double.POSITIVE_INFINITY;
        }

        return -this.mean * Math.log(1 - x);
    }

    /**
     * Gets a random value from the distribution.
     *
     * @return A random value from the distribution.
     */
    public double getExponentialRandom() {
        return this.getExponentialVal(this.rand.nextDouble());
    }
}
