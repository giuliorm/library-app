package library.services;

import library.domain.Book;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by zotova on 29.07.2016.
 */

public interface LibraryService {

    Collection<Book> all();
    void createOrUpdate(Book book);
    void remove(Integer bookId);
    Book findById(Integer bookId);
    Collection<Book> findByName(String name);
    Collection<Book> findByNameAndAuthor(String name, String author);
}
