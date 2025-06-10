package main.libraryManagement;

import main.libraryManagement.model.Book;
import main.libraryManagement.model.BookStatus;
import main.libraryManagement.model.Member;
import main.libraryManagement.service.LibraryService;
import main.libraryManagement.exception.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibraryService library = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize with sample data
        initializeSampleData();

        // Start background monitor
        library.startOverdueMonitor();

        // Main menu
        while (true) {
            System.out.println("\n==== Library Management System ====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. List All Books");
            System.out.println("7. List Borrowing Members");
            System.out.println("8. Exit");
            System.out.println("9. Remove Book");
            System.out.println("10. Remove Member");

            System.out.println("11. Exit Process");

            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> listAllBooks();
                case 7 -> listBorrowingMembers();
                case 9 -> removeBook();
                case 10 -> removeMember();

                case 11 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void initializeSampleData() {
        // Sample books
        library.addBook(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", BookStatus.AVAILABLE));
        library.addBook(new Book("B002", "To Kill a Mockingbird", "Harper Lee", BookStatus.AVAILABLE));
        library.addBook(new Book("B002", "To Kill a Mockingbird", "Harper Lee", BookStatus.AVAILABLE));

        // Sample members
        library.addMember(new Member("M001", "Alice Johnson", "alice@email.com"));
        library.addMember(new Member("M002", "Bob Smith", "bob@email.com"));

        System.out.println("Sample data initialized successfully!");
    }

    private static void addBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

//        library.addBook(new Book(id, title, author));
        library.addBook(new Book(id, title, author, BookStatus.AVAILABLE));
        System.out.println("Book added successfully!");
    }

    private static void addMember() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        library.addMember(new Member(id, name, email));
        System.out.println("Member added successfully!");
    }

    private static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String bookId = scanner.nextLine();
        library.removeBook(bookId);
    }

    private static void removeMember() {
        System.out.print("Enter Member ID to remove: ");
        String memberId = scanner.nextLine();
        library.removeMember(memberId);
    }

    private static void issueBook() {
        try {
            System.out.print("Enter Book ID: ");
            String bookId = scanner.nextLine();
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine();

            library.issueBook(bookId, memberId);
            System.out.println("Book issued successfully! Due in 14 days.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (BookNotAvailableException e) {
            System.out.println("Cannot issue book: " + e.getMessage());
        } catch (OverdueBookException e) {
            System.out.println("Cannot issue: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter Book ID to return: ");
            String bookId = scanner.nextLine();
            System.out.print("Is the book damaged? (y/n): ");
            boolean damaged = scanner.nextLine().equalsIgnoreCase("y");

            library.returnBook(bookId, damaged);
            System.out.println("Book returned successfully!");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();

        List<Book> results = library.findBooksByTitle(keyword);
        if (results.isEmpty()) {
            System.out.println("No books found matching '" + keyword + "'");
        } else {
            System.out.println("Search results:");
            results.forEach(System.out::println);
        }
    }

    private static void listAllBooks() {
        List<Book> books = library.getBooksSortedByTitle();
        if (books.isEmpty()) {
            System.out.println("No books in library");
        } else {
            System.out.println("All Books (Sorted by Title):");
            books.forEach(System.out::println);
        }
    }

    private static void listBorrowingMembers() {
        List<Member> borrowers = library.getMembersWithActiveBorrowings();
        if (borrowers.isEmpty()) {
            System.out.println("No members are currently borrowing books.");
        } else {
            System.out.println("Members currently borrowing books:");
            borrowers.forEach(System.out::println);
        }
    }
}