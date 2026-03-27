package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.entities.Aufgaben;
import com.example.demo.repository.AufgabenRepository;

import lombok.RequiredArgsConstructor;


/**
 * Diese Service-Klasse verarbeitet die Anwendungslogik fuer Aufgaben.
 * Sie erstellt neue Eintraege und wandelt Entitaeten in Response-DTOs um.
 */

@Service
@RequiredArgsConstructor
public class AufgabenService {
	
	private final AufgabenRepository aufgabenRepository;
		
	/**
	 * Erstellt einen neuen Aufgaben-Eintrag aus der validierten Anfrage.
	 * Vor dem Speichern werden Textfelder mit trim() bereinigt.
	 * Anschliessend wird der gespeicherte Datensatz als Response-DTO zurueckgegeben.
	 *
	 * @param request die vom Benutzer gesendeten Aufgabendaten
	 * @return der gespeicherte Aufgaben-Eintrag als Response-DTO
	 */
	
	@Transactional
	public AufgabenResponse createAufgaben(AufgabenRequest request) {
		
		Aufgaben aufgaben = Aufgaben.builder()
				.tag(request.getTag())
				.aufgaben(request.getAufgaben().trim())
				.stunden(request.getStunden())
				.abteilung(request.getAbteilung().trim())
				.build();
		Aufgaben saved = aufgabenRepository.save(aufgaben);
		return toResponse(saved);
	}
	
	/**
	 * Wandelt eine Aufgaben-Entitaet in ein Response-DTO fuer die API um.
	 *
	 * @param aufgaben die Aufgaben-Entitaet aus der Datenbank
	 * @return die API-Antwort mit den Aufgaben-Daten
	 */
	
	public AufgabenResponse toResponse(Aufgaben aufgaben) {
		return AufgabenResponse.builder()
				.id(aufgaben.getId())
				.tag(aufgaben.getTag())
				.aufgaben(aufgaben.getAufgaben())
				.stunden(aufgaben.getStunden())
				.abteilung(aufgaben.getAbteilung())
				.build();
		
	}
	
}
