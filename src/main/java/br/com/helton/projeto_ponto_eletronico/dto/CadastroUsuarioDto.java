package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Role;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroUsuarioDto(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotBlank String senha,
        @NotNull Role role,
        @NotNull TipoJornada tipoJornada
) {
}
