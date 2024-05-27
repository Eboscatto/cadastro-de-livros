package br.com.everaldoboscatto.LiterAlura.principal;

import br.com.everaldoboscatto.LiterAlura.model.*;
import br.com.everaldoboscatto.LiterAlura.repository.AutorRepository;
import br.com.everaldoboscatto.LiterAlura.repository.LivroRepository;
import br.com.everaldoboscatto.LiterAlura.service.RequestAPI;
import br.com.everaldoboscatto.LiterAlura.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private RequestAPI consumoApi = new RequestAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String BASE_URL = "https://gutendex.com/books/?search=";
    private LivroRepository repositorio;
    private AutorRepository autorRepository;
    private String nomeDoLivro;
    private List<Livro> livros;
    private AutorRepository livroRepository;

    // Injeta as dependêncicas no construtor da classe Principal
    public Principal(AutorRepository autorRepository, LivroRepository repositorio) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
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
                    06 - Listar os três livros mais baixados    
                    07 - Listar autor pelo nome                  
                                      
                    """;

            System.out.println("\n\n" + menu);
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
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    listarTop3Downloads();
                    break;
                case 7:
                    listarAutorPeloNome();
                    break;
                case 0:
                    System.out.println("\nEncerrando sistema...");
                    break;
                default:
                    System.out.println("\nOpçao inválida!");
            }
        }
    }

    // Solicita a entrada de dados pelo usuário
    private String solicitarDados() {
        System.out.println("Digite o nome do livro para a busca");
        nomeDoLivro = leitura.nextLine();
        return nomeDoLivro;
    }
    // Busca o livro na API
    private Dados buscarDadosAPI(String nomeDoLivro) {
        var json = consumoApi.obterDados(BASE_URL + nomeDoLivro.replace(" ", "+"));
        var dados = conversor.obterDados(json, Dados.class);
        System.out.println("\n" + dados);
        return dados;
    }

    // Filtra os dados de um determinado livro
    private Optional<Livro> obterInfoLivro(Dados dadosLivro, String nomeLivro) {
        Optional<Livro> livros = dadosLivro.results().stream()
                .filter(l -> l.titulo()
                        .toLowerCase()
                        .contains(nomeLivro.toLowerCase()))
                .map(b -> new Livro(b.titulo(),
                        b.idiomas(),
                        b.numeroDownloads(),
                        b.autores()))
                .findFirst();
        return livros;
    }

    // Salva os dados no banco de dados
    private Optional<Livro> obterDadosLivro() {
        String tituloLivro = solicitarDados();

        Dados infoLivro = buscarDadosAPI(tituloLivro);

        Optional<Livro> livro = obterInfoLivro(infoLivro, tituloLivro);

        // verifica se o livro já existe no banco de dados
        if (livro.isPresent()) {
            Livro l = livro.get();
            Autor autor = l.getAutor();
            System.out.println("\nLivro já existe no banco de dados!");

            // Verifica se o autor já existe no banco de dados
            Optional<Autor> autorExistente = autorRepository.findByNome(autor.getNome());
            if (autorExistente.isEmpty()) {
                // Salva o autor antes de salvar o livro
                autorRepository.save(autor);
            } else {
                // Atualiza o autor do livro com o autor existente
                l.setAutor(autorExistente.get());
            }
            // Salva o livro
            repositorio.save(l);

            System.out.println("\nLivro Encontrado:");
            System.out.println(l);
        } else {
            System.out.println("\nLivro não encontrado!\n");
        }
        return livro;
    }

    // Lista livros ordenados por título
    private void listarLivrosArmazenados() {
        livros = repositorio.findAll();
        System.out.println();

        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    // Constrututor da classe principal
    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.repositorio = livroRepository;
        this.autorRepository = autorRepository;
    }
    // Listar os 3 livros com maior número de downloads
    private void listarTop3Downloads() {
        List<Livro> livros = repositorio.findAll();
        livros.stream()
                .sorted(Comparator.comparingDouble(Livro::getNumeroDownloads).reversed())
                .limit(3)
                .forEach(l -> System.out.printf("\nTítulo: %s - Downloads: %.0f", l.getTitulo(), l.getNumeroDownloads()));
    }

    // Lista autores armazenados ordenados por nome
    private void listarAutoresArmazenados() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(a -> System.out.printf("\nAutor: %s Nascido: %s - Falecido: %s",
                        a.getNome(),
                        a.getAnoDeNascimento(),
                        a.getAnoDeFalecimento()));
    }


    // Busca autor por um trecho do nome
    private void listarAutorPeloNome() {
        System.out.println("Digite um trecho do nome do episódio que deseja buscar");
        var trechoNomeAutor = leitura.nextLine();
        List<Autor> autorEncontrado = autorRepository.autorPorTrechoDoNome(trechoNomeAutor);
        autorEncontrado.forEach(a ->
                System.out.printf("\nAutor encontrado: %s Autor",
                        a.getNome()));

        if (autorEncontrado.isEmpty()) {
            System.out.println("\nNão foi localizado nenhum autor com esse nome!");
        }
    }
    // Solicita a entrada de dados pelo usuário
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

    // Busca autor(es) vivo(os) em um determinado ano
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

    // Lista autor(es) vivo(os) em um determinado ano
    private void exibirAutor(Autor autor) {
        System.out.printf("\nAutor: %s Nascido: %s - Falecido: %s",
                autor.getNome(),
                autor.getAnoDeNascimento(),
                autor.getAnoDeFalecimento());
    }

    // Lista livro(os) do idioma escolhido
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

        System.out.println();

        livroIdioma.stream()
                .forEach(System.out::println);
    }
}