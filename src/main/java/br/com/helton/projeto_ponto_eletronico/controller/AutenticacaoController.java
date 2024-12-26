package br.com.helton.projeto_ponto_eletronico.controller;

import br.com.helton.projeto_ponto_eletronico.domain.Usuario;
import br.com.helton.projeto_ponto_eletronico.dto.LoginDto;
import br.com.helton.projeto_ponto_eletronico.dto.TokenJwtDto;
import br.com.helton.projeto_ponto_eletronico.infra.security.TokenService;
import br.com.helton.projeto_ponto_eletronico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<TokenJwtDto> efetuarLogin(@RequestBody @Valid LoginDto dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authentication = authenticationManager.authenticate(authenticationToken);

            var tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new TokenJwtDto(tokenJwt));
        }
        catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/nova-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody @Valid LoginDto loginDto) {
        var usuarioOptional = usuarioRepository.findByLogin(loginDto.login());

        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuário com login " + loginDto.login() + " não encontrado.");
        }

        var usuario = usuarioOptional.get();

        String novaSenha = passwordEncoder.encode(loginDto.senha());
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

}
