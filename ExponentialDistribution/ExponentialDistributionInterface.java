package ExponentialDistribution;

/**
 * ExponentialDistributionInterface.java
 *
 * This interface defines the methods for an exponential distribution, including
 * methods to get and set the mean, calculate the probability density function
 * (PDF),
 * cumulative distribution function (CDF), and generate random values.
 *
 * Created on 16/06/2023
 *
 * @version 1.0
 * @since 1.0
 * @see ExponentialDistribution
 *
 * @author André Teixeira
 */
public interface ExponentialDistributionInterface {

    /**
     * Gets the mean of the distribution.
     *
     * @return The mean of the distribution.
     */
    double getMean();

    /**
     * Sets the mean of the distribution.
     *
     * @param mean The mean of the distribution.
     */
    void setMean(double mean);

    /**
     * Gets the probability density function (PDF) of the distribution at a given
     * value.
     *
     * @param x The value at which the PDF is to be calculated.
     * @return The PDF of the distribution at x.
     */
    double getPdf(double x);

    /**
     * Gets the cumulative distribution function (CDF) of the distribution at a
     * given value.
     *
     * @param x The value at which the CDF is to be calculated.
     * @return The CDF of the distribution at x.
     */
    double getCdf(double x);

    /**
     * Gets the exponential value of the distribution for a given probability.
     *
     * @param x The probability value.
     * @return The exponential value of the distribution corresponding to the
     *         probability.
     */
    double getExponentialVal(double x);

    /**
     * Gets a random value from the distribution.
     *
     * @return A random value from the distribution.
     */
    double getExponentialRandom();
}
