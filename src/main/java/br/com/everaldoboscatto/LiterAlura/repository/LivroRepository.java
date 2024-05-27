package br.com.everaldoboscatto.LiterAlura.repository;

import br.com.everaldoboscatto.LiterAlura.model.Autor;
import br.com.everaldoboscatto.LiterAlura.model.Idiomas;
import br.com.everaldoboscatto.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// Interage com o banco de dados, realiza operações na Entidade Livro
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);
    @Query("SELECT a FROM Autor a " +
            "WHERE a.anoDeNascimento <= :ano " +
            "AND (a.anoDeFalecimento IS NULL OR a.anoDeFalecimento >= :ano)")

    List<Autor> obterAutoresVivosEmAno(int ano);

    List<Livro> findByIdiomas(Idiomas idoma);
}