package com.BookStore.BookStoreManager.service;

import com.BookStore.BookStoreManager.entity.Book;
import com.BookStore.BookStoreManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bRepo;
    /**
     * insert operation
     * @param book
     */
    public void save(Book book){
        bRepo.save(book);
    }

    public List<Book> getAllBook(){
        return bRepo.findAll();
    }

    public Book getBookById(int id){
        return bRepo.findById(id).get();
    }

    public void deleteById(int id){
        bRepo.deleteById(id);
    }
}
