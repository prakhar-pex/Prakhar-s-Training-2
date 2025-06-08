package main.libraryManagement.model;


public enum BookStatus {
    AVAILABLE,  // Book is ready to be borrowed
    ISSUED,     // Book is currently borrowed
    DAMAGED,    // Book cannot be issued
    RESERVED    // (Optional) Book is on hold for a member
}
