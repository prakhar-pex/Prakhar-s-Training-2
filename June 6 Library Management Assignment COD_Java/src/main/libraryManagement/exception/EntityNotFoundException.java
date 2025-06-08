package main.libraryManagement.exception;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String entityType, String id) {
        super(entityType + " with ID '" + id + "' not found!");
    }
}
