package com.bnp.books;

import java.util.List;
import java.util.stream.Collectors;

public class BookPriceCalculator {

	public double caluclatePriceFor(List<Book> books) {
		double totalPrice = 0;
		if (books.isEmpty()) {
			return totalPrice = Double.valueOf(0);
		}

		if (books.size() == 1) {

			totalPrice = Double.valueOf(books.get(0).price());
		}
		
		else {
			List<Book> distinct = books.stream().distinct().collect(Collectors.toList());
			
			if(distinct.size() > 1) {
				 if(distinct.size() == 2) {
					 totalPrice = books.stream()
						        .mapToDouble(Book::price)
						        .sum(); 
					 totalPrice = totalPrice * (1 - 0.05);
				 }
			}else {
				totalPrice = books.stream()
				        .mapToDouble(Book::price)
				        .sum();
			}
			 
		}

		return totalPrice;
	}

}
