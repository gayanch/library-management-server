package com.github.gayanch.library.book;

import com.github.gayanch.library.model.Book;
import com.github.gayanch.library.model.Borrower;

import java.util.Objects;

public final class BookMapper {
    private BookMapper() {}

    public static Book documentToDto(BookDocument doc) {
        var book = new Book(doc.getId(), doc.getIsbn(), doc.getTitle(), doc.getAuthor());

        var borrower = doc.getBorrower();
        if (Objects.nonNull(borrower)) {
            book.borrower(new Borrower(borrower.getId(), borrower.getName(), borrower.getEmail()));
        }

        return book;
    }
}
