package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Jornada;
import br.com.helton.projeto_ponto_eletronico.domain.Role;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;
import br.com.helton.projeto_ponto_eletronico.domain.Usuario;

public record RetornoUsuarioDto(Long id, String nome, String email, Role role, TipoJornada tipoJornada) {
    public RetornoUsuarioDto(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole(), usuario.getJornada().getTipoJornada());
    }
}
