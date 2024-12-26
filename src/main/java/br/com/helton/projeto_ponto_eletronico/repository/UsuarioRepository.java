package br.com.helton.projeto_ponto_eletronico.repository;

import br.com.helton.projeto_ponto_eletronico.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
