package com.example.bookstoreapi.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.example.bookstoreapi.controller.BookController;
import com.example.bookstoreapi.model.Book;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookResourceAssembler {

    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
    }

    public CollectionModel<EntityModel<Book>> toModel(Iterable<? extends Book> books) {
        CollectionModel<EntityModel<Book>> bookCollectionModel = CollectionModel.of(
                StreamSupport.stream(books.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList())
        );
        bookCollectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBooks()).withSelfRel());
        return bookCollectionModel;
    }
}

