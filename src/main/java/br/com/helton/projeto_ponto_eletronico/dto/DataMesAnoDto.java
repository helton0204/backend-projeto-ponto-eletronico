package br.com.helton.projeto_ponto_eletronico.dto;

import jakarta.validation.constraints.NotNull;

public record DataMesAnoDto(
        @NotNull int mes,
        @NotNull int ano
) {
}
