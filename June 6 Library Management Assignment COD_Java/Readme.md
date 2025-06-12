```
![Sequence Chart LMS.png](Sequence%20Chart%20LMS.png)


# **Library Management System - README**

## **1. Setup Instructions**

### **Prerequisites**
- Java JDK 17 or later
- Maven (for dependency management)
- Git (optional)

### **Installation**
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   cd library-management-system


2. Compile and run:

   ```bash
   javac -d out src/main/java/main/libraryManagement/*.java
   java -cp out main.libraryManagement.Main
   ```

### **IDE Setup**

1. Import as Maven project in IntelliJ/Eclipse
2. Run `Main.java` as Java application

## **2. Features Implemented**

### **Core Features**

| Feature           | Description                                              |
| ----------------- | -------------------------------------------------------- |
| Book Management   | Add, remove, and track books (ID, title, author, status) |
| Member Management | Register and manage library members                      |
| Book Lending      | Issue books to members with due dates                    |
| Book Returns      | Return books (mark as available or damaged)              |
| Overdue Tracking  | Background thread monitors overdue books                 |
| Search System     | Find books by title, ID, or author                       |

### **Advanced Features**

* Generic `Repository<T>` pattern
* Custom exception handling
* Multithreaded overdue monitoring
* Sorting and searching capabilities

## **3. Java Concepts Utilized**

| Java Concept           | Implementation Example                                 |
| ---------------------- | ------------------------------------------------------ |
| **OOP**                | Book, Member, LendingRecord classes                    |
| **Generics**           | `Repository<T>` class                                  |
| **Collections**        | `HashMap` for storage, `ArrayList` for lending records |
| **Exception Handling** | Custom exceptions like `BookNotAvailableException`     |
| **Multithreading**     | `OverdueMonitor` background thread                     |
| **Streams/Lambdas**    | Search, filter, and sort operations                    |
| **Date/Time**          | Due date calculation with `Calendar`                   |
| **Encapsulation**      | Private fields with getters/setters                    |

## **4. Sample Console Output**

### **System Initialization**

```
Sample data initialized successfully!
OVERDUE: Book B001 borrowed by Alice is overdue since Tue Jan 10 00:00:00 IST 2023

==== Library Management System ====
1. Add Book
2. Add Member
3. Issue Book
4. Return Book
5. Search Books
6. List All Books
7. Exit
Choose option:
```

### **Book Issuance**

```
Choose option: 3
Enter Book ID: B002
Enter Member ID: M001
Book issued successfully! Due in 14 days.
```

### **Book Search**

```
Choose option: 5
Enter search keyword (title/ID/author): gat
Search results (1):
ID: B001 | Title: The Great Gatsby | Author: F. Scott Fitzgerald | Status: ISSUED
```

### **Overdue Alert (Automatic)**

```
[OVERDUE MONITOR] Alert: Book B002 (To Kill a Mockingbird)
borrowed by Alice Johnson is overdue!
Due date: Tue Jan 24 00:00:00 IST 2023
```

### **Book Return**

```
Choose option: 4
Enter Book ID to return: B001
Is the book damaged? (y/n): n
Book returned successfully!
```

## **5. Class Diagram (UML)**

```
+----------------+       +----------------+       +------------------+
|     Book       |       |     Member     |       |  LendingRecord   |
+----------------+       +----------------+       +------------------+
| - bookId: String |     | - memberId: String|    | - recordId: String |
| - title: String  |     | - name: String    |    | - book: Book       |
| - author: String |     | - email: String   |    | - member: Member   |
| - status: Status |     +----------------+       | - issueDate: Date  |
+----------------+                               | - dueDate: Date    |
                                                | - returnDate: Date |
                                                +------------------+
                                                         ^
                                                         |
+----------------+       +----------------+       +------------------+
|    Repository  |       | LibraryService |       |  OverdueMonitor  |
+----------------+       +----------------+       +------------------+
| - storage: Map |       | - bookRepo     |       | - lendingRecords |
+----------------+       | - memberRepo   |       +------------------+
| + add()        |       | - lendingRecords|      | + run()          |
| + get()        |       +----------------+       +------------------+
| + remove()     |       | + issueBook()  |
| + exists()     |       | + returnBook() |
+----------------+       | + searchBooks()|
                        +----------------+
