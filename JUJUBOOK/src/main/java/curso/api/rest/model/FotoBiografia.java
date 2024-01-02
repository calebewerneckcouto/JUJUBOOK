package curso.api.rest.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import net.coobird.thumbnailator.Thumbnails;

@Entity
public class FotoBiografia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imagemBase64;

    @Lob
    private String miniaturaBase64;
    
    
    
    
    
    
    public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public String getImagemBase64() {
		return imagemBase64;
	}






	public void setImagemBase64(String imagemBase64) {
		this.imagemBase64 = imagemBase64;
	}






	public String getMiniaturaBase64() {
		return miniaturaBase64;
	}






	public void setMiniaturaBase64(String miniaturaBase64) {
		this.miniaturaBase64 = miniaturaBase64;
	}






	private String gerarMiniaturaBase64(String imagemBase64) {
        try {
            // Convertendo a string base64 para um array de bytes
            byte[] imageBytes = java.util.Base64.getDecoder().decode(imagemBase64);

            // Criando um InputStream a partir do array de bytes
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

            // Redimensionando a imagem usando a biblioteca Thumbnails
            BufferedImage resizedImage = Thumbnails.of(inputStream)
                    .size(100, 100)  // Tamanho desejado da miniatura (ajuste conforme necessário)
                    .asBufferedImage();

            // Convertendo a imagem redimensionada para base64
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", outputStream);
            byte[] resizedImageBytes = outputStream.toByteArray();
            String miniaturaBase64 = java.util.Base64.getEncoder().encodeToString(resizedImageBytes);

            // Retornando a miniatura em base64
            return miniaturaBase64;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Lida com exceções apropriadas para o seu aplicativo
        }
    }

}
