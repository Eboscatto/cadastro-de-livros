package br.com.everaldoboscatto.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tb_livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;

    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;
    private Integer numeroDownloads;

    public Livro(){

    }

    public Livro(DadosLivro dados) {

    }
    public Livro(List<DadosLivro> results) {

    }

    public Livro(String tiulo, List<String> idiomas, Integer numeroDownloads,  List<DadosAutor> autores) {
        this.titulo = tiulo;
        this.idiomas = Idiomas.fromString(idiomas.get(0));
        this.numeroDownloads = numeroDownloads;
        this.autores = new ArrayList<>();
        for (DadosAutor dadosAutor : autores) {
            Autor autor = new Autor(dadosAutor.nome(), dadosAutor.anoDeNascimento(), dadosAutor.anoDeFalecimento(), this);
            this.autores.add(autor);

        }
    }

    public Livro(Livro dados, Autor autor) {
        this.titulo = dados.titulo;
        setAutores(autores);
        this.idiomas = dados.idiomas;
        this.numeroDownloads = dados.numeroDownloads;
    }

    public Livro(Dados dados) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setLivro(this));
        this.autores = autores;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdioma(Idiomas idioma) {
        this.idiomas = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idioma='" + idiomas + '\'' +
                ", numeroDownloads=" + numeroDownloads;
    }
}