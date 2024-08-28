package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.assembler.BookResourceAssembler;
import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import com.example.bookstoreapi.model.Book;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @GetMapping("/{id}")
    public EntityModel<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return bookResourceAssembler.toModel(book);
    }

    @GetMapping
    public CollectionModel<EntityModel<Book>> getAllBooks() {
        List<EntityModel<Book>> books = bookService.findAllBooks().stream()
                .map(bookResourceAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(books);
    }

    @PostMapping
    public EntityModel<Book> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book book = bookService.saveBook(bookService.convertToEntity(bookDTO));
        return bookResourceAssembler.toModel(book);
    }

    @PutMapping("/{id}")
    public EntityModel<Book> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        Book book = bookService.updateBook(id, bookService.convertToEntity(bookDTO));
        return bookResourceAssembler.toModel(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}