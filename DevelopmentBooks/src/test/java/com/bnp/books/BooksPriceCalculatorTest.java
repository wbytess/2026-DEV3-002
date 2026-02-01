package com.bnp.books;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BooksPriceCalculatorTest {
	BookPriceCalculator priceCalculator;

	@BeforeEach
	void setUp() {
		priceCalculator = new BookPriceCalculator();
	}
	
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_zero_when_basket_is_empty() {
		List<Book> books = Collections.emptyList();

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(0);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_base_price_for_single_book() {
		List<Book> books = Collections.singletonList(new Book("CLEAN_CODE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(50);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_sum_of_price_for_2_similar_books() {
		List<Book> books = List.of(new Book("CLEAN_CODE", 50), new Book("CLEAN_CODE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(100.0);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_price_with_5_percent_discount_for_2_differnt_books() {
		List<Book> books = List.of(new Book("CLEAN_CODE", 50), new Book("CLEAN_CODER", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(95.0);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_price_with_10_percent_discount_for_3_differnt_books() {
		List<Book> books = List.of(new Book("CLEAN_CODE", 50), new Book("CLEAN_CODER", 50),
				new Book("CLEAN_ARCHITECTURE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(135);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_price_with_20_percent_discount_for_4_differnt_books() {
		List<Book> books = List.of(new Book("CLEAN_CODE", 50), new Book("CLEAN_CODER", 50),
				new Book("CLEAN_ARCHITECTURE", 50), new Book("TDD_BY_EXAMPLE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(160);
	}
	@Disabled("Temporarily disabled due to refactor")
	@Test
	void should_return_price_with_25_percent_discount_for_5_differnt_books() {
		List<Book> books = List.of(new Book("CLEAN_CODE", 50), new Book("CLEAN_CODER", 50),
				new Book("CLEAN_ARCHITECTURE", 50), new Book("TDD_BY_EXAMPLE", 50), new Book("LEGACY_CODE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(187.50);
	}
	
	@Disabled
	@Test
	void should_return_price_with_best_price_for_differnt_and_similar_books() {
		List<Book> books = List.of(
				new Book("CLEAN_CODE", 50), new Book("CLEAN_CODE", 50), 
				new Book("CLEAN_CODER", 50), new Book("CLEAN_CODER", 50),
				new Book("CLEAN_ARCHITECTURE", 50), new Book("CLEAN_ARCHITECTURE", 50), 
				new Book("TDD_BY_EXAMPLE", 50), 
				new Book("LEGACY_CODE", 50));

		assertThat(priceCalculator.caluclatePriceFor(books)).isEqualTo(320);
	}
	
	@Test
	void should_create_multiple_groups_for_list_containing_similar_books()
	{
		List<Book> books = List.of(
				new Book("CLEAN_CODE", 50), new Book("CLEAN_CODE", 50), 
				new Book("CLEAN_CODER", 50), new Book("CLEAN_CODER", 50),
				new Book("CLEAN_ARCHITECTURE", 50), new Book("CLEAN_ARCHITECTURE", 50), 
				new Book("TDD_BY_EXAMPLE", 50), 
				new Book("LEGACY_CODE", 50));
		
		
		List<List<Book>> groups = priceCalculator.reArrangeBooksListToCalculateBestPrice(books);
		
		
		assertThat(groups).hasSize(2);

		assertThat(groups.get(0)).extracting(Book::name).contains("CLEAN_CODE", "CLEAN_CODER",
				"CLEAN_ARCHITECTURE", "TDD_BY_EXAMPLE");

		assertThat(groups.get(1)).extracting(Book::name).contains("CLEAN_CODE", "CLEAN_CODER",
				"CLEAN_ARCHITECTURE");

	}
	
}
