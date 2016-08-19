package library.repository;

import library.domain.Book;

import java.util.Collection;

/**
 * Created by zotova on 27.07.2016.
 */
public interface LibraryRepository {


    Collection<Book> all();
    int create(Book book);
    int update(Book book);
    void remove(int bookId);
    Book findById(int bookId);
    Collection<Book> findByName(String name);
    Collection<Book> findByNameAndAuthor(String name, String author);
}
