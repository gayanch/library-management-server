package com.github.gayanch.library.borrower;

import com.github.gayanch.library.model.Borrower;

public final class BorrowerMapper {
    private BorrowerMapper() {}

    public static Borrower documentToDto(BorrowerDocument doc) {
        return new Borrower(doc.getId(), doc.getName(), doc.getEmail());
    }
}
