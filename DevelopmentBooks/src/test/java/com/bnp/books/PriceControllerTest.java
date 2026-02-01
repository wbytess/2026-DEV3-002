package com.bnp.books;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

	private static final String API_V1_PRICE = "/api/v1/price";
	
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
	    String requestBody = "[]";

	    mockMvc.perform(get("/api/v1/price")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(requestBody))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Book list cannot be empty"));
	}

	
	private void assertPrice(String requestBody, String price) throws Exception {
		mockMvc.perform(get(API_V1_PRICE).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk()).andExpect(content().string(price));
	}
}
