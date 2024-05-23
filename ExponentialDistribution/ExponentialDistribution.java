package ExponentialDistribution;



import java.util.Random;   

/*
    * ExponentialDistribution.java
    * Simple class to model an exponential distribution
    * 
    * Created on 16/06/2023
*/
public class ExponentialDistribution implements ExponentialDistributionInterface{
    private double lambda = 0.0f;

    /**
     * Constructor for the ExponentialDistribution class
     * 
     * @param lambda The rate parameter of the exponential distribution
     */
    public ExponentialDistribution(double lambda){
        this.lambda = lambda;
    }

    /*
        * Get the lambda of the distribution 
        *
        * @return The lambda of the distribution
        */
    public double getLambda(){
        return lambda;
    }

    /*
        * Set the lambda of the distribution
        *
        * @param lambda The lambda of the distribution
        */
    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    /*
        * Get the mean of the distribution
        *
        * @return The mean of the distribution
        */
    public double getMean(){
        return 1/lambda;
    }

    /*
        * Set the mean of the distribution
        *
        * @param mean The mean of the distribution
        */
    public void setMean(double mean){
        this.lambda = 1/mean;
    }

    /*
        * Get the variance of the distribution
        *
        * @return The variance of the distribution
        */
    public double getVariance(){
        return 1/(lambda*lambda);
    }

    /*
        * Set the variance of the distribution
        *
        * @param variance The variance of the distribution
        */
    public double getPdf(double x){
        return lambda*(double)Math.exp(-lambda*x);
    }

    /* 
        * Get the cumulative distribution function of the distribution
        *
        * @param x The value to evaluate the CDF at
        * @return The value of the CDF at x
        */
    public double getCdf(double x){
        return 1-(double)Math.exp(-lambda*x);
    }

    /*
        * Get the quantile of the distribution
        *
        * @param p The probability to get the quantile for
        * @return The quantile for the given probability
        */
    public double getQuantile(double p){
        return (double)Math.log(1-p)/(-lambda);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public double getExponentialVal(double x) {
        if (x < 0) {
            return 0;
        }

        return (double) this.lambda * Math.exp(-this.lambda * x);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public double getExponentialRandom() {
        Random rand = new Random();

        return this.getExponentialVal(rand.nextFloat());
    }
}
