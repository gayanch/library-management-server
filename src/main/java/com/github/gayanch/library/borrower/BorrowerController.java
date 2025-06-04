package com.github.gayanch.library.borrower;

import com.github.gayanch.library.api.BorrowersApi;
import com.github.gayanch.library.model.Borrower;
import com.github.gayanch.library.model.CreateBorrower;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class BorrowerController implements BorrowersApi {
    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @Override
    public ResponseEntity<Borrower> createBorrower(CreateBorrower createBorrower) {
        var borrower = borrowerService.create(createBorrower);
        return ResponseEntity.created(URI.create("")).body(borrower);
    }

    //ToDo: Response paging support
    @Override
    public ResponseEntity<List<Borrower>> getAllBorrowers() {
        var borrowers = borrowerService.getAll();
        return ResponseEntity.ok(borrowers);
    }
}
