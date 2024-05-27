package br.com.everaldoboscatto.LiterAlura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Recebe os dados vindos da API
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias({"title"}) String titulo,
                         @JsonAlias({"authors"}) List<DadosAutor> autores,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("download_count") Double numeroDownloads) {
}