package br.com.everaldoboscatto.LiterAlura.principal;

import br.com.everaldoboscatto.LiterAlura.model.*;
import br.com.everaldoboscatto.LiterAlura.service.RequestAPI;
import br.com.everaldoboscatto.LiterAlura.service.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private RequestAPI consumoApi = new RequestAPI();
    private ConvertData conversor = new ConvertData();
    private final String BASE_URL = "https://gutendex.com/books/?search=";
    private List<Dados> livrosBuscados = new ArrayList<Dados>(); // Armazena os livros buscados

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """       
                    00 - Sair                
                    01 - Buscar livro pelo título
                    02 - Lista livros armazenados
                    03 - Listar autores armazenados 
                    04 - Listar autores vivos em um determinado ano 
                    05 - Listar livros em um determinado idioma                        
                                      
                    """;

            System.out.println("\n" + menu);
            System.out.println("Opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosArmazenados();
                    break;
                case 3:
                    listarAutoresArmazenados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;

                case 0:
                    System.out.println("\nEncerrando sistema...");
                    break;
                default:
                    System.out.println("\nOpçao inválida!");
            }
        }
    }

    private void buscarLivroWeb() {
        Dados dados = getDadosLivro();
        livrosBuscados.add(dados);
        System.out.println(dados);
    }

    private Dados getDadosLivro() {
        System.out.println("Digite o nome do livro que deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var json = consumoApi.obterDados(BASE_URL + nomeLivro.replace(" ", "+"));
        System.out.println(json);
        Dados dados = conversor.obterDados(json, Dados.class);
        return dados;
    }

    private void listarLivrosArmazenados() {
        livrosBuscados.forEach(System.out::println);
    }

    private void listarAutoresArmazenados() {
        System.out.println("Listar aqui livros armazendados.");
    }

    private void listarAutoresVivos() {
        System.out.println("Mostrar aqui os nomes dos autores.");
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Mostrar aqui os livros do idioma escolhido.");

    }
}

    
//Dados[results=[DadosLivro[titulo=Dom Casmurro, atuores=[DadosAutor[nome=Machado de Assis, anoDeNascimento=1839, anoDeFalecimento=1908]],
// numeroDownloads=915]]]


//Dados[results=[DadosLivro[titulo=Os Sinos: Poesia Narrativa, autores=[DadosAutor[nome=Proença, Raul Sangreman, anoDeNascimento=1884, anoDeFalecimento=1941]],
// idiomas=[pt], numeroDownloads=54]]]