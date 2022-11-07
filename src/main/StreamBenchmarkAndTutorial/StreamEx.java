package StreamBenchmarkAndTutorial;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamEx {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5).stream();

        stream.map(e -> { System.out.println("Calling by Intermediate operation: " + e); return e;})
              .forEach(e -> System.out.println("Calling by Terminal operation: " + e));
    }
}
