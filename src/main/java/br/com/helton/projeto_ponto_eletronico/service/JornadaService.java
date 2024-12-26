package br.com.helton.projeto_ponto_eletronico.service;

import br.com.helton.projeto_ponto_eletronico.domain.Jornada;
import br.com.helton.projeto_ponto_eletronico.dto.JornadaDto;
import br.com.helton.projeto_ponto_eletronico.repository.JornadaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    @Transactional
    public JornadaDto criarJornada(JornadaDto jornadaDto) {
        Jornada jornada = new Jornada(jornadaDto);
        Jornada jornadaSalva = jornadaRepository.save(jornada);
        return new JornadaDto(jornadaSalva);
    }

    public List<JornadaDto> listarJornadas() {
        List<Jornada> jornadas = jornadaRepository.findAll();
        return jornadas.stream().map(jornada -> new JornadaDto(jornada)).toList();
    }

    @Transactional
    public JornadaDto editarJornada(Long id, JornadaDto jornada) {
        Jornada jornadaExistente = jornadaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jornada não encontrada com o ID: " + id));
        jornadaExistente.setTipoJornada(jornada.tipoJornada());

        Jornada jornadaAtualizada = jornadaRepository.save(jornadaExistente);;
        return new JornadaDto(jornadaAtualizada);
    }

    @Transactional
    public void excluirJornada(Long id) {
        if (!jornadaRepository.existsById(id)) {
            throw new EntityNotFoundException("Jornada não encontrada com o ID: " + id);
        }
        jornadaRepository.deleteById(id);
    }
}
