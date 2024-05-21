package ExponentialDistribution;



import java.util.Random;   

/*
    * ExponentialDistribution.java
    * Simple class to model an exponential distribution
    * 
    * Created on 16/06/2023
*/
public class ExponentialDistribution implements ExponentialDistributionInterface{
    private float lambda = 0.0f;

    /**
     * Constructor for the ExponentialDistribution class
     * 
     * @param lambda The rate parameter of the exponential distribution
     */
    public ExponentialDistribution(float lambda){
        this.lambda = lambda;
    }

    /*
        * Get the lambda of the distribution 
        *
        * @return The lambda of the distribution
        */
    public float getLambda(){
        return lambda;
    }

    /*
        * Set the lambda of the distribution
        *
        * @param lambda The lambda of the distribution
        */
    public void setLambda(float lambda){
        this.lambda = lambda;
    }

    /*
        * Get the mean of the distribution
        *
        * @return The mean of the distribution
        */
    public float getMean(){
        return 1/lambda;
    }

    /*
        * Set the mean of the distribution
        *
        * @param mean The mean of the distribution
        */
    public void setMean(float mean){
        this.lambda = 1/mean;
    }

    /*
        * Get the variance of the distribution
        *
        * @return The variance of the distribution
        */
    public float getVariance(){
        return 1/(lambda*lambda);
    }

    /*
        * Set the variance of the distribution
        *
        * @param variance The variance of the distribution
        */
    public float getPDF(float x){
        return lambda*(float)Math.exp(-lambda*x);
    }

    /* 
        * Get the cumulative distribution function of the distribution
        *
        * @param x The value to evaluate the CDF at
        * @return The value of the CDF at x
        */
    public float getCDF(float x){
        return 1-(float)Math.exp(-lambda*x);
    }

    /*
        * Get the quantile of the distribution
        *
        * @param p The probability to get the quantile for
        * @return The quantile for the given probability
        */
    public float getQuantile(float p){
        return (float)Math.log(1-p)/(-lambda);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public float getExponentialVal(float x) {
        if (x < 0) {
            return 0;
        }

        return (float) this.lambda * Math.exp(-this.lambda • x);
    }

    /*
        * Get a random value from the distribution
        *
        * @return A random value from the distribution
        */
    public float getExponentialRandom() {
        Random rand = new Random();

        return this.getExponentialVal(rand.nextFloat());
    }
}
