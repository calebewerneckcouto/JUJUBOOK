package curso.api.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.api.rest.model.FotoBiografia;

public interface FotoRepository extends JpaRepository<FotoBiografia, Long> {
    // Adicione métodos personalizados aqui, se necessário
}
