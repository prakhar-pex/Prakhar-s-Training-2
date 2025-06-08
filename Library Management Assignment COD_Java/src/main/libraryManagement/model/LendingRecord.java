package main.libraryManagement.model;

import java.util.Date;

public class LendingRecord {
    private String recordId;
    private Book book; //Declaring a variable of class type (Book is a class)
    // which will hold a reference to an object
    //- this is k/a COMPOSITION : A lending record is tied to-
    //One specific book
    //One specific member

    private Member member;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;

    public LendingRecord(String recordId, Book book, Member member, Date issueDate, Date dueDate) {
        this.recordId = recordId;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null; //// Initially not returned
    }


    public String getRecordId() {
        return recordId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "LendingRecord [Book: " + book.getTitle() + ", Member: " + member.getName() +
                ", Due: " + dueDate + "]";
    }
}
