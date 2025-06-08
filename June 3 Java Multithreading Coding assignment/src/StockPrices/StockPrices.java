package StockPrices;

import java.util.*;
import java.util.concurrent.*;

public class StockPrices {


//     Reads stock data from a CSV file and returns it as a Map.
//     Format: "Symbol,Price" (e.g., "AAPL,182.51")

    private static Map<String, Double> readStockData(String filePath) throws Exception {
        Map<String, Double> stockData = new HashMap<>();
        try (Scanner scanner = new Scanner(new java.io.File(filePath))) {
            scanner.nextLine(); // Skip header (Symbol,Price)
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                stockData.put(parts[0], Double.parseDouble(parts[1]));
            }
        }
        return stockData;
    }


//     Splits a list into smaller sublists for parallel processing.
//     Example: partition([A,B,C,D], 2) → [[A,B], [C,D]]

    private static <T> List<List<T>> partition(List<T> list, int numPartitions) {
        List<List<T>> partitions = new ArrayList<>();
        int size = list.size();
        int chunkSize = (int) Math.ceil((double) size / numPartitions);

        for (int i = 0; i < size; i += chunkSize) {
            int end = Math.min(i + chunkSize, size);
            partitions.add(list.subList(i, end));
        }
        return partitions;
    }

//    Fetches prices for a subset of symbols (used by Callable tasks).
    private static Map<String, Double> fetchPrices(
            Map<String, Double> stockData, List<String> symbols) {
        Map<String, Double> result = new HashMap<>();
        for (String symbol : symbols) {
            Double price = stockData.get(symbol);
            if (price != null) {
                result.put(symbol, price);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            //  Load stock data from CSV
            Map<String, Double> stockData = readStockData("Stocks.csv");

            //Define symbols to fetch (can be user-provided)
            List<String> symbolsToFetch = Arrays.asList(
                    "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "META", "NFLX", "NVDA");

            //  Split symbols into chunks for parallel processing
            int numThreads = 4; // Optimal: Runtime.getRuntime().availableProcessors()
            List<List<String>> chunks = partition(symbolsToFetch, numThreads);

            // Create thread pool
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            List<Future<Map<String, Double>>> futures = new ArrayList<>();

            //  Submit tasks to fetch prices in parallel
            for (List<String> chunk : chunks) {
                Callable<Map<String, Double>> task = () -> fetchPrices(stockData, chunk);
                futures.add(executor.submit(task));
            }

            // Combine results from all threads
            Map<String, Double> finalResult = new HashMap<>();
            for (Future<Map<String, Double>> future : futures) {
                finalResult.putAll(future.get()); // Blocks until result is available
            }

            //  Shutdown executor (important!)
            executor.shutdown();

            //  Print results
            System.out.println("Fetched Stock Prices:");
            finalResult.forEach((symbol, price) ->
                    System.out.printf("%-6s : $%.2f%n", symbol, price));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}