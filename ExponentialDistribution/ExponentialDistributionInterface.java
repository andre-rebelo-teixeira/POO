package ExponentialDistribution;



/**
 * ExponentialDistributionInterface.java
 * Interface for the ExponentialDistribution class
 * 
 * Created on 16/06/2023
 */
public inteface ExponentialDistributionInterface {
    
    /*
        * Get the lambda of the distribution 
        *
        * @return The lambda of the distribution
        */
    public float getLambda();
    
    /*
        * Set the lambda of the distribution
        *
        * @param lambda The lambda of the distribution
        */
    public void setLambda(float lambda);
    
    /*
        * Get the mean of the distribution
        *
        * @return The mean of the distribution
        */
    public float getMean();

    /*
        * Set the mean of the distribution
        *
        * @param mean The mean of the distribution
        */ 
    public void setMean(float mean);

    /*
        * Get the variance of the distribution
        *
        * @return The variance of the distribution
        */

    public float getVariance();

    /*
        * Get the probability density function of the distribution
        *
        * @param x The value at which the pdf is to be calculated
        * @return The pdf of the distribution at x
        */    
    public float getPdf(flaot x);
    
    /*
        * Get the cumulative distribution function of the distribution
        *
        * @param x The value at which the cdf is to be calculated
        * @return The cdf of the distribution at x
        */
    public float getCdf(float x);

    /*
        * Get the quantile of the distribution
        *
        * @param p The probability at which the quantile is to be calculated
        * @return The quantile of the distribution at p
        */ 
    public float getQuantile(float p);
    
    /*
        * Get the exponential value of the distribution
        *
        * @param x The value at which the exponential value is to be calculated
        * @return The exponential value of the distribution at x
        */
    public float getExponentialVal(float x);

    /*
        * Get the exponential random value of the distribution
        *
        * @return The exponential random value of the distribution
        */
    public float getExponentialRandom();
}
