package curso.api.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import curso.api.rest.model.FotoBiografia;
import curso.api.rest.repository.FotoRepository;
import net.coobird.thumbnailator.Thumbnails;

@Controller
public class BiografiaController {

    @Autowired
    private FotoRepository fotoRepository;

    @GetMapping("/biografia")
    public String biografia(Model model) {
    	  model.addAttribute("fotos", fotoRepository.findAll());
        return "biografia";
    }
    
    @PostMapping("/delete-foto/{fotoId}")
    public String deletarFoto(@PathVariable Long fotoId, Model model) {
        // Verifica se a foto com o ID especificado existe
        if (fotoRepository.existsById(fotoId)) {
            // Lógica para deletar a foto com o ID especificado
            fotoRepository.deleteById(fotoId);
        }

        // Obtém a lista atualizada de fotos para exibir na página
        model.addAttribute("fotos", fotoRepository.findAll());

        // Redireciona de volta para a página que exibe a lista de fotos
        return "redirect:/fotobiografia";
    }
    

    @GetMapping("/fotobiografia")
    public String fotobiografia(Model model) {
        // Obtém a lista de fotos da biografia para exibir na página
        model.addAttribute("fotos", fotoRepository.findAll());
        return "fotobiografia";
    }

    @PostMapping("/upload")
    public String upload(FotoBiografia fotoBiografia, @RequestParam("foto") MultipartFile imagem, RedirectAttributes redirectAttributes) {
        try {
            // Verifica se foi fornecida uma imagem
            if (!imagem.isEmpty()) {
                // Converte a imagem para base64
                String imagemBase64 = converterImagemBase64(imagem);

                // Configura a foto da biografia com a imagem em base64
                fotoBiografia.setImagemBase64(imagemBase64);

                // Gera e configura a miniatura
                String miniaturaBase64 = gerarMiniaturaBase64(imagem);
                fotoBiografia.setMiniaturaBase64(miniaturaBase64);

                // Salva a foto da biografia no banco de dados
                fotoRepository.save(fotoBiografia);

                // Adiciona uma mensagem de sucesso
                redirectAttributes.addFlashAttribute("mensagem", "Foto enviada com sucesso!");
            }

            // Redireciona para a página de lista de fotos da biografia
            return "redirect:/fotobiografia";
        } catch (IOException e) {
            e.printStackTrace();
            // Adiciona uma mensagem de erro
            redirectAttributes.addFlashAttribute("erro", "Erro ao enviar a foto.");
            // Redireciona para a página de lista de fotos da biografia
            return "redirect:/fotobiografia";
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
                .size(100, 100) // Tamanho desejado da miniatura (ajuste conforme necessário)
                .toOutputStream(outputStream);

        // Converte a miniatura para base64
        byte[] miniaturaBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(miniaturaBytes);
    }
}
