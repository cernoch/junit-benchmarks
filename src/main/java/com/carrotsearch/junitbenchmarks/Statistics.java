package com.carrotsearch.junitbenchmarks;

import java.util.List;

/**
 * Calculate simple statistics from multiple {@link SingleResult}s.
 */
final class Statistics
{
    public Measures gc;
    public Measures evaluation;
    public Measures blocked;

    public static Statistics from(List<SingleResult> results)
    {
        final Statistics stats = new Statistics();
        long [] times = new long [results.size()];

        // GC-times.
        for (int i = 0; i < times.length; i++)
            times[i] = results.get(i).gcTime();
        stats.gc = Measures.from(times);

        // Evaluation-only times.
        for (int i = 0; i < times.length; i++)
            times[i] = results.get(i).evaluationTime();
        stats.evaluation = Measures.from(times);

        // Thread blocked times.
        for (int i = 0; i < times.length; i++)
            times[i] = results.get(i).blockTime;
        stats.blocked = Measures.from(times);


        return stats;
    }
}