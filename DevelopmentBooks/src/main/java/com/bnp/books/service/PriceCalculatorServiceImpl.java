package com.bnp.books.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bnp.books.BookPriceCalculator;

@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {
	
	private final BookPriceCalculator bookPriceCalculator;
	
	 public PriceCalculatorServiceImpl(BookPriceCalculator bookPriceCalculator) {
	        this.bookPriceCalculator = bookPriceCalculator;
	    }

	@Override
	public double caluclatePriceForBooks(List<com.bnp.books.Book> books) {
		return bookPriceCalculator.caluclatePriceFor(books);
	}

	

}
