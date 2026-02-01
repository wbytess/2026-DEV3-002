package com.bnp.books;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BooksPriceCalculatorTest {

	@Test
	void should_return_zero_when_basket_is_empty() {
		List<Book> books = Collections.emptyList();
		BookPriceCalculator priceCalculator = new BookPriceCalculator();

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(0);
	}

	@Test
	void should_return_50_price_for_single_book() {
		List<Book> books = Collections.singletonList(new Book("CLEAN_CODE"));
		
		BookPriceCalculator priceCalculator = new  BookPriceCalculator();
		
		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(50);
	}
}
