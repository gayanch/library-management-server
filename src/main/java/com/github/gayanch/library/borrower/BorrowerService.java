package com.github.gayanch.library.borrower;

import com.github.gayanch.library.error.AppException;
import com.github.gayanch.library.model.Borrower;
import com.github.gayanch.library.model.CreateBorrower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BorrowerService {
    private static final Logger log = LoggerFactory.getLogger(BorrowerService.class);

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public Borrower getById(String id) {
        var doc = borrowerRepository.findById(id)
            .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND.value(), "Borrower not found"));

        return BorrowerMapper.documentToDto(doc);
    }

    public Borrower create(CreateBorrower createBorrower) {
        //check whether a Borrower exists with the same email
        var existingBorrower = findByEmail(createBorrower.getEmail());
        if (Objects.nonNull(existingBorrower)) {
            //do not allow duplicate email addresses
            throw new AppException(HttpStatus.BAD_REQUEST.value(), "A Borrower with the same email already exists");
        }

        var doc = BorrowerDocument.newBorrower(createBorrower.getName(), createBorrower.getEmail());
        var created = borrowerRepository.save(doc);

        log.info("Borrower: {} - {} created", created.getName(), created.getId());

        return BorrowerMapper.documentToDto(created);
    }

    public List<Borrower> getAll() {
        var docs = borrowerRepository.findAll();
        return docs.stream().map(BorrowerMapper::documentToDto).toList();
    }

    private BorrowerDocument findByEmail(String email) {
        var query = new BorrowerDocument();
        query.setEmail(email);

        return borrowerRepository.findOne(Example.of(query)).orElse(null);
    }
}
