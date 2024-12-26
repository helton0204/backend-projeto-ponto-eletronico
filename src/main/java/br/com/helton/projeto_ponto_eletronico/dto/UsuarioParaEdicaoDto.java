package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Role;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;

public record UsuarioParaEdicaoDto(
        String nome,
        String email,
        String login,
        String senha,
        Role role,
        TipoJornada tipoJornada
) {
}
