package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Role;

public record UsuarioDto(String nome, String email, String login, String senha, Role role, Long jornadaId) {
}
