package com.github.gayanch.library.borrower;

import com.github.gayanch.library.api.BorrowersApi;
import com.github.gayanch.library.common.UriService;
import com.github.gayanch.library.model.Borrower;
import com.github.gayanch.library.model.CreateBorrower;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BorrowerController implements BorrowersApi {
    private final BorrowerService borrowerService;
    private final UriService uriService;

    public BorrowerController(BorrowerService borrowerService, UriService uriService) {
        this.borrowerService = borrowerService;
        this.uriService = uriService;
    }

    @Override
    public ResponseEntity<Borrower> createBorrower(CreateBorrower createBorrower) {
        var borrower = borrowerService.create(createBorrower);
        return ResponseEntity.created(uriService.createUri("borrowers", borrower.getId())).body(borrower);
    }

    //ToDo: Response paging support
    @Override
    public ResponseEntity<List<Borrower>> getAllBorrowers() {
        var borrowers = borrowerService.getAll();
        return ResponseEntity.ok(borrowers);
    }
}
