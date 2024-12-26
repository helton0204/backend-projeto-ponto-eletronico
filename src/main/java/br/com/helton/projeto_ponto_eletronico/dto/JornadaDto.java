package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Jornada;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;
import jakarta.validation.constraints.NotNull;

public record JornadaDto(
        @NotNull TipoJornada tipoJornada
) {
    public JornadaDto(Jornada jornada){
        this(jornada.getTipoJornada());
    }
}
