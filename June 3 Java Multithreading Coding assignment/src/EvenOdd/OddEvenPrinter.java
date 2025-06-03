package EvenOdd;

public class OddEvenPrinter {
    private final int max;
    private int number = 1;
    private boolean isOddTurn = true;

    public OddEvenPrinter(int max) {
        this.max = max;
    }

    public synchronized void printOdd() {
        while (number <= max) {
            while (!isOddTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (number <= max) {
                System.out.println("Odd: " + number);
                number++;
                isOddTurn = false;
                notify();
            }
        }
    }

    public synchronized void printEven() {
        while (number <= max) {
            while (isOddTurn) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // or leave empty
                }
            }
            if (number <= max) {
                System.out.println("Even: " + number);
                number++;
                isOddTurn = true;
                notify();
            }
        }
    }

    public static void main(String[] args) {
        OddEvenPrinter printer = new OddEvenPrinter(10);


        //using runnable so no need to create seperate EvenPrint and OddPrint classes.
        //using runnable, so dont need those

        Runnable oddTask = () -> printer.printOdd();
        Runnable evenTask = () -> printer.printEven();

        Thread oddThread = new Thread(oddTask);
        Thread evenThread = new Thread(evenTask);

        oddThread.start();
        evenThread.start();
    }
}
