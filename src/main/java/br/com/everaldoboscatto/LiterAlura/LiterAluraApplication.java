package br.com.everaldoboscatto.LiterAlura;

import br.com.everaldoboscatto.LiterAlura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
    //private LivroRepository repositorio;
	public static void main(String[] args) {
			SpringApplication.run(LiterAluraApplication.class, args);
		
	}
    @Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		//Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}