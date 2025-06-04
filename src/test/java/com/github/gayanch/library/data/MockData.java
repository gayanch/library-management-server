package com.github.gayanch.library.data;

import com.github.gayanch.library.book.BookDocument;
import com.github.gayanch.library.borrower.BorrowerDocument;
import com.github.gayanch.library.model.BorrowBook;
import com.github.gayanch.library.model.Borrower;
import com.github.gayanch.library.model.RegisterBook;

import java.time.LocalDateTime;
import java.util.UUID;

public final class MockData {
    private MockData() {}

    public static RegisterBook createRegisterBook() {
        return new RegisterBook("test-isbn-1", "Hello Book", "John Doe");
    }

    public static BookDocument createBookDocument() {
        var book = BookDocument.newBook("test-isbn-1", "Hello Book", "John Doe");
        book.setId(UUID.randomUUID().toString());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        return book;
    }

    public static BookDocument createBookDocument(String title, String author) {
        var book = BookDocument.newBook("test-isbn-1", title, author);
        book.setId(UUID.randomUUID().toString());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        return book;
    }

    public static BookDocument createBookDocumentWithBorrower() {
        var book = createBookDocument();
        book.setBorrower(createBorrowerDocument());

        return book;
    }

    public static BorrowerDocument createBorrowerDocument() {
        var borrower = BorrowerDocument.newBorrower("Batman", "batman@example.com");
        borrower.setId(UUID.randomUUID().toString());
        borrower.setCreatedAt(LocalDateTime.now());
        borrower.setUpdatedAt(LocalDateTime.now());

        return borrower;
    }

    public static BorrowBook createBorrowBook() {
        return new BorrowBook("borrower-1");
    }

    public static Borrower createBorrower() {
        return new Borrower("borrower-1", "Batman", "batman@example.com");
    }
}
