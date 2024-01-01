package curso.api.rest.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.coobird.thumbnailator.Thumbnails;

@Entity
@Table(name = "livros") // Especifica o nome da tabela no banco de dados
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    @Column(length = 1000) // Especifica o tamanho máximo do campo descricao
    private String descricao;
    private double valor;
    @Column(length = 10000) // Especifica o tamanho máximo do campo descricao
    private String resenha;

    @Lob
    private String imagemBase64;

    @Lob
    private String miniaturaBase64;

    // Construtores

    public Livro() {
    }

    public Livro(String titulo, String descricao, double valor, String imagemBase64) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.imagemBase64 = imagemBase64;
        this.miniaturaBase64 = gerarMiniaturaBase64(imagemBase64);
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }
    
    
    

    public String getResenha() {
		return resenha;
	}

	public void setResenha(String resenha) {
		this.resenha = resenha;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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