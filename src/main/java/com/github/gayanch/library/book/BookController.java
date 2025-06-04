package com.github.gayanch.library.book;

import com.github.gayanch.library.api.BooksApi;
import com.github.gayanch.library.model.Book;
import com.github.gayanch.library.model.BorrowBook;
import com.github.gayanch.library.model.RegisterBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class BookController implements BooksApi {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<Book> registerBook(RegisterBook registerBook) {
        var book = bookService.register(registerBook);
        return ResponseEntity.created(URI.create("")).body(book);
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
        return ResponseEntity.created(URI.create("")).body(book);
    }

    @Override
    public ResponseEntity<Void> returnBook(String id) {
        bookService.returnBook(id);
        return ResponseEntity.noContent().build();
    }
}
