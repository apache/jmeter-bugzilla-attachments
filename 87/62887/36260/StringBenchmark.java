package str;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringBenchmark {

    @State(Scope.Benchmark)
    public static class Params {
        public String[] var1 = new String[] {"ABCD", "ABCDEFG", "ABCDEFG", "ABCDEFG", "ABCDEFG", "ABCDEFG", "ABCDEFG"};

        public String[] var2 = new String[] {"ABCD", "DEFGHIJ", "ABCDEFGH", "ABCDEFG", "abcedfgh", "aBCEDFG", "ABCDEFG"};


        @Param({ "0", "1", "2", "3", "4", "5", "6" })
        public int index;
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    public void equalsBenchmark(Params params) {
        params.var1[params.index].equals(params.var2[params.index]);
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
    public void compareToBenchmark(Params params) {
        params.var1[params.index].compareTo(params.var2[params.index]);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }
}
