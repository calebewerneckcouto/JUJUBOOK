package curso.api.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import curso.api.rest.model.Livro;
import curso.api.rest.repository.LivroRepository;
import net.coobird.thumbnailator.Thumbnails;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    
    
    @PostMapping("/delete/{livroId}")
    public String deletarLivro(@PathVariable Long livroId) {
        // Lógica para deletar o livro com o ID especificado
    	 livroRepository.deleteById(livroId);
    	livroRepository.findAll();

        // Redireciona de volta para a página que exibe a lista de livros
        return "redirect:/homecontrole";
    }
    
    
   
    
    
    @RequestMapping("/homecontrole")
    public String homecontrole(Model model) {
    	 model.addAttribute("livros", livroRepository.findAll());
        return "homecontrole";
    }

    @PostMapping("/salvarLivro")
    public String salvarLivro(Livro livro, @RequestParam("imagem") MultipartFile imagem, Model model) {
        try {
            // Verifica se foi fornecida uma imagem
            if (!imagem.isEmpty()) {
                // Converte a imagem para base64
                String imagemBase64 = converterImagemBase64(imagem);

                // Configura o livro com a imagem em base64
                livro.setImagemBase64(imagemBase64);

                // Gera e configura a miniatura
                String miniaturaBase64 = gerarMiniaturaBase64(imagem);
                livro.setMiniaturaBase64(miniaturaBase64);
            }

            // Salva o livro no banco de dados
            livroRepository.save(livro);

            // Obtém a lista de livros para exibir na página
            model.addAttribute("livros", livroRepository.findAll());

            // Redireciona para a página de lista de livros
            return "homecontrole";
        } catch (IOException e) {
            e.printStackTrace();
            // Lida com a exceção (por exemplo, exibir uma mensagem de erro)
            return "erro";
        }
    }

    // Método para converter a imagem em base64
    private String converterImagemBase64(MultipartFile imagem) throws IOException {
        byte[] bytes = imagem.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    // Método para gerar a miniatura da imagem em base64
    private String gerarMiniaturaBase64(MultipartFile imagem) throws IOException {
        // Converte a imagem para um array de bytes
        byte[] imageBytes = imagem.getBytes();

        // Redimensiona a imagem para uma miniatura usando a biblioteca Thumbnails
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(imageBytes))
                .size(100, 100)  // Tamanho desejado da miniatura (ajuste conforme necessário)
                .toOutputStream(outputStream);

        // Converte a miniatura para base64
        byte[] miniaturaBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(miniaturaBytes);
    }
}
