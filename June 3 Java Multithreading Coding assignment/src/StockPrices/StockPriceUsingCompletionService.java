package StockPrices;
import java.io.*;
        import java.util.*;
        import java.util.concurrent.*;

public class StockPriceUsingCompletionService {

    // Reads two lines (starting from a given line index) using BufferedReader
    private static List<String> readLinesFromFile(String filePath, int startLine, int linesToRead) {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            reader.readLine();

            int currentLine = 0;
            String line;

            while ((line = reader.readLine()) != null) {
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
        CompletionService<List<String>> completionService = new ExecutorCompletionService<>(executor);

        // Submit 4 reading tasks (each reads 2 lines)
        for (int i = 0; i < 4; i++) {
            int startLine = i * 2;
            completionService.submit(() -> readLinesFromFile(filePath, startLine, 2));
        }

        // Collect results from all 4 tasks
        List<String> allLines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Future<List<String>> future = completionService.take(); // waits for next completed task
            allLines.addAll(future.get());
        }

        // Final output: parse and print symbol and price
        for (String line : allLines) {
            String[] parts = line.split(",");
            System.out.printf("%-6s : $%.2f%n", parts[0], Double.parseDouble(parts[1]));
        }

        executor.shutdown();
    }
}
