package library.repository;

import library.domain.Book;
import library.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

/**
 * Created by zotova on 27.07.2016.
 */
@ImportResource("classpath:applicationContext.xml")
@Repository("libraryDao")
public class LibraryRepositoryImpl implements LibraryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Collection<Book> all()  {

     //   entityManager.getTransaction().begin();
        //language=PostgreSQL
        Collection<Book> books = entityManager.createNamedQuery(Book.ALL).getResultList();
       // entityManager.getTransaction().commit();
      //  entityManager.close();
        return books;
    }

    @Transactional
    public int create(Book book) {

      //  entityManager.getTransaction().begin();

        return entityManager.createNativeQuery(Book.INSERT)
                .setParameter("1", book.getBookName())
                .setParameter("2", book.getAuthor())
                .executeUpdate();
      //  entityManager.getTransaction().commit();
      //  entityManager.close();
    }

    @Transactional
    public int update(Book book) {

        //  entityManager.getTransaction().begin();

        return entityManager.createNativeQuery(Book.UPDATE)
                .setParameter("1", book.getBookName())
                .setParameter("2", book.getAuthor())
                .setParameter("3", book.getId())
                .executeUpdate();
        //  entityManager.getTransaction().commit();
        //  entityManager.close();
    }

    @Transactional
    public void remove(int bookId) {
       // entityManager.getTransaction().begin();
        entityManager.createNamedQuery(Book.REMOVE)
                .setParameter("id", bookId).executeUpdate();
       // entityManager.getTransaction().commit();
        //entityManager.close();
    }

    @Transactional
    public Book findById(int bookId) {

      //  entityManager.getTransaction().begin();
        Collection<Book> book = entityManager.createNamedQuery(Book.FINDBYID)
                .setParameter("id", bookId).getResultList();
       // entityManager.getTransaction().commit();
       // entityManager.close();
        return book != null && book.iterator().hasNext() ? book.iterator().next() : null;
    }

    public Collection<Book> findByName(String name) {
       // entityManager.getTransaction().begin();
        Collection<Book> books = entityManager.createNamedQuery(Book.FINDBYID)
                .setParameter("bookname", name).getResultList();
       // entityManager.getTransaction().commit();
      //  entityManager.close();
        return books;
    }
    public Collection<Book> findByNameAndAuthor(String name, String author) {
       // entityManager.getTransaction().begin();
        Collection<Book> books = entityManager.createNamedQuery(Book.FINDBYID)
                .setParameter("bookname", name)
                .setParameter("author", author).getResultList();
     //   entityManager.getTransaction().commit();
      //  entityManager.close();
        return books;
    }

  //  public void insert(Person person) {
    //    entityManager.persist(person);
   // }




}
