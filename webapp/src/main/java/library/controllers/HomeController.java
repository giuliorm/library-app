package library.controllers;

import com.ibm.websphere.security.UserRegistry;
import library.domain.Book;
import library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Created by zotova on 26.07.2016.
 */
@Controller
@RequestMapping("/")
@ImportResource("classpath:applicationContext.xml")
public class HomeController {

    @Autowired
    LibraryService libraryService;

    @Autowired
    UserRegistry userRegistry;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {


        //Collection<Book> books = libraryService.all();
        return "login";
    }

 /*   @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

     //   Collection<Book> books = libraryService.all();
        return "index";
    } */

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {

       // Collection<Book> books = libraryService.all();
        return "index";
    }

   /* @RequestMapping("/index")
    public String index1() {
        return "index";
    }
*/
}
