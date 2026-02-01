package com.bnp.books;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BooksPriceCalculatorTest {

	@Test
	void should_return_zero_when_basket_is_empty()
	{
		List<Books> books = Collections.emptyList();
		BookPriceCalculator priceCalculator = new  BookPriceCalculator();
		
		priceCalculator.caluclatePriceFor(books);
	}
}
