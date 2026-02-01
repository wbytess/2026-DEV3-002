package com.bnp.books;

import java.util.List;

public class BookPriceCalculator {

	public double caluclatePriceFor(List<Book> books) {
		double totalPrice = 0;
		if(books.isEmpty()) {
			return totalPrice = Double.valueOf(0);
		}
		
		if(books.size() == 1) {
			totalPrice = Double.valueOf(50);
		}
		
	return totalPrice;
	}

}
