package br.com.helton.projeto_ponto_eletronico.service;

import br.com.helton.projeto_ponto_eletronico.domain.Jornada;
import br.com.helton.projeto_ponto_eletronico.domain.Usuario;
import br.com.helton.projeto_ponto_eletronico.dto.CadastroUsuarioDto;
import br.com.helton.projeto_ponto_eletronico.dto.RetornoUsuarioDto;
import br.com.helton.projeto_ponto_eletronico.dto.UsuarioParaEdicaoDto;
import br.com.helton.projeto_ponto_eletronico.infra.exception.UsuarioNaoEncontradoException;
import br.com.helton.projeto_ponto_eletronico.repository.JornadaRepository;
import br.com.helton.projeto_ponto_eletronico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JornadaRepository jornadaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public RetornoUsuarioDto cadastrarUsuario(CadastroUsuarioDto usuarioDto) {
        if (usuarioRepository.existsByEmail(usuarioDto.email())) {
            throw new IllegalArgumentException("Já existe um usuário com o mesmo email.");
        }
        if (usuarioRepository.existsByLogin(usuarioDto.login())) {
            throw new IllegalArgumentException("Já existe um usuário com o mesmo login.");
        }

        Jornada jornada = jornadaRepository.findByTipoJornada(usuarioDto.tipoJornada())
                .orElseThrow(() -> new IllegalArgumentException("Jornada não encontrada com o ID: " + usuarioDto.tipoJornada()));

        String senhaCriptografada = passwordEncoder.encode(usuarioDto.senha());

        Usuario usuario = new Usuario(usuarioDto, senhaCriptografada, jornada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        jornada.getUsuarios().add(usuario);
        return new RetornoUsuarioDto(usuarioSalvo);
    }

    public List<RetornoUsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new RetornoUsuarioDto(usuario))
                .collect(Collectors.toList());
    }

    public RetornoUsuarioDto listarUsuarioLogado(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new RetornoUsuarioDto(usuario);
    }

    public RetornoUsuarioDto listarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
        return new RetornoUsuarioDto(usuario);
    }

    @Transactional
    public RetornoUsuarioDto editarUsuario(Long id, UsuarioParaEdicaoDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));

        Jornada jornada = jornadaRepository.findByTipoJornada(usuarioDto.tipoJornada())
                .orElseThrow(() -> new EntityNotFoundException("Jornada não encontrada para o tipo: " + usuarioDto.tipoJornada()));

        atualizarDadosUsuario(usuario, usuarioDto, jornada);
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return new RetornoUsuarioDto(usuarioAtualizado);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private void atualizarDadosUsuario(Usuario usuario, UsuarioParaEdicaoDto usuarioDto, Jornada jornada) {
        if (usuarioDto.nome() != null) {
            usuario.setNome(usuarioDto.nome());
        }
        if (usuarioDto.email() != null) {
            usuario.setEmail(usuarioDto.email());
        }
        if (usuarioDto.login() != null) {
            usuario.setLogin(usuarioDto.login());
        }
        if (usuarioDto.senha() != null) {
            String senhaCriptografada = passwordEncoder.encode(usuarioDto.senha());
            usuario.setSenha(senhaCriptografada);
        }
        if (usuarioDto.role() != null) {
            usuario.setRole(usuarioDto.role());
        }
        if(usuarioDto.tipoJornada() != null) {
            usuario.getJornada().setTipoJornada(usuarioDto.tipoJornada());
        }
    }

}
