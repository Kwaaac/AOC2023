package fr.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Report {

    private final List<Integer> report;

    public Report(List<Integer> reports) {
        this.report = List.copyOf(reports);
    }

    public static Report fromLine(String line) {
        var report = Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).boxed().toList();
        return new Report(report);
    }

    public int extrapolate() {
        var reports = new ArrayList<List<Integer>>();
        reports.addFirst(report);


        for (; ; ) {
            var pouet = reports.getFirst();
            // done
            if (pouet.stream().allMatch(i -> i == 0)) {
                int p = 0;
                for (var r : reports) {
                    p = r.getFirst() - p;
                    System.out.println("p = " + p);
                }

                System.out.println("final p = " + p);
                return p;
            }


            var newReport = IntStream.range(0, pouet.size() - 1)
                    .map(i -> pouet.get(i + 1) - pouet.get(i))
                    .boxed().toList();
            System.out.println("newReport = " + newReport);
            reports.addFirst(newReport);
        }
    }
}
