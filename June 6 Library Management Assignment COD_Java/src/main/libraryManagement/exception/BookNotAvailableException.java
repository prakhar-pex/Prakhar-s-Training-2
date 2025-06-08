package main.libraryManagement.exception;

public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String bookId, String reason){
        super("Book '" + bookId + "' cannot be issued: " + reason);
    }
}
