package com.bnp.books;

public record Book(String name, double price) {

	private static final double DEFAULT_PRICE = 50.0;

	public Book {
		if (price <= 0) {
			price = DEFAULT_PRICE;
		}
	}
}
