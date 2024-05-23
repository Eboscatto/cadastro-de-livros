package br.com.everaldoboscatto.LiterAlura.principal;

import br.com.everaldoboscatto.LiterAlura.model.*;
import br.com.everaldoboscatto.LiterAlura.repository.LivroRepository;
import br.com.everaldoboscatto.LiterAlura.service.RequestAPI;
import br.com.everaldoboscatto.LiterAlura.service.ConvertData;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private RequestAPI consumoApi = new RequestAPI();
    private ConvertData conversor = new ConvertData();
    private final String BASE_URL = "https://gutendex.com/books/?search=";
    private LivroRepository repositorio;
    private String nomeDoLivro;
    private List<Livro> livros;

    public Principal(LivroRepository repositorio) {

        this.repositorio = repositorio;
    }

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
                    obterDadosLivro();
                    break;
                case 2:
                    listarLivrosArmazenados();
                    break;
                case 3:
                    listarAutoresArmazenados();
                    break;
                case 4:
                    //listarAutoresVivos();
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

    private String solicitarDados() {
        System.out.println("Digite o nome do livro para a busca");
        nomeDoLivro = leitura.nextLine();
        return nomeDoLivro;
    }

    private Dados buscarDadosAPI(String nomeDoLivro) {
        var json = consumoApi.obterDados(BASE_URL + nomeDoLivro.replace(" ", "+"));
        var dados = conversor.obterDados(json, Dados.class);
        System.out.println(dados);
        return dados;
    }

    private Optional<Livro> obterInfoLivro(Dados dadosLivro, String nomeLivro) {
        Optional<Livro> livros = dadosLivro.results().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nomeLivro.toLowerCase()))
                .map(l -> new Livro(l.titulo(), l.idiomas(), l.numeroDownloads(), l.autores()))
                .findFirst();
        return livros;
    }

    private Optional<Livro> obterDadosLivro() {
        String tituloLivro = solicitarDados();

        Dados infoLivro = buscarDadosAPI(tituloLivro);

        Optional<Livro> livro = obterInfoLivro(infoLivro, tituloLivro);

        if (livro.isPresent()) {
            var l = livro.get();
            repositorio.save(l);
            System.out.println(l);
        } else {
            System.out.println("\nLivro não encontrado!\n");
        }
        return livro;
    }

    private void listarLivrosArmazenados() {
        livros = repositorio.findAll();

        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresArmazenados() {
        List<Autor> autores = repositorio.obterDadosAutor();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(a -> System.out.printf("Autor: %s Nascido: %s Falecido: %s\n",
                        a.getNome(), a.getAnoDeNascimento(), a.getAnoDeFalecimento()));
    }

    private int solicitarAno() {
        System.out.println("Digite o ano para o qual deseja saber um autor vivo:");

        while (true) {
            try {
                int ano = leitura.nextInt();
                leitura.nextLine();
                return ano;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                leitura.nextLine();
            }
        }
    }

    private void listarAutoresVivos() {
        int ano = solicitarAno();

        if (ano < 0) {
            System.out.println("Ano inválido!");
            return;
        }

        List<Autor> autoresVivosEmAno = repositorio.obterAutoresVivosEmAno(ano);
        autoresVivosEmAno.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(this::exibirAutor);
    }

    private void exibirAutor(Autor autor) {
        System.out.printf("Autor: %s Nascido: %s Falecido: %s\n",
                autor.getNome(), autor.getAnoDeNascimento(), autor.getAnoDeFalecimento());
    }

    private void listarLivrosPorIdioma() {
        String idiomasList = """
                Escolha o idioma do livro que deseja buscar                
                en - Inglês
                es - Espanhol
                fr - Francês                
                pt - Português
                                
                """;
        System.out.println(idiomasList);
        System.out.println("Opção: ");
        String text = leitura.nextLine();

        var idoma = Idiomas.fromString(text);

        List<Livro> livroIdioma = repositorio.findByIdiomas(idoma);

        livroIdioma.stream()
                .forEach(System.out::println);

    }
}