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
		return bookPriceCalculator.caluclatePriceFor(books);
	}

}
