package br.com.helton.projeto_ponto_eletronico.service;

import br.com.helton.projeto_ponto_eletronico.domain.*;
import br.com.helton.projeto_ponto_eletronico.dto.DataMesAnoDto;
import br.com.helton.projeto_ponto_eletronico.dto.PontoDto;
import br.com.helton.projeto_ponto_eletronico.dto.RegistroPontoDto;
import br.com.helton.projeto_ponto_eletronico.infra.exception.RegistroPontoException;
import br.com.helton.projeto_ponto_eletronico.infra.exception.UsuarioNaoEncontradoException;
import br.com.helton.projeto_ponto_eletronico.repository.JornadaRepository;
import br.com.helton.projeto_ponto_eletronico.repository.PontoRepository;
import br.com.helton.projeto_ponto_eletronico.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PontoService {

    @Autowired
    private PontoRepository pontoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JornadaRepository jornadaRepository;

    @Transactional
    public PontoDto registrarPonto(RegistroPontoDto registroPontoDto, String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));
        Jornada jornada = usuario.getJornada();

        LocalDate hoje = LocalDate.now();
        Ponto ponto = pontoRepository.findByUsuarioAndData(usuario, hoje)
                .orElse(new Ponto(usuario, jornada, hoje));

        switch (registroPontoDto.tipoPonto()) {
            case ENTRADA:
                if (ponto.getHoraEntrada() != null) {
                    throw new RegistroPontoException("Hora de entrada já registrada para este ponto.");
                }
                ponto.setHoraEntrada(LocalTime.now().withSecond(0).withNano(0));
                break;

            case PAUSA_ALMOCO:
                if (ponto.getPausaAlmoco() != null) {
                    throw new RegistroPontoException("Pausa para almoço já registrada para este ponto.");
                }
                ponto.setPausaAlmoco(LocalTime.now().withSecond(0).withNano(0));
                break;

            case RETORNO_ALMOCO:
                if(ponto.getPausaAlmoco() == null) {
                    throw new RegistroPontoException("Ainda não foi registrada a pausa para o almoço.");
                }
                if (ponto.getRetornoAlmoco() != null) {
                    throw new RegistroPontoException("Retorno do almoço já registrado para este ponto.");
                }
                ponto.setRetornoAlmoco(LocalTime.now().withSecond(0).withNano(0));
                break;

            case SAIDA:
                if(ponto.getRetornoAlmoco() == null) {
                    throw new RegistroPontoException("Ainda não foi registrado o retorno do almoço.");
                }
                if (ponto.getHoraSaida() != null) {
                    throw new RegistroPontoException("Hora de saída já registrada para este ponto.");
                }
                ponto.setHoraSaida(LocalTime.now().withSecond(0).withNano(0));
                ponto.setHorasTrabalhadas();
                ponto.setHorasExcedentes();
                jornada.getPontos().add(ponto);
                break;

            default:
                throw new RegistroPontoException("Tipo de ponto inválido.");
        }

        pontoRepository.save(ponto);
        return new PontoDto(ponto);
    }

    public PontoDto consultarPontoDoDia(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));

        LocalDate hoje = LocalDate.now();
        Ponto ponto = pontoRepository.findByUsuarioAndData(usuario, hoje)
                .orElseThrow(() -> new RegistroPontoException("Nenhum ponto registrado para o dia de hoje."));
        return new PontoDto(ponto);
    }

    public List<PontoDto> listarPontosDoMesAtual(String login) {
        LocalDate hoje = LocalDate.now();
        List<Ponto> pontos = calcularPontosPorMes(login, hoje.getYear(), hoje.getMonthValue());
        return pontos.stream().map(PontoDto::new).collect(Collectors.toList());
    }

    public List<PontoDto> listarPontosPorMes(String login, DataMesAnoDto data) {
        List<Ponto> pontos = calcularPontosPorMes(login, data.ano(), data.mes());
        return pontos.stream().map(PontoDto::new).collect(Collectors.toList());
    }

    public List<Ponto> calcularPontosPorMes(String login, int ano, int mes) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));

        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        List<Ponto> pontos = pontoRepository.findByUsuarioAndDataBetween(usuario, inicioMes, fimMes);
        return pontos;
    }

}

