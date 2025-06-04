package com.github.gayanch.library.book;

import com.github.gayanch.library.borrower.BorrowerService;
import com.github.gayanch.library.data.MockData;
import com.github.gayanch.library.error.AppException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    BorrowerService borrowerService;

    BookService bookService;

    @BeforeEach
    public void reset() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(bookRepository, borrowerService);

        this.bookService = new BookService(bookRepository, borrowerService);
    }

    @Test
    void testRegisterBookSuccess() {
        Mockito.when(bookRepository.findOne(Mockito.any())).thenReturn(Optional.empty());

        var mockBook = MockData.createBookDocument();
        Mockito.when(bookRepository.save(Mockito.any(BookDocument.class))).thenReturn(mockBook);

        var registerBook = MockData.createRegisterBook();
        var book = bookService.register(registerBook);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(registerBook.getIsbn(), book.getIsbn());
    }

    @Test
    void testRegisterSecondCopySuccess() {
        var mockBook = MockData.createBookDocument();

        Mockito.when(bookRepository.findOne(Mockito.any())).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(Mockito.any(BookDocument.class))).thenReturn(mockBook);

        var registerBook = MockData.createRegisterBook();
        var book = bookService.register(registerBook);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(registerBook.getIsbn(), book.getIsbn());
    }

    @Test
    void testRegisterBookWhenIsbnMatchesAndAuthorNameMismatches() {
        var existingBook = MockData.createBookDocument("Hello Book", "Author 2");

        Mockito.when(bookRepository.findOne(Mockito.any())).thenReturn(Optional.of(existingBook));

        var registerBook = MockData.createRegisterBook();

        Assertions.assertThrows(AppException.class, () -> bookService.register(registerBook));
    }

    @Test
    void testRegisterBookWhenIsbnMatchesAndTitleMismatches() {
        var existingBook = MockData.createBookDocument("Other Book", "John Doe");

        Mockito.when(bookRepository.findOne(Mockito.any())).thenReturn(Optional.of(existingBook));

        var registerBook = MockData.createRegisterBook();

        Assertions.assertThrows(AppException.class, () -> bookService.register(registerBook));
    }

    @Test
    void testBorrowBookSuccess() {
        var request = MockData.createBorrowBook();

        Mockito.when(bookRepository.findById("book-1")).thenReturn(Optional.of(MockData.createBookDocument()));
        Mockito.when(borrowerService.getById("borrower-1")).thenReturn(MockData.createBorrower());
        Mockito.when(bookRepository.save(Mockito.any(BookDocument.class))).thenReturn(MockData.createBookDocumentWithBorrower());

        var book = bookService.borrow("book-1", request);

        Assertions.assertNotNull(book);
    }

    @Test
    void testBorrowBookWhenTheBookDoesNotExist() {
        var request = MockData.createBorrowBook();

        Mockito.when(bookRepository.findById("book-1")).thenReturn(Optional.empty());

        Assertions.assertThrows(AppException.class, () -> bookService.borrow("book-1", request));
    }

    @Test
    void testBorrowBookWhenTheBookIsAlreadyBorrowed() {
        var request = MockData.createBorrowBook();

        Mockito.when(bookRepository.findById("book-1")).thenReturn(Optional.of(MockData.createBookDocumentWithBorrower()));

        Assertions.assertThrows(AppException.class, () -> bookService.borrow("book-1", request));
    }

    @Test
    void testReturnBookSuccess() {
        Mockito.when(bookRepository.findById("book-1")).thenReturn(Optional.of(MockData.createBookDocumentWithBorrower()));

        Assertions.assertDoesNotThrow(() -> bookService.returnBook("book-1"));
    }

    @Test
    void testReturnBookWhenBookDoesNotExist() {
        Mockito.when(bookRepository.findById("book-1")).thenReturn(Optional.empty());

        Assertions.assertThrows(AppException.class, () -> bookService.returnBook("book-1"));
    }
}
