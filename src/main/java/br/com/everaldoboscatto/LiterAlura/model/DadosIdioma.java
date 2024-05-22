package br.com.everaldoboscatto.LiterAlura.model;

public enum DadosIdioma {

    PORTUGUESE("pt"),
    ESPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr");
    private String idiomasGutendex;

    DadosIdioma(String idiomasGutendex) {

        this.idiomasGutendex = idiomasGutendex;
    }
    public static DadosIdioma fromString(String text) {
        for (DadosIdioma categoria : DadosIdioma.values()) {
            if (categoria.idiomasGutendex.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhum encontrado para a string fornecida: " + text);
    }
}
