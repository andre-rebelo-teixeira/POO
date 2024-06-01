package ExponentialDistribution;



import java.util.Random;   

/*
    * ExponentialDistribution.java
    * Simple class to model an exponential distribution
    * 
    * Created on 16/06/2023
*/
public class ExponentialDistribution implements ExponentialDistributionInterface{
    private double mean = 0.0f;
    private final Random rand = new Random();

    /**
     * Constructor for the ExponentialDistribution class
     *
     * @param mean The mean parameter of the exponential distribution
     */
    public ExponentialDistribution(double mean){
        this.mean = mean;
    }


    /*
        * Get the mean of the distribution
        *
        * @return The mean of the distribution
        */
    public double getMean(){
        return mean;
    }

    /*
        * Set the mean of the distribution
        *
        * @param mean The mean of the distribution
        */
    public void setMean(double mean){
        this.mean = mean;
    }

    /*
        * Set the variance of the distribution
        *
        * @param variance The variance of the distribution
        */
    public double getPdf(double x){
        return mean*Math.exp(-mean*x);
    }

    /* 
        * Get the cumulative distribution function of the distribution
        *
        * @param x The value to evaluate the CDF at
        * @return The value of the CDF at x
        */
    public double getCdf(double x){
        return 1-Math.exp(x/ this.mean);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public double getExponentialVal(double x) {
        if (x < 0) {
            return 0;
        } else if (x > 1) {
            return Double.POSITIVE_INFINITY;
        }

        return -this.mean * Math.log(1 - x);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public double getExponentialRandom() {
        return this.getExponentialVal(this.rand.nextDouble(1.0));
    }
}
