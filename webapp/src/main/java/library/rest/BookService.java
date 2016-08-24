package library.rest;

import com.sun.jersey.core.spi.factory.ResponseImpl;
import library.domain.Book;
import library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by zotova on 15.08.2016.
 */
@Component
@Path("/book")
public class BookService {


    private Integer parseId(String id) {
        Integer intId = null;
        try {
            intId = Integer.parseInt(id);
        }
        catch (Exception ex) {

        }
        return intId;
    }

    @Autowired
    LibraryService libraryService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response all() {

        Collection<Book> books = libraryService.all();
        if (books == null)
            return Response.status(500).type("application/json").build();

        HashMap<String, LinkedList<HashMap<String, String>>> response = new HashMap<>();
        LinkedList<HashMap<String, String>> responseList = new LinkedList<>();
        response.put("books", responseList);

        for (Book book : books) {
            HashMap<String, String> bookResponse = new HashMap<>();
            bookResponse.put("bookId", String.valueOf(book.getId()));
            bookResponse.put("bookName", book.getBookName());
            bookResponse.put("bookAuthor", book.getAuthor());
            responseList.add(bookResponse);
        }

        return Response.ok(response, MediaType.APPLICATION_JSON).type("application/json").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createOrUpdate")
    public Response createBook(
            @DefaultValue("") @QueryParam("bookId") String bookId,
            @DefaultValue("") @QueryParam("bookName") String bookName,
                               @DefaultValue("") @QueryParam("bookAuthor") String bookAuthor) {

        Integer id = parseId(bookId);
        HashMap<String, String> response = new HashMap<>();
        Book book = libraryService.findById(id);

        if (book == null) {
            book = new Book();
        }
        if (bookName != null && !bookName.isEmpty()) {
            book.setBookName(bookName);
        }
        if (bookAuthor != null && !bookAuthor.isEmpty())
            book.setAuthor(bookAuthor);

        book = libraryService.createOrUpdate(book);
        if (book == null)        {
            response.put("info", "Cannot create book with empty parameters");
        }
        else  {
            response.put("bookName", bookName);
            response.put("bookAuthor", bookAuthor);
        }
        return Response.ok(response, MediaType.APPLICATION_JSON).type("application/json").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Response removeBook(@DefaultValue("") @QueryParam("bookId") String bookId) {
        HashMap<String, String> response = new HashMap<>();

        if (bookId == null || bookId != null && bookId.isEmpty()) {
            response.put("info", "Cannot remove book, book id is empty");
            return Response.ok(response, MediaType.APPLICATION_JSON)
                    .type("application/json").build();
        }

        Integer id = parseId(bookId);

        libraryService.remove(id);
        response.put("removed", bookId);
        return Response.ok(response, MediaType.APPLICATION_JSON).type("application/json").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findById")
    public Response findById(@DefaultValue("") @QueryParam("bookId") String bookId) {
        Integer id = parseId(bookId);
        Book book = libraryService.findById(id);
        if (book == null)
            return Response.status(500).type("application/json").build();

        HashMap<String, String> response = new HashMap<>();
        response.put("bookId", bookId);
        response.put("bookName", book.getBookName());
        response.put("bookAuthor", book.getAuthor());
        return Response.ok(response, MediaType.APPLICATION_JSON).type("application/json").build();
    }

}
