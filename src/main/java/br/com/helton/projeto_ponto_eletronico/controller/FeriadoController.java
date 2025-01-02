package br.com.helton.projeto_ponto_eletronico.controller;

import br.com.helton.projeto_ponto_eletronico.dto.FeriadoDto;
import br.com.helton.projeto_ponto_eletronico.dto.PontoDto;
import br.com.helton.projeto_ponto_eletronico.dto.RegistroPontoDto;
import br.com.helton.projeto_ponto_eletronico.service.FeriadoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/feriados")
@SecurityRequirement(name = "bearer-key")
public class FeriadoController {

    @Autowired
    FeriadoService feriadoService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FeriadoDto> registrarFeriado(@RequestBody FeriadoDto feriadoDto) {
        FeriadoDto feriadoRegistrado = feriadoService.registrarFeriado(feriadoDto);
        return ResponseEntity.ok(feriadoRegistrado);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<FeriadoDto>> listarFeriados(@RequestParam("mes") int mes, @RequestParam("ano") int ano) {
        List<FeriadoDto> feriados = feriadoService.listarFeriados(mes, ano);
        return ResponseEntity.ok(feriados);
    }
}
