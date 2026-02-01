package com.bnp.books.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.books.domain.model.Book;
import com.bnp.books.service.PriceCalculatorService;

@RestController
@RequestMapping("/api")
public class PriceController {
    
    private final PriceCalculatorService priceCalculatorService;

    public PriceController(PriceCalculatorService priceCalculatorService) {
        this.priceCalculatorService = priceCalculatorService;
    }

	@GetMapping("/v1/price")
	public ResponseEntity<Double> calculatePrice(@RequestBody List<Book> books) {
		Double price = priceCalculatorService.caluclatePriceForBooks(books);
		return ResponseEntity.ok(price);
	}
}
