package br.com.helton.projeto_ponto_eletronico.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank String login,
        @NotBlank String senha
) { }
