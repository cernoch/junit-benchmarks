package com.carrotsearch.junitbenchmarks;

/**
 * A result of a single test.
 */
class SingleResult
{
    public final long startTime;
    public final long afterGC;
    public final long endTime;
    public final long blockTime;
    public final Exception thrown;

    public SingleResult(long startTime, long afterGC, long endTime, long blockTime)
    {
        this(startTime, afterGC, endTime, blockTime, null);
    }

    public SingleResult(long startTime, long afterGC, long endTime, long blockTime, Exception thrown)
    {
        this.startTime = startTime;
        this.afterGC = afterGC;
        this.endTime = endTime;
        this.blockTime = blockTime;
        this.thrown = thrown;
    }

    public long gcTime()
    {
        return afterGC - startTime;
    }

    public long evaluationTime()
    {
        return endTime - afterGC;
    }
}