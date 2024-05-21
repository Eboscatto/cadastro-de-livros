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
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<DadosLivro> results;
    private List<DadosLivro> dadosLivros = new ArrayList<>();

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
           System.out.println("Digite o nome do livro que deseja buscar:");
           var nomeLivro = leitura.nextLine();
           var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
           System.out.println(json);
           Dados dados = conversor.obterDados(json, Dados.class);
           System.out.println(dados);

           json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
           DadosAutor autor = conversor.obterDados(json, DadosAutor.class);
           System.out.println(json);
           System.out.println(autor);

       }

    private void listarLivrosArmazenados() {
        System.out.println("Mostrar aqui todos os livros armazenados no repositório.");
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

    
