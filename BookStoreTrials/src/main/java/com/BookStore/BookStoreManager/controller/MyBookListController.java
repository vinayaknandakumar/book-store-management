package com.BookStore.BookStoreManager.controller;

import com.BookStore.BookStoreManager.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyBookListController {

    @Autowired
    private MyBookListService service;


    /**
     *  delete book from mybook
     * @param id
     * @param redirectAttributes
     * @return my_books
     */
    @GetMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id") int id,RedirectAttributes redirectAttributes){
        service.deleteById(id);
        redirectAttributes.addAttribute("message", "Book deleted successfully");
        return "redirect:/my_books";
    }

//    @GetMapping("/deleteBook/{id}")
//    public String deleteBook(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
//        service.deleteById(id);
//        redirectAttributes.addAttribute("message", "Book deleted successfully");
//        return "redirect://localhost:8080/available_books";
//    }

}
