package br.com.helton.projeto_ponto_eletronico.repository;

import br.com.helton.projeto_ponto_eletronico.domain.Ponto;
import br.com.helton.projeto_ponto_eletronico.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Long> {

    Optional<Ponto> findByUsuarioAndData(Usuario usuario, LocalDate data);

    List<Ponto> findByUsuarioAndDataBetween(Usuario usuario, LocalDate inicio, LocalDate fim);

    @Query("SELECT p FROM Ponto p WHERE p.usuario = :usuario AND YEAR(p.data) = :ano AND MONTH(p.data) = :mes")
    List<Ponto> findByUsuarioAndAnoAndMes(@Param("usuario") Usuario usuario, @Param("ano") int ano, @Param("mes") int mes);
}
