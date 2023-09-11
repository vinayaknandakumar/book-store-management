package com.BookStore.BookStoreManager.controller;

import com.BookStore.BookStoreManager.entity.Book;
import com.BookStore.BookStoreManager.entity.MyBookList;
import com.BookStore.BookStoreManager.service.BookService;
import com.BookStore.BookStoreManager.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private MyBookListService myBookService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    /**
     * show the book register page
     * @return bookRegister
     */
    @GetMapping("/book_register")
    public String bookRegister(){
        return "bookRegister";
    }

    /*model and view for available books
     */
    @GetMapping("/available_books")
    public ModelAndView getAllBook(){
        List<Book> list = service.getAllBook();
        //ModelAndView m = new ModelAndView();
        return new ModelAndView("bookList","book",list);
    }

//    @PostMapping("/save")
//    public String addBook(@ModelAttribute Book b){
//        service.save(b);
//        //model.addAttribute("successMessage", "The operation was successful.");
//        return "redirect://localhost:8080";
//    }

    /**
     * to register book in the db
     * @param book
     * @param redirAttrs
     * @return
     */
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirAttrs) {
        service.save(book);
        redirAttrs.addAttribute("message", "Book added successfully");
        return "redirect:/book_register"; // Redirect to the home page or the desired page
    }

    /**
     * to return my books
     * @param model
     * @return myBooks
     */
    @GetMapping("/my_books")
    public String getMyBooks(Model model){
        List<MyBookList> list = myBookService.getAllMyBooks();
        model.addAttribute("book",list);
        return "myBooks";
    }

    /**
     * for adding books to booklist database
     * @param id
     * @return my_books
     */
    @GetMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id){
        Book b = service.getBookById(id);
        MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
        myBookService.saveMyBooks(mb);
        return "redirect://localhost:8080/my_books";
    }

    /**
     * to edit a book data
     * @param id
     * @param model
     * @return bookEdit
     */
    @GetMapping("/editBook/{id}")
    public String editbook(@PathVariable("id") int id,Model model){
        Book book = service.getBookById(id);
        model.addAttribute("book",book);//b-book
        return "bookEdit";
    }

    /**
     * making edit changes to db
     * @param book
     * @param id
     * @param redirAttrs
     * @return st
     */
    @PostMapping("/save/edit")
    public String editBook(@ModelAttribute Book book,@RequestParam("id") int id, RedirectAttributes redirAttrs) {
        service.save(book);
        String st="redirect:/editBook/"+id;
        redirAttrs.addAttribute("message", "Book edited successfully");
        return st; // Redirect to the home page or the desired page
    }


//    @GetMapping("/deleteBook/{id}")
//    public String deleteBook(@PathVariable("id") int id){
//        service.deleteById(id);
//        return "redirect://localhost:8080/available_books";
//    }

    /**
     * for deletebyid
     * @param id
     * @param redirectAttributes
     * @return available_books
     */
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id,RedirectAttributes redirectAttributes){
        service.deleteById(id);
        redirectAttributes.addAttribute("message", "Book deleted successfully");
        return "redirect://localhost:8080/available_books";
    }
}


















//    @GetMapping("/mylist/{id}")
//    public String getMyList(@PathVariable("id") int id) {
//        Book b = service.getBookById(id);
//
//        // Check if the MyBookList already exists in the database
//        if (myBookService.existsMyBookList(b.getId())) {
//            throw new MyBookListAlreadyExistsException("MyBookList already exists in the database");
//        }
//
//        MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
//        myBookService.saveMyBooks(mb);
//        return "redirect://localhost:8080/my_books";
//    }


//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(MyBookListAlreadyExistsException.class)
//    public ResponseEntity<String> handleMyBookListAlreadyExistsException(MyBookListAlreadyExistsException ex) {
//        // Customize the response message or return an error page
//        String errorMessage = "MyBookList already exists: " + ex.getMessage();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
//    }
//}
