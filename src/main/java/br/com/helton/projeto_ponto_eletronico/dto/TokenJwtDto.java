package br.com.helton.projeto_ponto_eletronico.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenJwtDto(
        @NotBlank String token) {
}
