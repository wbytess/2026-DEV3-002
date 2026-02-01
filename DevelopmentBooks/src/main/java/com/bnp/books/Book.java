package com.bnp.books;

import java.util.Objects;

public record Book(String name, double price) {

	private static final double DEFAULT_PRICE = 50.0;

	public Book {
		if (price <= 0) {
			price = DEFAULT_PRICE;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(name, other.name);
	}
	
	
}
