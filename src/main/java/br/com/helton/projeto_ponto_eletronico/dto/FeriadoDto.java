package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Feriado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FeriadoDto(
        @NotNull LocalDate data,
        @NotBlank String nome,
        @NotBlank String tipo
) {
    public FeriadoDto(Feriado feriado) {
        this(feriado.getData(), feriado.getNome(), feriado.getTipo());
    }
}