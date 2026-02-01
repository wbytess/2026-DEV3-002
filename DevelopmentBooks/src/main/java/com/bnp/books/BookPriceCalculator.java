package com.bnp.books;

import java.util.ArrayList;
import java.util.Comparator;
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
		return rebalanceGroups(groups);
	}
	
	
	public List<List<Book>> rebalanceGroups(List<List<Book>> groupsToBeRebalanced) {
		List<List<Book>>  groups =  groupsToBeRebalanced;

	    // Group by size (e.g. size -> list of groups)
	    var groupsBySize = groups.stream()
	            .collect(Collectors.groupingBy(List::size));

	    // Sort sizes descending
	    var sizesDesc = groupsBySize.keySet().stream()
	            .sorted(Comparator.reverseOrder())
	            .toList();

	    for (int i = 0; i < sizesDesc.size(); i++) {
	        for (int j = sizesDesc.size() - 1; j > i; j--) {

	            int largerSize = sizesDesc.get(i);
	            int smallerSize = sizesDesc.get(j);

	            // Only rebalance meaningful differences
	            if (largerSize - smallerSize < 2) {
	                continue;
	            }

	            var largerGroups = groupsBySize.get(largerSize);
	            var smallerGroups = groupsBySize.get(smallerSize);

	            int pairs = Math.min(largerGroups.size(), smallerGroups.size());

	            for (int p = 0; p < pairs; p++) {
	                if (rebalancePair(largerGroups.get(p), smallerGroups.get(p))) {

	                    // Update group sizes after rebalance
	                    groupsBySize
	                            .computeIfAbsent(largerSize - 1, k -> new ArrayList<>())
	                            .add(largerGroups.get(p));

	                    groupsBySize
	                            .computeIfAbsent(smallerSize + 1, k -> new ArrayList<>())
	                            .add(smallerGroups.get(p));
	                }
	            }
	        }
	    }
	    return groups;
	}

	private boolean rebalancePair(List<Book> larger, List<Book> smaller) {
	    for (var iterator = larger.iterator(); iterator.hasNext(); ) {
	        var book = iterator.next();

	        if (!smaller.contains(book)) {
	            iterator.remove();
	            smaller.add(book);
	            return true;
	        }
	    }
	    return false;
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
