package com.BookStore.BookStoreManager.service;

import com.BookStore.BookStoreManager.entity.Book;
import com.BookStore.BookStoreManager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        Book bookToSave = new Book();
        bookToSave.setName("Wings of Fire");
        bookToSave.setAuthor("APJ Abdul Kalam");
        bookToSave.setPrice("1999");

        bookService.save(bookToSave);

        verify(bRepo, times(1)).save(bookToSave);
    }

    @Test
    void testGetAllBook() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Wings Of Fire", "APJ Abdul Kalam", "1999"));
        books.add(new Book(2, "Balarama", "Manorama", "30"));

        when(bRepo.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBook();

        assertEquals(2, result.size());

        assertEquals("Wings Of Fire", result.get(0).getName());
        assertEquals("APJ Abdul Kalam", result.get(0).getAuthor());
        assertEquals("1999", result.get(0).getPrice());

        assertEquals("Balarama", result.get(1).getName());
        assertEquals("Manorama", result.get(1).getAuthor());
        assertEquals("30", result.get(1).getPrice());
    }

    @Test
    void testGetBookById() {
        int bookId = 1;
        Book expectedBook = new Book(bookId, "Wings Of Fire", "APJ Abdul Kalam", "1999");

        when(bRepo.findById(bookId)).thenReturn(Optional.of(expectedBook));

        Book result = bookService.getBookById(bookId);

        assertEquals(expectedBook.getId(), result.getId());
        assertEquals(expectedBook.getName(), result.getName());
        assertEquals(expectedBook.getAuthor(), result.getAuthor());
        assertEquals(expectedBook.getPrice(), result.getPrice());
    }


    @Test
    void testDeleteById() {
        int bookId = 1;

        bookService.deleteById(bookId);

        verify(bRepo, times(1)).deleteById(bookId);
    }
}
