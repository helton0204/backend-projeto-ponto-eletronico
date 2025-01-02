package br.com.helton.projeto_ponto_eletronico.repository;

import br.com.helton.projeto_ponto_eletronico.domain.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeriadoRepository extends JpaRepository<Feriado, Long> {

    List<Feriado> findByDataBetween(LocalDate inicio, LocalDate fim);

    boolean existsByDataBetween(LocalDate startDate, LocalDate endDate);

}
