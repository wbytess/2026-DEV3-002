package com.bnp.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

			List<List<Book>> groups = reArrangeBooksListToCalculateBestPrice(books);
			for (List<Book> group : groups) {

				List<Book> distinct = group.stream().distinct().collect(Collectors.toList());

				double getBooksPriceForGroup = calculateTotalPrice(group);
				double groupPrice = switch (distinct.size()) {
				case 2 -> applyDiscount(getBooksPriceForGroup, DISCOUNT_5_PERCENT);
				case 3 -> applyDiscount(getBooksPriceForGroup, DISCOUNT_10_PERCENT);
				case 4 -> applyDiscount(getBooksPriceForGroup, DISCOUNT_20_PERCENT);
				case 5 -> applyDiscount(getBooksPriceForGroup, DISCOUNT_25_PERCENT);
				default -> getBooksPriceForGroup;
				};
			totalPrice += groupPrice; 
			}
		}

		return totalPrice;
	}

	public List<List<Book>> reArrangeBooksListToCalculateBestPrice(List<Book> books) {
		List<Book> listOfAllInputBooks = new ArrayList<>(books);
		List<List<Book>> groups = new ArrayList<>();

		while (!listOfAllInputBooks.isEmpty()) {
			List<Book> singleGroup = new ArrayList<>();
			Set<Book> setOfBooks = new HashSet<>();

			Iterator<Book> iterator = listOfAllInputBooks.iterator();
			while (iterator.hasNext()) {
				Book book = iterator.next();
				if (setOfBooks.add(book)) {
					singleGroup.add(book);
					iterator.remove();
				}
			}
			groups.add(singleGroup);
		}
		return groups;
	}

	private double applyDiscount(double price, double discount) {
		return price * (1 - discount);
		
	}

	private double calculateTotalPrice(List<Book> books) {
		double totalPrice;
		totalPrice = books.stream().mapToDouble(Book::price).sum();
		return totalPrice;
	}

}
