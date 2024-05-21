package br.com.everaldoboscatto.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(String name, int birth_year, int death_year) {
}