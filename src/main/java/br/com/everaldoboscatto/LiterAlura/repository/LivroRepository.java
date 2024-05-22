package br.com.everaldoboscatto.LiterAlura.repository;

import br.com.everaldoboscatto.LiterAlura.model.Autor;
import br.com.everaldoboscatto.LiterAlura.model.Idiomas;
import br.com.everaldoboscatto.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    @Query("SELECT a FROM Livro b JOIN b.autores a")
    List<Autor> obterDadosAutor();

    @Query("SELECT a FROM Livro b JOIN b.autores a WHERE anoDeNascimento >= :date")
    List<Autor> obterDadosAutorLiveAfter(Integer date);

    List<Livro> findByIdiomas(Idiomas idomas);
}