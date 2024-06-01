package ExponentialDistribution;



/**
 * ExponentialDistributionInterface.java
 * Interface for the ExponentialDistribution class
 * 
 * Created on 16/06/2023
 */
public interface ExponentialDistributionInterface {
    

    /*
        * Get the mean of the distribution
        *
        * @return The mean of the distribution
        */
    public double getMean();

    /*
        * Set the mean of the distribution
        *
        * @param mean The mean of the distribution
        */ 
    public void setMean(double mean);


    /*
        * Get the probability density function of the distribution
        *
        * @param x The value at which the pdf is to be calculated
        * @return The pdf of the distribution at x
        */    
    public double getPdf(double x);
    
    /*
        * Get the cumulative distribution function of the distribution
        *
        * @param x The value at which the cdf is to be calculated
        * @return The cdf of the distribution at x
        */
    public double getCdf(double x);

    /*
        * Get the exponential value of the distribution
        *
        * @param x The value at which the exponential value is to be calculated
        * @return The exponential value of the distribution at x
        */
    public double getExponentialVal(double x);

    /*
        * Get the exponential random value of the distribution
        *
        * @return The exponential random value of the distribution
        */
    public double getExponentialRandom();
}
