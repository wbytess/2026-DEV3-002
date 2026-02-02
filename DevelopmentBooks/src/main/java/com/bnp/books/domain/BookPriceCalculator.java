package com.bnp.books.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.bnp.books.domain.model.Book;

public class BookPriceCalculator {

	private static final Map<Integer, Double> DISCOUNTS = Map.of(
	        2, 0.05,
	        3, 0.10,
	        4, 0.20,
	        5, 0.25
	);

	public double calculatePriceFor(List<Book> books) {
	    if (books == null || books.isEmpty()) {
	        return 0.0;
	    }

	    return reArrangeBooksListToCalculateBestPrice(books).stream()
	            .mapToDouble(this::calculateGroupPrice)
	            .sum();
	}

	private double calculateGroupPrice(List<Book> group) {
	    int distinctCount = (int) group.stream()
	            .map(Book::name)
	            .distinct()
	            .count();

	    double basePrice = calculateTotalPrice(group);
	    double discount = DISCOUNTS.getOrDefault(distinctCount, 0.0);

	    return applyDiscount(basePrice, discount);
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

                if (largerSize - smallerSize < 2) {
                    continue;
                }

                var largerGroups = groupsBySize.get(largerSize);
                var smallerGroups = groupsBySize.get(smallerSize);

                int pairs = Math.min(largerGroups.size(), smallerGroups.size());

                for (int p = 0; p < pairs; p++) {
                    rebalancePair(largerGroups.get(p), smallerGroups.get(p));
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
