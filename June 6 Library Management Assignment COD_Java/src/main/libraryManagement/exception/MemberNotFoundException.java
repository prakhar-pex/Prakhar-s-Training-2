package main.libraryManagement.exception;

//we have made these exceptions public bcuz we want the exceptions to be thrown and caught
// from other packages like service, repository, etc (Can be used from anywhere in the app).

//If you don’t declare it public, other packages won’t be able to use it – you’d get a compile-time error.
public class MemberNotFoundException extends Exception{
    public MemberNotFoundException(String memberId) {
        super("Member with ID '" + memberId + "' not found!");
    }
}
