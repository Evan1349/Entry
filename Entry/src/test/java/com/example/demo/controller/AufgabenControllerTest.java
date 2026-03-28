package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.enumerations.Tag;
import com.example.demo.service.AufgabenService;

@WebMvcTest(AufgabenController.class)
public class AufgabenControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private AufgabenService aufgabenService;
	
	@Test
	void create_shouldReturnCreated() throws Exception {
		
		String requestBody ="""
				{
				"tag" : "MONTAG",
				"aufgaben" : "API testen",
				"stunden" : 2.5,
				"abteilung" : "IT"
				}
		""";
		
		AufgabenResponse response = AufgabenResponse.builder()
				.id(1L)
				.tag(Tag.MONTAG)
				.aufgaben("API testen")
				.stunden(new BigDecimal("2.5"))
				.abteilung("IT")
				.build();
		
		when(aufgabenService.createAufgaben(org.mockito.ArgumentMatchers.any()))
		.thenReturn(response);
		
		mockMvc.perform(post("/api/aufgaben")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.tag").value("MONTAG"))
				.andExpect(jsonPath("$.aufgaben").value("API testen"))
				.andExpect(jsonPath("$.stunden").value(2.5))
				.andExpect(jsonPath("$.abteilung").value("IT"));
	}
	
	
	@Test
	void create_shouldReturnBadRequest_whenAufgabenIsBlank() throws Exception {
		String requestBody = """
				{
				  "tag": "MONTAG",
				  "aufgaben": "   ",
				  "stunden": 2.5,
				  "abteilung": "IT"
				}
				""";

		mockMvc.perform(post("/api/aufgaben")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.aufgaben").value("Der Inhalt darf nicht leer sein."));
	}

	
	@Test
	void create_shouldReturnBadRequestAndValidationMessage_whenAufgabenIsBlank() throws Exception {
		String requestBody = """
				{
				  "tag": "MONTAG",
				  "aufgaben": "   ",
				  "stunden": 2.5,
				  "abteilung": "IT"
				}
				""";

		mockMvc.perform(post("/api/aufgaben")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.aufgaben").value("Der Inhalt darf nicht leer sein."));
	}
}
