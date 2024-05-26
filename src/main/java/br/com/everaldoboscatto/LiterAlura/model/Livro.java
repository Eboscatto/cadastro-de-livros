package br.com.everaldoboscatto.LiterAlura.model;

import jakarta.persistence.*;

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
   @ManyToOne
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;
    private Integer numeroDownloads;

    public Livro(){
    }


    public Livro(DadosLivro dados, Autor autor) {
        this.titulo = dados.titulo();
        this.autor = autor;
        this.idiomas = Idiomas.fromString(dados.idiomas().get(0));
        this.numeroDownloads = dados.numeroDownloads();
    }

    public Livro(List<DadosLivro> results) {
        for (DadosLivro dados : results) {
            Autor autor = new Autor(dados.autores().get(0).nome(), dados.autores().get(0).anoDeNascimento(), dados.autores().get(0).anoDeFalecimento());
            Livro livro = new Livro(dados, autor);
            // Adicione o livro à lista de livros do autor
            autor.getLivros().add(livro);
        }
    }

    public Livro(String tiulo, List<String> idiomas, Integer numeroDownloads,  List<DadosAutor> autores) {
        this.titulo = tiulo;
        this.idiomas = Idiomas.fromString(idiomas.get(0));
        this.numeroDownloads = numeroDownloads;
        Autor autor = new Autor(autores.get(0).nome(), autores.get(0).anoDeNascimento(), autores.get(0).anoDeFalecimento());
        this.autor = autor;

        }

    public Livro(Livro dados, Autor autor) {
        this.titulo = dados.titulo;
        this.autor = autor;
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

    public Autor getAutor() {
        return autor;
    }


    public void setAutor(Autor autor) {
        this.autor = autor;
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
                ", autores=" + autor +
                ", idioma='" + idiomas + '\'' +
                ", numeroDownloads=" + numeroDownloads;
    }

}