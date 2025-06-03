package StockPrices;
import java.util.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StockPriceFetcher {

    // Main class to store stock data from CSV
    private static class StockData {
        private final Map<String, Double> symbolToPrice = new HashMap<>();

        public void loadFromCsv(String filePath) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean headerSkipped = false;

                while ((line = br.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        symbolToPrice.put(parts[0], Double.parseDouble(parts[1]));
                    }
                }
            }
        }

        public Double getPrice(String symbol) {
            return symbolToPrice.get(symbol);
        }
    }

    // Task for fetching prices for a subset of symbols
    private static class PriceFetchTask implements Callable<Map<String, Double>> {
        private final StockData stockData;
        private final List<String> symbols;

        public PriceFetchTask(StockData stockData, List<String> symbols) {
            this.stockData = stockData;
            this.symbols = symbols;
        }

        @Override
        public Map<String, Double> call() {
            Map<String, Double> result = new HashMap<>();
            for (String symbol : symbols) {
                Double price = stockData.getPrice(symbol);
                if (price != null) {
                    result.put(symbol, price);
                }
            }
            return result;
        }
    }

    // Main method to fetch prices using multiple threads
    public static Map<String, Double> fetchStockPrices(String csvFilePath, List<String> symbols, int threadCount)
            throws Exception {

        // Load stock data from CSV
        StockData stockData = new StockData();
        stockData.loadFromCsv(csvFilePath);

        // Divide symbols into sublists for parallel processing
        List<List<String>> symbolChunks = partitionList(symbols, threadCount);

        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Map<String, Double>>> futures = new ArrayList<>();

        // Submit tasks to thread pool
        for (List<String> chunk : symbolChunks) {
            futures.add(executor.submit(new PriceFetchTask(stockData, chunk)));
        }

        // Combine results from all threads
        Map<String, Double> result = new HashMap<>();
        for (Future<Map<String, Double>> future : futures) {
            result.putAll(future.get());
        }

        // Shutdown thread pool
        executor.shutdown();

        return result;
    }

    // Helper method to divide list into roughly equal parts
    private static <T> List<List<T>> partitionList(List<T> list, int partitions) {
        List<List<T>> result = new ArrayList<>();
        int size = list.size();
        int chunkSize = (int) Math.ceil((double) size / partitions);

        for (int i = 0; i < size; i += chunkSize) {
            int end = Math.min(i + chunkSize, size);
            result.add(list.subList(i, end));
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            // Example usage
            String csvFile = "Stocks.csv";
            List<String> symbolsToFetch = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "META", "NFLX", "NVDA");
            int threadCount = 4;

            Map<String, Double> prices = fetchStockPrices(csvFile, symbolsToFetch, threadCount);

            // Print results
            System.out.println("Fetched stock prices:");
            prices.forEach((symbol, price) ->
                    System.out.printf("%-6s : $%.2f%n", symbol, price));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}