package com.carrotsearch.junitbenchmarks;

import java.io.*;
import java.util.Locale;

/**
 * {@link IResultsConsumer} printing benchmark results to a given writer. 
 */
public final class WriterConsumer implements IResultsConsumer
{
    private final Writer w;

    public WriterConsumer()
    {
        this(getDefaultWriter());
    }

    public WriterConsumer(Writer w)
    {
        this.w = w;
    }

    public void accept(Result result) throws IOException
    {
        w.write(String.format(Locale.ENGLISH,
            "%s.%s:\n",
            result.getShortTestClassName(),
            result.getTestMethodName()));
        
        for (Exception ex : result.failures) {
            ex.printStackTrace(new PrintWriter(w));
        }
        
        w.write(String.format(Locale.ENGLISH,
            "\t[measured %d out of %d rounds, %s]\n" +
            "\t[round: %s, round.block: %s]\n" + 
            "\t[round.gc: %s, GC.calls: %d, GC.time: %.2f]\n" +
            "\t[time.total: %.2f, time.warmup: %.2f, time.bench: %.2f]\n",
            result.benchmarkRounds, 
            result.benchmarkRounds + result.warmupRounds, 
            concurrencyToText(result),
            result.roundAverage.toString(),
            result.blockedAverage.toString(),
            result.gcAverage.toString(), 
            result.gcInfo.accumulatedInvocations(), 
            result.gcInfo.accumulatedTime() / 1000.0,
            (result.warmupTime + result.benchmarkTime) * 0.001,
            result.warmupTime * 0.001, 
            result.benchmarkTime * 0.001
        ));
        
        if (!result.failures.isEmpty()) {
            w.write(String.format(
                "!\t[%d out of %d (%d%%) rounds have failed]\n",
                result.failures.size(),
                result.benchmarkRounds,
                100 * result.failures.size() / result.benchmarkRounds
            ));
        }
        
        w.flush();
    }

    private String concurrencyToText(Result result)
    {
        int threads = result.getThreadCount();
        if (threads == Runtime.getRuntime().availableProcessors())
        {
            return "threads: " + threads +
                " (all cores)";
        }

        if (threads == 1)
        {
            return "threads: 1 (sequential)";
        }

        return "threads: " + result.concurrency
            + " (physical processors: " + Runtime.getRuntime().availableProcessors() + ")";
    }

    /**
     * Return the default writer (console).
     */
    private static Writer getDefaultWriter()
    {
        return new OutputStreamWriter(System.out)
        {
            public void close() throws IOException
            {
                // Don't close the superstream.
            }
        };
    }
}
