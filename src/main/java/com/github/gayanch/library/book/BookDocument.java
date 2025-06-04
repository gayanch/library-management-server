package com.github.gayanch.library.book;

import com.github.gayanch.library.borrower.BorrowerDocument;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Document(collection = "books")
public class BookDocument {
    @Id
    private String id;

    private String isbn;

    private String title;

    private String author;

    @DocumentReference
    private BorrowerDocument borrower;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BorrowerDocument getBorrower() {
        return borrower;
    }

    public void setBorrower(BorrowerDocument borrower) {
        this.borrower = borrower;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static BookDocument newBook(String isbn, String title, String author) {
        var book = new BookDocument();
        book.isbn = isbn;
        book.title = title;
        book.author = author;

        return book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDocument that = (BookDocument) o;
        return Objects.equals(id, that.id) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BookDocument.class.getSimpleName() + "[", "]")
            .add("id='" + id + "'")
            .add("isbn='" + isbn + "'")
            .add("title='" + title + "'")
            .add("author='" + author + "'")
            .add("borrower=" + borrower)
            .add("createdAt=" + createdAt)
            .add("updatedAt=" + updatedAt)
            .toString();
    }
}
