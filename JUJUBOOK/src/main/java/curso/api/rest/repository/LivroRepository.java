package curso.api.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.api.rest.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findAll();
    Optional<Livro> findById(Long id);
   
    
}
