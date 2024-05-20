package br.com.everaldoboscatto.LiterAlura.service;

public interface IConvertData {

    // Receber um Json com um livro e retornar um DadosLivro
    <T> T  obterDados(String json, Class<T> classe);
}
