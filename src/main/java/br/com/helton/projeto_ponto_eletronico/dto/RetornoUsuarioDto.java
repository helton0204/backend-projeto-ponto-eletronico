package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Role;
import br.com.helton.projeto_ponto_eletronico.domain.Usuario;

public record RetornoUsuarioDto(String nome, String email, Role role, Long jornadaId) {
    public RetornoUsuarioDto(Usuario usuario) {
        this(usuario.getNome(), usuario.getEmail(), usuario.getRole(), usuario.getJornada().getId());
    }
}
