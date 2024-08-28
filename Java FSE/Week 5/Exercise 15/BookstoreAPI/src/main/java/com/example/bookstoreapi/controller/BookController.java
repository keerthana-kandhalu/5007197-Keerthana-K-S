package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.assembler.BookResourceAssembler;
import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import com.example.bookstoreapi.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Api(value = "Book API")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book created"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Book>> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book book = bookService.saveBook(bookService.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResourceAssembler.toModel(book));
    }

    @ApiOperation(value = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books retrieved"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Book>>> getBooks() {
        List<Book> books = bookService.findAllBooks();
        CollectionModel<EntityModel<Book>> bookModels = bookResourceAssembler.toModel(books);
        return ResponseEntity.ok(bookModels);
    }


    @ApiOperation(value = "Get book by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> ResponseEntity.ok(bookResourceAssembler.toModel(b)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update book by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book updated"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookService.convertToEntity(bookDTO));
        return ResponseEntity.ok(bookResourceAssembler.toModel(updatedBook));
    }

    @ApiOperation(value = "Delete book by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book deleted"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}