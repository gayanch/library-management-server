package com.github.gayanch.library.book;

import com.github.gayanch.library.borrower.BorrowerDocument;
import com.github.gayanch.library.borrower.BorrowerService;
import com.github.gayanch.library.error.AppException;
import com.github.gayanch.library.model.Book;
import com.github.gayanch.library.model.BorrowBook;
import com.github.gayanch.library.model.RegisterBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final BorrowerService borrowerService;

    public BookService(BookRepository bookRepository, BorrowerService borrowerService) {
        this.bookRepository = bookRepository;
        this.borrowerService = borrowerService;
    }

    public Book register(RegisterBook registerBook) {
        validateBookRegistration(registerBook);

        var doc = BookDocument.newBook(registerBook.getIsbn(), registerBook.getTitle(), registerBook.getAuthor());
        var created = bookRepository.save(doc);

        log.info("Book {} - {} registered", created.getId(), created.getTitle());

        return BookMapper.documentToDto(created);
    }

    public List<Book> getAll() {
        var docs = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
        return docs.stream().map(BookMapper::documentToDto).toList();
    }

    public Book borrow(String id, BorrowBook borrowBook) {
        var doc = bookRepository.findById(id)
            .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Book not found"));

        //Cannot borrow a book if there already is a borrower
        if (Objects.nonNull(doc.getBorrower())) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Book is already borrowed");
        }

        var borrower = borrowerService.getById(borrowBook.getBorrowerId());

        //update book with borrower info.
        // Only need to populate the id field in book->borrower. Spring data handles the linking.
        var borrowerDoc = new BorrowerDocument();
        borrowerDoc.setId(borrower.getId());
        doc.setBorrower(borrowerDoc);

        var updatedDoc = bookRepository.save(doc);

        log.info("Book {} - {} was borrowed by the user {} - {}", id, doc.getTitle(), borrower.getName(), borrower.getId());

        return BookMapper.documentToDto(updatedDoc);
    }

    public void returnBook(String id) {
        var doc = bookRepository.findById(id)
            .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Book not found"));

        //Remove the borrower from book and save
        doc.setBorrower(null);
        bookRepository.save(doc);

        log.info("Book: {} - {} returned", doc.getTitle(), id);
    }

    private void validateBookRegistration(RegisterBook registerBook) {
        //check whether books with same ISBN exist
        var query = new BookDocument();
        query.setIsbn(registerBook.getIsbn());

        var optionalDoc = bookRepository.findOne(Example.of(query));
        if (optionalDoc.isEmpty()) {
            log.debug("The library does not have a book with given ISBN {}. Registration can proceed.", registerBook.getIsbn());

            //This is ok. It's the first time we are seeing this ISBN.
            return;
        }

        //A book with the same ISBN exists. Verify author name and title
        var doc = optionalDoc.get();

        //Either we can throw on Author/Title mismatches or
        // we can silently update them to correct values based on existing books
        // Using the first option here. We might have to adopt the second option based on the real world requirements.
        if (!doc.getAuthor().equals(registerBook.getAuthor())) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(),
                "Author name does not match with the books with same ISBN");
        }

        if (!doc.getTitle().equals(registerBook.getTitle())) {
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "Title does not match with the books with same ISBN");
        }
    }
}
