package main.libraryManagement.repository;

import main.libraryManagement.exception.EntityNotFoundException;

import java.util.*;

//Using Generics bcuz instead of creating two different classes like bookRepository, and mapRepository
//we can use a single generic type repository, so we can write just once using T, and reuse it for any type:

//through this we dont need to write two different classes, and can use this generic repository as:
//Repository<Book> bookRepo = new Repository<>();
//Repository<Member> memberRepo = new Repository<>();

//bookRepo stores Book objects.
//memberRepo stores Member objects.
//That's the power of generics: Write once, use for many types.


//Navigate to OneNote for detailed breakdown


//Repository is for CRUD. what entity to be stored is defined in services
public class Repository<T>{
    private Map<String, T> storage = new HashMap<>();

    //Adding an entity (entity is entire book/member object)
    public void add(String id, T entity){
        storage.put(id, entity);
    }

    //Get an entity by id (from map)
    public T get(String id, String entityType) throws EntityNotFoundException {
        T entity = storage.get(id);  //get from map
        if (entity == null) {
            throw new EntityNotFoundException(entityType, id);
        }
        return entity;
    }

    //remove an entity
    public void remove(String id){
        storage.remove(id);
    }

    // Check if an entity exists
    public boolean exists(String id) {
        return storage.containsKey(id);
    }


    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }


}
