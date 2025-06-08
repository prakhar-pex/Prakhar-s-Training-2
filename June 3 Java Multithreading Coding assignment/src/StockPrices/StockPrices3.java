package StockPrices;
//package StockPrices;
//import java.io.IOException;
//import java.nio.file.*;
//        import java.util.*;
//        import java.util.concurrent.*;
//        import java.util.stream.*;
//
//public class StockPrices3 {
//
//    public static void main(String[] args) throws Exception {
//        // Read all lines from CSV
//        List<String> lines = Files.readAllLines(Paths.get("Stocks.csv"));
//        lines = lines.subList(1, lines.size()); // Skip header
//
//        // Define number of threads
//        int chunkSize = 2;
//        int threadCount = lines.size() / chunkSize;
//
//        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
//        List<CompletableFuture<Map<String, Double>>> futures = new ArrayList<>();
//
//        for (int i = 0; i < threadCount; i++) {
//            int start = i * chunkSize;
//            int end = start + chunkSize;
//
//            List<String> chunk = lines.subList(start, end);
//
//            // Each thread processes its chunk
//            futures.add(CompletableFuture.supplyAsync(() -> {
//                Map<String, Double> partialResult = new HashMap<>();
//                for (String line : chunk) {
//                    String[] parts = line.split(",");
//                    String symbol = parts[0].trim();
//                    Double price = Double.parseDouble(parts[1].trim());
//                    partialResult.put(symbol, price);
//                }
//                return partialResult;
//            }, executor));
//        }
//
//        // Combine all futures
//        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        CompletableFuture<Map<String, Double>> combined = allDone.thenApply(v -> {
//            Map<String, Double> finalResult = new HashMap<>();
//            for (CompletableFuture<Map<String, Double>> future : futures) {
//                finalResult.putAll(future.join());
//            }
//            return finalResult;
//        });
//
//        // Print final results
//        combined.get().forEach((symbol, price) ->
//                System.out.printf("%-6s : $%.2f%n", symbol, price));
//
//        executor.shutdown();
//    }
//}


import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class StockPrices3 {

    // Reads two lines (starting from a given line index) using RandomAccessFile
    private static List<String> readLinesFromFile(String filePath, int startLine, int linesToRead) {
        List<String> result = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            // Skip the header line
            file.readLine();

            int currentLine = 0;
            String line;

            while ((line = file.readLine()) != null) {
                if (currentLine >= startLine && currentLine < startLine + linesToRead) {
                    result.add(line);
                }
                currentLine++;
                if (currentLine >= startLine + linesToRead) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "Stocks.csv";

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Define tasks for 4 threads (each reads 2 lines)
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int startLine = i * 2;
            futures.add(CompletableFuture.supplyAsync(() ->
                    readLinesFromFile(filePath, startLine, 2), executor));
        }

        // Combine all results into one list
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<String>> combinedResult = allDone.thenApply(v -> {
            List<String> allLines = new ArrayList<>();
            for (CompletableFuture<List<String>> future : futures) {
                allLines.addAll(future.join());
            }
            return allLines;
        });

        // Final output: parse and print symbol and price
        for (String line : combinedResult.get()) {
            String[] parts = line.split(",");
            System.out.printf("%-6s : $%.2f%n", parts[0], Double.parseDouble(parts[1]));
        }

        executor.shutdown();
    }
}
