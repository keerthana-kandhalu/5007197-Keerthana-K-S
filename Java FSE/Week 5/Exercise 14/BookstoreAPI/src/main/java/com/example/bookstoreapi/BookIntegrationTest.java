package com.example.bookstoreapi;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll(); // Clear the repository before each test
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPrice(45.99);
        book.setIsbn("9780134685991");

        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPrice(29.99);
        book.setIsbn("9780132350884");
        Book savedBook = bookRepository.save(book);

        mockMvc.perform(get("/books/" + savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Java Concurrency in Practice");
        book.setAuthor("Brian Goetz");
        book.setPrice(39.99);
        book.setIsbn("9780321349606");
        Book savedBook = bookRepository.save(book);

        book.setPrice(49.99);

        mockMvc.perform(put("/books/" + savedBook.getId())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(49.99));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("Introduction to Algorithms");
        book.setAuthor("Thomas H. Cormen");
        book.setPrice(59.99);
        book.setIsbn("9780262033848");
        Book savedBook = bookRepository.save(book);

        mockMvc.perform(delete("/books/" + savedBook.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/" + savedBook.getId()))
                .andExpect(status().isNotFound());
    }
}