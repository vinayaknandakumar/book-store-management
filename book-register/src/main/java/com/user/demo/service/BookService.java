package com.user.demo.service;

import com.user.demo.Repository.BookRepository;
import com.user.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bRepo;

    public void save(Book b){
        bRepo.save(b);
    }
}
