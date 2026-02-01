package com.bnp.books.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bnp.books.domain.BookPriceCalculator;
import com.bnp.books.domain.model.Book;

@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {
	
	private final BookPriceCalculator bookPriceCalculator;
	
	 public PriceCalculatorServiceImpl(BookPriceCalculator bookPriceCalculator) {
	        this.bookPriceCalculator = bookPriceCalculator;
	    }

	@Override
	public double caluclatePriceForBooks(List<Book> books) {
		if (books == null || books.isEmpty()) {
	        throw new IllegalArgumentException("Book list cannot be empty");
	    }
		
		// Validate each book
        books.forEach(book -> {
        	if (book.name() == null || book.name().isBlank()) {
                throw new IllegalArgumentException("Book name must not be null or empty");
            }
            if (book.price() == null) {
                throw new IllegalArgumentException("Book price must not be null: " + book.name());
            }
            if (book.price() <= 0) {
                throw new IllegalArgumentException("Book price must be greater than zero for book: " + book.name());
            }
        });
	    return bookPriceCalculator.caluclatePriceFor(books);
	}

}
