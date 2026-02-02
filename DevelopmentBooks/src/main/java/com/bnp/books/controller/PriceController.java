package com.bnp.books.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.books.domain.model.Book;
import com.bnp.books.service.PriceCalculatorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class PriceController {

	private final PriceCalculatorService priceCalculatorService;

	public PriceController(PriceCalculatorService priceCalculatorService) {
		this.priceCalculatorService = priceCalculatorService;
	}

	@PostMapping("/price-quotes")
	public ResponseEntity<Double> calculatePrice(@Valid @RequestBody List<Book> books) {
		return ResponseEntity.ok(priceCalculatorService.caluclatePriceForBooks(books));
	}
}
