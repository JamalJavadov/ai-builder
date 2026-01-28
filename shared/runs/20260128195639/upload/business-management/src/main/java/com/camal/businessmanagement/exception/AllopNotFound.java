package com.camal.businessmanagement.exception;

@jakarta.annotation.Generated("java-project-crud.py")
public class AllopNotFound extends NotFoundException {
    public AllopNotFound(Object idOrHint) {
        super("Allop not found: " + idOrHint);
    }
}
