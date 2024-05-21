package br.com.everaldoboscatto.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias({"title"}) String titulo,
                          @JsonAlias({"authors"}) List<DadosAutor> atuores,
                          //@JsonAlias("languages") List<DadosIdioma> idioma,
                         @JsonAlias("download_count") Integer numeroDownloads) {
}