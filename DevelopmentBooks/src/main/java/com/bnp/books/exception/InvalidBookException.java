package com.bnp.books.exception;

public class InvalidBookException extends RuntimeException {
    private static final long serialVersionUID = -3020445250998809197L;

	public InvalidBookException(String message) {
        super(message);
    }
}

