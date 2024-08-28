package com.example.bookstoreapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.example.bookstoreapi.controller.BookController;
import com.example.bookstoreapi.model.Book;

@Component
public class BookResourceAssembler {

    public EntityModel<Book> toModel(Book book) {
        EntityModel<Book> bookResource = EntityModel.of(book);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel();
        bookResource.add(selfLink);

        return bookResource;
    }
}

