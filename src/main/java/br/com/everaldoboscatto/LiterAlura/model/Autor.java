package br.com.everaldoboscatto.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;
    @ManyToOne
    private Livro livro;
    public Autor(){
    }
    public Autor(Autor autor){

    }
    public Autor(String nome, Integer anoDeNascimento, Integer anoDeFalecimento, Livro livro){
        this.nome = nome;
        this.anoDeNascimento = anoDeNascimento;
        this.anoDeFalecimento = anoDeFalecimento;
        this.livro = livro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {

        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {

        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {

        this.anoDeFalecimento = anoDeFalecimento;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' +
                ", dataDeNascimento=" + anoDeNascimento +
                ", dataDeFalecimento=" + anoDeFalecimento;
    }
}
