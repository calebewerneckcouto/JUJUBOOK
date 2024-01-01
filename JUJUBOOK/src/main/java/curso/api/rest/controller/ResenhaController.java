package curso.api.rest.controller;

import java.awt.PageAttributes.MediaType;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.api.rest.model.Livro;
import curso.api.rest.repository.LivroRepository;

@Controller
public class ResenhaController {
	@Autowired
	private LivroRepository livroRepository;
	
	
	@GetMapping("/resenha")
	public String resenha() {
		return "resenha";
	}
	
	
	

	@GetMapping("/resenha.html")
	public String exibirResenha(@RequestParam Long livroId, Model model) {
	    // Lógica para carregar a resenha do livro do banco de dados com base no livroId
	    Optional<Livro> livroOptional = livroRepository.findById(livroId);

	    if (livroOptional.isPresent()) {
	        Livro livro = livroOptional.get();
	        model.addAttribute("livro", livro);
	        return "resenha"; // Nome do arquivo HTML da página de resenha
	    } else {
	        return "livroNaoEncontrado"; // Página de tratamento para livro não encontrado (crie conforme necessário)
	    }
	}
}

	


