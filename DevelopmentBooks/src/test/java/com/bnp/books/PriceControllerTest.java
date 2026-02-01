package com.bnp.books;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

		mockMvc.perform(post("/api/v1/price").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk()).andExpect(content().string("50.0"));
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

		mockMvc.perform(post("/api/v1/price").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isOk()).andExpect(content().string("95.0"));
	}
}
