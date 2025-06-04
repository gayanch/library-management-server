package com.github.gayanch.library.borrower;

import com.github.gayanch.library.error.AppException;
import com.github.gayanch.library.model.Borrower;
import com.github.gayanch.library.model.CreateBorrower;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {
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
        var doc = BorrowerDocument.newBorrower(createBorrower.getName(), createBorrower.getEmail());
        var created = borrowerRepository.save(doc);
        return BorrowerMapper.documentToDto(created);
    }

    public List<Borrower> getAll() {
        var docs = borrowerRepository.findAll();
        return docs.stream().map(BorrowerMapper::documentToDto).toList();
    }
}
