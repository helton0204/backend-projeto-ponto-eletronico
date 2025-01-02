package br.com.helton.projeto_ponto_eletronico.service;

import br.com.helton.projeto_ponto_eletronico.domain.Feriado;
import br.com.helton.projeto_ponto_eletronico.dto.BrasilApiDto;
import br.com.helton.projeto_ponto_eletronico.dto.FeriadoDto;
import br.com.helton.projeto_ponto_eletronico.repository.FeriadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeriadoService {

    @Autowired
    FeriadoRepository feriadoRepository;
    @Autowired
    private RestTemplate restTemplate;

    public FeriadoDto registrarFeriado(FeriadoDto feriadoDto){
        Feriado feriado = new Feriado(feriadoDto);
        feriadoRepository.save(feriado);
        return new FeriadoDto(feriado);
    }

    public void RegistrarFeriadosDoAno(int ano) {
        String url = "https://brasilapi.com.br/api/feriados/v1/" + ano;
        try {
            BrasilApiDto[] feriados = restTemplate.getForObject(url, BrasilApiDto[].class);

            List<Feriado> feriadosParaSalvar = Arrays.stream(feriados)
                    .map(feriado -> new Feriado(new FeriadoDto(LocalDate.parse(feriado.date()), feriado.name(), feriado.type())))
                    .collect(Collectors.toList());

            feriadoRepository.saveAll(feriadosParaSalvar);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar os feriados para o ano " + ano, e);
        }
    }

    public List<FeriadoDto> listarFeriados(int mes, int ano) {
        LocalDate primeiroDiaDoMes = LocalDate.of(ano, mes, 1);
        LocalDate ultimoDiaDoMes = primeiroDiaDoMes.withDayOfMonth(primeiroDiaDoMes.lengthOfMonth());

        List<Feriado> feriados = feriadoRepository.findByDataBetween(primeiroDiaDoMes, ultimoDiaDoMes);

        return feriados.stream().map(FeriadoDto::new).collect(Collectors.toList());
    }

}
