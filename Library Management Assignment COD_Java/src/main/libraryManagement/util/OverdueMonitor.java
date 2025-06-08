package main.libraryManagement.util;

import main.libraryManagement.model.LendingRecord;
import java.util.Date;
import java.util.List;

public class OverdueMonitor implements Runnable {
    private List<LendingRecord> lendingRecords;

    public OverdueMonitor(List<LendingRecord> lendingRecords) {
        this.lendingRecords = lendingRecords;
    }

    @Override
    public void run() {
        while (true) {
            Date now = new Date();
            lendingRecords.stream()
                    .filter(record -> record.getReturnDate() == null)
                    .filter(record -> record.getDueDate().before(now))
                    .forEach(record -> System.out.println(
                            "OVERDUE: Book " + record.getBook().getBookId() +
                                    " borrowed by " + record.getMember().getName() +
                                    " is overdue since " + record.getDueDate()));

            try {
                Thread.sleep(60_000); // Check every minute

//                for test
                Thread.sleep(5000); // Check every 5 sec

            } catch (InterruptedException e) {
                System.out.println("Overdue monitor stopped");
                break;
            }
        }
    }
}