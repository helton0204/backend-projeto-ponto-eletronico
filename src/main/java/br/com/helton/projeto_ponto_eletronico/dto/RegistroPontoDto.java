package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.TipoPonto;
import jakarta.validation.constraints.NotNull;


public record RegistroPontoDto(
        @NotNull TipoPonto tipoPonto
) { }
