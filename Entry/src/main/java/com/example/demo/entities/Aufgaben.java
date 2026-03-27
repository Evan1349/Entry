package com.example.demo.entities;


import java.math.BigDecimal;

import com.example.demo.enumerations.Tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="T_AUFGABEN")
public class Aufgaben {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Tag tag;
	
	@Column(columnDefinition ="TEXT")
	private String aufgaben;
	
    @DecimalMin(value = "0.1", message = "Mindestens 0,1 Stunden.")
    @Max(value = 10, message = "Maximal 10 Stunden pro Eintrag.")
	private BigDecimal stunden;
	
    @NotBlank(message = "Die Abteilung darf nicht leer sein.")
    @Size(max = 100, message = "Die Abteilung ist zu lang (max. 100 Zeichen).")
    private String abteilung;
	
}
