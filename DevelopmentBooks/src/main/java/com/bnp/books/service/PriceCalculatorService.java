package com.bnp.books.service;

import java.util.List;

import com.bnp.books.Book;

public interface PriceCalculatorService {
	double caluclatePriceForBooks(List<Book> books);
}
