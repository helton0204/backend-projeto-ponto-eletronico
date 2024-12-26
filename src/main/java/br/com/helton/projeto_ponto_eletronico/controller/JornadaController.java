package br.com.helton.projeto_ponto_eletronico.controller;

import br.com.helton.projeto_ponto_eletronico.dto.JornadaDto;
import br.com.helton.projeto_ponto_eletronico.service.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jornada")
public class JornadaController {

    @Autowired
    JornadaService jornadaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<JornadaDto> criarJornada(@RequestBody JornadaDto jornadaDto) {
        JornadaDto novaJornada = jornadaService.criarJornada(jornadaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaJornada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<JornadaDto>> listarJornadas() {
        List<JornadaDto> jornadas = jornadaService.listarJornadas();
        return ResponseEntity.ok(jornadas);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<JornadaDto> editarJornada(@PathVariable Long id, @RequestBody JornadaDto jornada) {
        JornadaDto jornadaAtualizada = jornadaService.editarJornada(id, jornada);
        return ResponseEntity.ok(jornadaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirJornada(@PathVariable Long id) {
        jornadaService.excluirJornada(id);
        return ResponseEntity.noContent().build();
    }

}
