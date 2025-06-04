package com.github.gayanch.library.book;

import com.github.gayanch.library.api.BooksApi;
import com.github.gayanch.library.common.UriService;
import com.github.gayanch.library.model.Book;
import com.github.gayanch.library.model.BorrowBook;
import com.github.gayanch.library.model.RegisterBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController implements BooksApi {
    private final BookService bookService;
    private final UriService uriService;

    public BookController(BookService bookService, UriService uriService) {
        this.bookService = bookService;
        this.uriService = uriService;
    }

    @Override
    public ResponseEntity<Book> registerBook(RegisterBook registerBook) {
        var book = bookService.register(registerBook);
        return ResponseEntity.created(uriService.createUri("books", book.getId())).body(book);
    }


    //ToDo: Response paging support
    @Override
    public ResponseEntity<List<Book>> getAllBooks() {
        var books = bookService.getAll();
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<Book> borrowBook(String id, BorrowBook borrowBook) {
        var book = bookService.borrow(id, borrowBook);
        return ResponseEntity.created(uriService.createUri("books", id, "borrow")).body(book);
    }

    @Override
    public ResponseEntity<Void> returnBook(String id) {
        bookService.returnBook(id);
        return ResponseEntity.noContent().build();
    }
}
