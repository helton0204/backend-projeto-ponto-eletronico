package br.com.helton.projeto_ponto_eletronico.controller;

import br.com.helton.projeto_ponto_eletronico.dto.CadastroUsuarioDto;
import br.com.helton.projeto_ponto_eletronico.dto.RetornoUsuarioDto;
import br.com.helton.projeto_ponto_eletronico.dto.UsuarioParaEdicaoDto;
import br.com.helton.projeto_ponto_eletronico.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<RetornoUsuarioDto> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDto usuarioDto) {
        RetornoUsuarioDto novoUsuario = usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<RetornoUsuarioDto>> listarUsuarios() {
        List<RetornoUsuarioDto> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/logado")
    public RetornoUsuarioDto listarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioService.listarUsuarioLogado(authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RetornoUsuarioDto> listarUsuarioPorId(@PathVariable Long id) {
        RetornoUsuarioDto usuario = usuarioService.listarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar/{id}")
    public ResponseEntity<RetornoUsuarioDto> editarUsuario(@PathVariable Long id, @RequestBody UsuarioParaEdicaoDto usuarioDto) {
        RetornoUsuarioDto usuarioAtualizado = usuarioService.editarUsuario(id, usuarioDto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
