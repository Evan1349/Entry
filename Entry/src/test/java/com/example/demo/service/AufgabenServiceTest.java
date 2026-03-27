package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.repository.AufgabenRepository;



import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.entities.Aufgaben;
import com.example.demo.enumerations.Tag;

@ExtendWith(MockitoExtension.class)
class AufgabenServiceTest {
	
	@Mock
	private AufgabenRepository aufgabenRepository;
	
	private AufgabenService aufgabenService;
	
	@Captor
	private ArgumentCaptor<Aufgaben> aufgabenCaptor;

	@BeforeEach
	void setUp() {
		aufgabenService = new AufgabenService(aufgabenRepository);
	}
	
	@Test
	void createAufgaben_shouldReturnSavedResponse() {
		
		AufgabenRequest request = AufgabenRequest.builder()
				.tag(Tag.MONTAG)
				.aufgaben("API testen")
				.stunden(new BigDecimal("2.5"))
				.abteilung("IT")
				.build();
		
		Aufgaben savedEntity = Aufgaben.builder()
				.id(1L)
				.tag(Tag.MONTAG)
				.aufgaben("API testen")
				.stunden(new BigDecimal("2.5"))
				.abteilung("IT")
				.build();
		
		when(aufgabenRepository.save(any(Aufgaben.class))).thenReturn(savedEntity);
		
		AufgabenResponse response = aufgabenService.createAufgaben(request);

		verify(aufgabenRepository).save(aufgabenCaptor.capture());
		Aufgaben captured = aufgabenCaptor.getValue();
		
		assertEquals(1L, response.getId());
		assertEquals("API testen", captured.getAufgaben());
		assertEquals("IT", captured.getAbteilung());
		assertEquals(Tag.MONTAG, captured.getTag());
		assertEquals(new BigDecimal("2.5"), captured.getStunden());
	}
	
	
	
	
	@Test
	void toResponse_shouldMapAllFields() {
		Aufgaben aufgaben = Aufgaben.builder()
				.id(1L)
				.tag(Tag.MONTAG)
				.aufgaben("API testen")
				.stunden(new BigDecimal("2.5"))
				.abteilung("IT")
				.build();

		AufgabenResponse response = aufgabenService.toResponse(aufgaben);

		assertEquals(1L, response.getId());
		assertEquals(Tag.MONTAG, response.getTag());
		assertEquals("API testen", response.getAufgaben());
		assertEquals(new BigDecimal("2.5"), response.getStunden());
		assertEquals("IT", response.getAbteilung());
	}
}
