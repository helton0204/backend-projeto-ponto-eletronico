package br.com.helton.projeto_ponto_eletronico.repository;

import br.com.helton.projeto_ponto_eletronico.domain.Jornada;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    Optional<Jornada> findByTipoJornada(TipoJornada tipoJornada);

}