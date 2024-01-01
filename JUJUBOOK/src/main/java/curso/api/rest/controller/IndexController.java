package curso.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import curso.api.rest.model.Livro;
import curso.api.rest.repository.LivroRepository;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private LivroRepository livroRepository;
    
    
    
    @GetMapping("/")
    public String index(Model model) {
    	 // Obtém a lista de livros para exibir na página
        model.addAttribute("livros", livroRepository.findAll());
    	return "index";
    }
    

   
}
