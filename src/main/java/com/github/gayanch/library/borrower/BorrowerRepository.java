package com.github.gayanch.library.borrower;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends MongoRepository<BorrowerDocument, String> {
}
