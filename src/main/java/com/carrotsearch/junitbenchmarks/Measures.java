package com.carrotsearch.junitbenchmarks;

import java.util.Arrays;
import java.util.Locale;

/**
 * Average with standard deviation.
 */
public final class Measures
{
    /**
     * Average (in milliseconds).
     */
    public final double avg;

    /**
     * Standard deviation (in milliseconds).
     */
    public final double stddev;

    /**
     * Median (in milliseconds).
     */
    public final double median;

    /**
     * Median absolute deviation (in milliseconds).
     */
    public final double mad;

    public Measures(double avg, double stddev, double median, double mad) {
        this.avg = avg;
        this.stddev = stddev;
        this.median = median;
        this.mad = mad;
    }

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "%.2f [+- %.2f]", 
            avg, stddev);
    }

    static Measures from(long [] values)
    {
        double avg = avg(values);
        double stddev = stddev(avg, values);
        
        double[] asLong = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            asLong[i] = values[i];
        }
        double median = median(asLong);
        double mad = mad(median, asLong);
        
        return new Measures(avg, stddev, median, mad);
    }

    static double avg(long [] values) {
        double sum = 0;
        
        for (long l : values) {
            double value = l; // convert to double
            sum += value / 1000.0; // prevent overflow
        }

        return sum / values.length;
    }
    
    static double stddev(double avg, long [] values) {
        double sumDeltaSquares = 0;
        
        for (long l : values) {
            double delta = avg - ((double) l / 1000.0);
            sumDeltaSquares += delta * delta;
        }
        
        return Math.sqrt(sumDeltaSquares / values.length);
    }
    
    
    static double median(double [] values) {
        Arrays.sort(values);
        return middle(values) / 1000.0;
    }
    
    static double mad(double median, double[] values) {
        
        for (int i = 0; i < values.length; i++) {
            values[i] = Math.abs(median - ((double) values[i]) / 1000.0);
        }
        
        Arrays.sort(values);
        return middle(values);
    }
    
    private static double middle(double [] values) {
        
        if (values.length % 2 == 1) {
            return values[values.length / 2];
        }
        
        double midSum = values[values.length / 2]
                      + values[values.length / 2 - 1];        
        return midSum / 2.0;
    }
}