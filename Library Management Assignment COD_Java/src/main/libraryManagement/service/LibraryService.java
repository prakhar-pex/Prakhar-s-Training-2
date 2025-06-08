package main.libraryManagement.service;

import main.libraryManagement.exception.BookNotAvailableException;
import main.libraryManagement.exception.EntityNotFoundException;
import main.libraryManagement.exception.OverdueBookException;
import main.libraryManagement.model.Book;
import main.libraryManagement.model.BookStatus;
import main.libraryManagement.model.LendingRecord;
import main.libraryManagement.model.Member;
import main.libraryManagement.repository.Repository;
import main.libraryManagement.util.OverdueMonitor;

import java.util.*;

public class LibraryService {
    private Repository<Book> bookRepository = new Repository<>();
    private Repository<Member> memberRepository = new Repository<>();
    private List<LendingRecord> lendingRecords = new ArrayList<>();

    //add a new book (object)
    public void addBook(Book book){
    //bookRepository will store in map, which takes id, and the bookObject.
        //add is the method of repository class
        bookRepository.add(book.getBookId(), book);
    }

    // Add a new member
    public void addMember(Member member) {
        memberRepository.add(member.getMemberId(), member);
    }


    // Issue a book to a member
    public void issueBook(String bookId, String memberId)
            throws EntityNotFoundException, BookNotAvailableException, OverdueBookException {

        // 1. Get book and member (throws EntityNotFoundException)
        Book book = bookRepository.get(bookId, "Book");
        Member member = memberRepository.get(memberId, "Member");

        // 2. Check if book is available
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookNotAvailableException(bookId, "status: " + book.getStatus());
        }

        // 3. Check for overdue books (optional)
        if (hasOverdueBooks(memberId)) {
            throw new OverdueBookException(memberId);
        }

        // 4. Create lending record
        Date issueDate = new Date();

//        Date dueDate = calculateDueDate(issueDate, 14); // 14-day loan period

        //for testing
        Date dueDate = new Date(issueDate.getTime() + 10 * 1000);


        //did not keep in repository, bcuz no cruds are to be done on lendingRecords
        LendingRecord record = new LendingRecord(
                UUID.randomUUID().toString(), // Unique lending record ID
                book,
                member,
                issueDate,
                dueDate
        );

        // 5. Update system state
        book.setStatus(BookStatus.ISSUED);
        lendingRecords.add(record);
    }

    //When a user RETURN a book
    public void returnBook(String bookId, boolean isDamaged)
            throws EntityNotFoundException {

        // 1. Find the active lending record
        LendingRecord record = findActiveRecord(bookId);

        // 2. Update book status
        Book book = record.getBook();
        book.setStatus(isDamaged ? BookStatus.DAMAGED : BookStatus.AVAILABLE);

        // 3. Mark as returned
        record.setReturnDate(new Date());
    }


    // Helper: Find active lending record for a book
    private LendingRecord findActiveRecord(String bookId)
            throws EntityNotFoundException {

        return lendingRecords.stream()
                .filter(record -> record.getBook().getBookId().equals(bookId))
                .filter(record -> record.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Active lending record", bookId));
    }

    // Helper: Check for overdue books (for OverdueBookException)
    private boolean hasOverdueBooks(String memberId) {
        Date today = new Date();
        return lendingRecords.stream()
                .filter(record -> record.getMember().getMemberId().equals(memberId))
                .filter(record -> record.getReturnDate() == null)
                .anyMatch(record -> record.getDueDate().before(today));
    }


    public List<Book> findBooksByTitle(String keyword) {
        return bookRepository.getAll().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.getAll().stream()
                .filter(member -> member.getName().contains(name))
                .toList();
    }


    public List<Book> getBooksSortedByTitle() {
        return bookRepository.getAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }

    public void startOverdueMonitor() {
        new Thread(new OverdueMonitor(lendingRecords)).start();
    }


    public List<Member> getMembersWithActiveBorrowings() {
        return lendingRecords.stream()
                .filter(record -> record.getReturnDate() == null)
                .map(LendingRecord::getMember)
                .distinct()
                .toList();
    }
}
