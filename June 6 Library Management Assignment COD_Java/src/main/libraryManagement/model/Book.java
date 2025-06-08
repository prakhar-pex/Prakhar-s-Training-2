package main.libraryManagement.model;

public class Book implements Comparable<Book>{
    private String bookId;
    private String title;
    private String author;
    private BookStatus status;

//    public Book(BookStatus status, String author, String title, String bookId) {
//        this.status = BookStatus.AVAILABLE;
//        this.author = author;
//        this.title = title;
//        this.bookId = bookId;
//    }

    public Book(String bookId, String title, String author, BookStatus status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.status = status;
    }


    //Getters and 1 setter .We are not creating setter for all the member variables,
    // bcuz the constructor itself sets the value when the object is created. like when you create a object as:
    //Book book = new Book("B101", "Java Basics", "James Gosling");, the constructor sets these fields.
    //so There is NO NEED to use setters for those fields unless you want to modify them later
    // after the object is created.

    //For eg. we want to change the status even after object creation, so just for status, we
    //will use setter only for setStatus.
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book [ID: " + bookId + ", Title: " + title +
                ", Author: " + author + ", Status: " + status + "]";
    }
    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
}
