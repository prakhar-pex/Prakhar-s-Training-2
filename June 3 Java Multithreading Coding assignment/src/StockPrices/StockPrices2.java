package StockPrices;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class StockPrices2 {

    public static void main(String[] args) throws Exception {
        // Step 1: Read CSV into Map
        Map<String, Double> stockData = Files.lines(Paths.get("Stocks.csv"))
                .skip(1)
                .map(line -> line.split(","))
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> Double.parseDouble(parts[1].trim())
                ));

        //Divide symbols into 4 sublists
        List<String> symbols = new ArrayList<>(stockData.keySet());
        List<List<String>> sublists = new ArrayList<>();
        int chunkSize = (int) Math.ceil(symbols.size() / 4.0);
        for (int i = 0; i < symbols.size(); i += chunkSize) {
            sublists.add(symbols.subList(i, Math.min(i + chunkSize, symbols.size())));
        }

        //Create fixed thread pool of 4
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Launch 4 CompletableFutures, each handling one sublist
        List<CompletableFuture<Map<String, Double>>> futures = new ArrayList<>();
        for (List<String> sublist : sublists) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                Map<String, Double> result = new HashMap<>();
                for (String symbol : sublist) {
                    result.put(symbol, stockData.get(symbol));
                }
                return result;
            }, executor));
        }

        // Combine results from all threads
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        CompletableFuture<Map<String, Double>> finalResult = allDone.thenApply(v -> {
            Map<String, Double> combined = new HashMap<>();
            for (CompletableFuture<Map<String, Double>> future : futures) {
                combined.putAll(future.join());
            }
            return combined;
        });

        // Print result
        finalResult.get().forEach((symbol, price) ->
                System.out.printf("%-6s : $%.2f%n", symbol, price));

        executor.shutdown();
    }
}



//package StockPrices;
//
//import java.io.File;
//import java.util.*;
//        import java.util.concurrent.*;
//        import java.util.stream.Collectors;
//
//public class StockPrices2 {
//
//    // Reads stock data from CSV and returns Map<Symbol, Price>
//    private static Map<String, Double> readStockData(String filePath) throws Exception {
//        Map<String, Double> stockData = new HashMap<>();
//        try (Scanner scanner = new Scanner(new File(filePath))) {
//            scanner.nextLine(); // Skip header
//            while (scanner.hasNextLine()) {
//                String[] parts = scanner.nextLine().split(",");
//                stockData.put(parts[0].trim(), Double.parseDouble(parts[1].trim()));
//            }
//        }
//        return stockData;
//    }
//
//    // Splits a list into N sublists
//    private static <T> List<List<T>> partition(List<T> list, int parts) {
//        List<List<T>> result = new ArrayList<>();
//        int chunkSize = (int) Math.ceil((double) list.size() / parts);
//        for (int i = 0; i < list.size(); i += chunkSize) {
//            result.add(list.subList(i, Math.min(i + chunkSize, list.size())));
//        }
//        return result;
//    }
//
//    // Fetch prices for a list of symbols
//    private static Map<String, Double> fetchPrices(Map<String, Double> stockData, List<String> symbols) {
//        Map<String, Double> result = new HashMap<>();
//        for (String symbol : symbols) {
//            Double price = stockData.get(symbol);
//            if (price != null) {
//                result.put(symbol, price);
//            }
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//        try {
//            // Load data from CSV
//            Map<String, Double> stockData = readStockData("Stocks.csv");
//
//            // Only fetch these 8 symbols (match the CSV)
//            List<String> symbolsToFetch = Arrays.asList(
//                    "AAPL", "GOOGL", "MSFT", "AMZN",
//                    "TSLA", "META", "NFLX", "NVDA"
//            );
//
//            // Split symbols into 4 sublists (2 each)
//            List<List<String>> partitions = partition(symbolsToFetch, 4);
//
//            // Create fixed thread pool of 4 threads
//            ExecutorService executor = Executors.newFixedThreadPool(4);
//
//            // Create 4 CompletableFutures for parallel fetching
//            List<CompletableFuture<Map<String, Double>>> futures = partitions.stream()
//                    .map(chunk ->
//                            CompletableFuture.supplyAsync(() -> fetchPrices(stockData, chunk), executor)
//                    )
//                    .collect(Collectors.toList());
//
//            // Combine all futures into one
//            CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//
//            // After all are done, merge all results
//            CompletableFuture<Map<String, Double>> combinedResult = allDone.thenApply(v -> {
//                Map<String, Double> finalResult = new HashMap<>();
//                for (CompletableFuture<Map<String, Double>> future : futures) {
//                    finalResult.putAll(future.join()); // join is safe here because allOf is complete
//                }
//                return finalResult;
//            });
//
//            // Get and print final results
//            Map<String, Double> result = combinedResult.get();
//            System.out.println("Fetched Stock Prices:");
//            result.forEach((symbol, price) ->
//                    System.out.printf("%-6s : $%.2f%n", symbol, price));
//
//            executor.shutdown();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
