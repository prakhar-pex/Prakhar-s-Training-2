package main.libraryManagement.exception;

public class OverdueBookException extends Exception {
    public OverdueBookException(String memberId) {
        super("Member '" + memberId + "' has overdue books!");
    }
}
