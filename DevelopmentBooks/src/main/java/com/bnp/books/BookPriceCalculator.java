package com.bnp.books;

import java.util.List;
import java.util.stream.Collectors;

public class BookPriceCalculator {

	private static final double DISCOUNT_5_PERCENT = 0.05;
	private static final double DISCOUNT_10_PERCENT = 0.10;
	private static final double DISCOUNT_20_PERCENT = 0.20;
	private static final double DISCOUNT_25_PERCENT = 0.25;

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

			int distinctCount = distinct.size();

			totalPrice = switch (distinctCount) {
			case 2 -> applyDiscount(calculateTotalPrice(books), DISCOUNT_5_PERCENT);
			case 3 -> applyDiscount(calculateTotalPrice(books), DISCOUNT_10_PERCENT);
			case 4 -> applyDiscount(calculateTotalPrice(books), DISCOUNT_20_PERCENT);
			case 5 -> applyDiscount(calculateTotalPrice(books), DISCOUNT_25_PERCENT);
			default -> calculateTotalPrice(books);
			};

		}

		return totalPrice;
	}

	private double applyDiscount(double totalPrice, double discount) {
		totalPrice = totalPrice * (1 - discount);
		return totalPrice;
	}

	private double calculateTotalPrice(List<Book> books) {
		double totalPrice;
		totalPrice = books.stream().mapToDouble(Book::price).sum();
		return totalPrice;
	}

}
