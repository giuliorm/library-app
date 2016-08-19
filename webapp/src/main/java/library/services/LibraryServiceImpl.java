package library.services;

import library.domain.Book;
import library.repository.LibraryRepository;
import library.repository.LibraryRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by zotova on 29.07.2016.
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    LibraryRepository repository;

    public Collection<Book> all() {
        return repository.all();
    }

    public void createOrUpdate(Book book) {
        if (repository.update(book) <= 0)
            repository.create(book);
    }
    public void remove(Integer bookId) {
        if (bookId != null)
            repository.remove(bookId);
    }
    public Book findById(Integer bookId)
    {
        return bookId != null ? repository.findById(bookId) : null;
    }
    public Collection<Book> findByName(String name) {
        return repository.findByName(name);
    }
    public Collection<Book> findByNameAndAuthor(String name, String author) {
        return repository.findByNameAndAuthor(name, author);
    }
}
