package com.bnp.books;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

	private static final String JSON_PATH_EXPRESSION = "$.message";

	private static final String API_V1_PRICE_QUOTES = "/api/v1/books/price-quotes";
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("price of a single book should be 50")
	void priceOfSingleBookShouldBe50() throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 }
				]
				""";

		assertPrice(requestBody, "50.0");
	}

	@Test
	@DisplayName("two different books get 5 percent discount")
	void twoDifferentBooksGet5PercentDiscount() throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 },
				  { "name": "CLEAN_CODER", "price": 50 }
				]
				""";

		assertPrice(requestBody, "95.0");
	}
	
	@Test
	@DisplayName("three different books get 10 percent discount")
	void threeDifferentBooksGet10PercentDiscount() throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 },
				  { "name": "CLEAN_CODER", "price": 50 },
				  { "name": "CLEAN_ARCHITECTURE", "price": 50 }
				]
				""";

		assertPrice(requestBody, "135.0");
	}
	
	@Test
	@DisplayName("four different books get 20 percent discount")
	void fourDifferentBooksGet20PercentDiscount() throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 },
				  { "name": "CLEAN_CODER", "price": 50 },
				  { "name": "CLEAN_ARCHITECTURE", "price": 50 },
				  { "name": "TDD_BY_EXAMPLE", "price": 50 }
				]
				""";

		assertPrice(requestBody, "160.0");
	}
	
	@Test
	@DisplayName("five different books get 25 percent discount")
	void fiveDifferentBooksGet25PercentDiscount()throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 },
				  { "name": "CLEAN_CODER", "price": 50 },
				  { "name": "CLEAN_ARCHITECTURE", "price": 50 },
				  { "name": "TDD_BY_EXAMPLE", "price": 50 },
				  { "name": "LEGACY_CODE", "price": 50 }
				]
				""";

		assertPrice(requestBody, "187.5");
	}
	
	@Test
	@DisplayName("assignment example basket should cost 320")
    void assignmentExample() throws Exception {

		String requestBody = """
				[
				  { "name": "CLEAN_CODE", "price": 50 },{ "name": "CLEAN_CODE", "price": 50 },
				  { "name": "CLEAN_CODER", "price": 50 },{ "name": "CLEAN_CODER", "price": 50 },
				  { "name": "CLEAN_ARCHITECTURE", "price": 50 },{ "name": "CLEAN_ARCHITECTURE", "price": 50 },
				  { "name": "TDD_BY_EXAMPLE", "price": 50 },
				  { "name": "LEGACY_CODE", "price": 50 }
				]
				""";

		assertPrice(requestBody, "320.0");
	}
	
	@Test
	@DisplayName("empty book list should return 400")
	void emptyBookListReturnsBadRequest() throws Exception {
	    String invalidBookRequest = "[]";
	    
	    assertErrorMessage(invalidBookRequest, JSON_PATH_EXPRESSION, "Book list cannot be empty");
	}

	@Test
    @DisplayName("should return 400 BAD REQUEST when book has null price")
    void shouldReturnBadRequestForBookWithNullPrice() throws Exception {

        String invalidBookRequest = """
                [
                    { "name": "CLEAN_CODE", "price": null }
                ]
                """;

        assertErrorMessage(invalidBookRequest, JSON_PATH_EXPRESSION, "Book price must not be null: CLEAN_CODE");
    }
	
	@Test
    @DisplayName("should return 400 BAD REQUEST when book name is null")
    void shouldReturnBadRequestForBookWithNullName() throws Exception {

        String invalidBookRequest = """
                [
                    { "name": null, "price": 50 }
                ]
                """;
        
        assertErrorMessage(invalidBookRequest, JSON_PATH_EXPRESSION, "Book name must not be null or empty");
    }
	
	@Test
    @DisplayName("should return 400 BAD REQUEST when book price is equal to zero")
    void shouldReturnBadRequestForBookPriceEQtoZero() throws Exception {

        String invalidBookRequest = """
                [
                    { "name": "CLEAN_CODE", "price": 0 }
                ]
                """;

        assertErrorMessage(invalidBookRequest, JSON_PATH_EXPRESSION, "Book price must be greater than zero for book: CLEAN_CODE");
    }

	private void assertErrorMessage(String invalidBookRequest, String pathExpression, String message) throws Exception {
		mockMvc.perform(post(API_V1_PRICE_QUOTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBookRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(pathExpression).value(message));
	}
	
	private void assertPrice(String requestBody, String price) throws Exception {
		mockMvc.perform(post(API_V1_PRICE_QUOTES).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk()).andExpect(content().string(price));
	}
}
