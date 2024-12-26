package br.com.helton.projeto_ponto_eletronico.controller;

import br.com.helton.projeto_ponto_eletronico.dto.DataMesAnoDto;
import br.com.helton.projeto_ponto_eletronico.dto.PontoDto;
import br.com.helton.projeto_ponto_eletronico.dto.RegistroPontoDto;
import br.com.helton.projeto_ponto_eletronico.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoController {

    @Autowired
    private PontoService pontoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<PontoDto> registrarPonto(@RequestBody RegistroPontoDto registroPontoDto, @AuthenticationPrincipal UserDetails userDetails) {
        PontoDto pontoRegistrado = pontoService.registrarPonto(registroPontoDto, userDetails.getUsername());
        return ResponseEntity.ok(pontoRegistrado);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<PontoDto>> listarPontosDoMesAtual(@AuthenticationPrincipal UserDetails userDetails) {
        List<PontoDto> pontos = pontoService.listarPontosDoMesAtual(userDetails.getUsername());
        return ResponseEntity.ok(pontos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/mes-especifico")
    public ResponseEntity<List<PontoDto>> listarPontosPorMes(@RequestBody DataMesAnoDto data, @AuthenticationPrincipal UserDetails userDetails) {
        List<PontoDto> pontos = pontoService.listarPontosPorMes(userDetails.getUsername(), data);
        return ResponseEntity.ok(pontos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @RequestMapping("/ponto-de-hoje")
    public ResponseEntity<PontoDto> consultarPontosDoDia(@AuthenticationPrincipal UserDetails userDetails) {
        PontoDto pontosDoDia = pontoService.consultarPontoDoDia(userDetails.getUsername());
        return ResponseEntity.ok(pontosDoDia);
    }


}