package br.com.helton.projeto_ponto_eletronico.service;

import br.com.helton.projeto_ponto_eletronico.domain.*;
import br.com.helton.projeto_ponto_eletronico.dto.*;
import br.com.helton.projeto_ponto_eletronico.infra.exception.RegistroPontoException;
import br.com.helton.projeto_ponto_eletronico.infra.exception.UsuarioNaoEncontradoException;
import br.com.helton.projeto_ponto_eletronico.repository.FeriadoRepository;
import br.com.helton.projeto_ponto_eletronico.repository.PontoRepository;
import br.com.helton.projeto_ponto_eletronico.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PontoService {

    @Autowired
    private PontoRepository pontoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private FeriadoRepository feriadoRepository;


    @Transactional
    public PontoDto registrarPonto(RegistroPontoDto registroPontoDto, String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));

        Jornada jornada = usuario.getJornada();

        LocalDate hoje = LocalDate.now();

        Ponto ponto = pontoRepository.findByUsuarioAndData(usuario, hoje)
                .orElseThrow(() -> new RegistroPontoException("Nenhum ponto registrado para o dia de hoje."));

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
                ponto.setSaldoEmMinutos();
                jornada.getPontos().add(ponto);
                break;

            default:
                throw new RegistroPontoException("Tipo de ponto inválido.");
        }

        pontoRepository.save(ponto);
        return new PontoDto(ponto);
    }

    @Transactional
    public List<PontoDto> listarPontosDoMesAtual(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));

        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        if (!pontosJaRegistrados(usuario, hoje)) {
            registrarTodosOsPontosDoMes(usuario, hoje);
        }

        List<Ponto> pontos = pontoRepository.findByUsuarioAndDataBetween(usuario, inicioMes, fimMes);

        return pontos.stream().map(PontoDto::new).collect(Collectors.toList());
    }

    public List<PontoDto> listarPontosPorMes(String login, DataMesAnoDto data) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o login: " + login));

        LocalDate dataConsiderada = LocalDate.of(data.ano(), data.mes(), 1);
        LocalDate inicioMes = dataConsiderada.withDayOfMonth(1);
        LocalDate fimMes = dataConsiderada.withDayOfMonth(dataConsiderada.lengthOfMonth());

        List<Ponto> pontos = pontoRepository.findByUsuarioAndDataBetween(usuario, inicioMes, fimMes);
        return pontos.stream().map(PontoDto::new).collect(Collectors.toList());
    }

    private boolean pontosJaRegistrados(Usuario usuario, LocalDate dataAtual) {
        LocalDate inicioMes = dataAtual.withDayOfMonth(1);
        LocalDate fimMes = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth());

        return !pontoRepository.findByUsuarioAndDataBetween(usuario, inicioMes, fimMes).isEmpty();
    }

    private void registrarTodosOsPontosDoMes(Usuario usuario, LocalDate dataAtual) {
        LocalDate inicioMes = dataAtual.withDayOfMonth(1);
        LocalDate fimMes = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth());

        LocalDate inicioAno = LocalDate.of(dataAtual.getYear(), 1, 1);
        LocalDate fimAno = LocalDate.of(dataAtual.getYear(), 12, 31);

        if(!feriadoRepository.existsByDataBetween(inicioAno, fimAno)) {
            System.out.println("Registrando feriados do ano: " + inicioAno);
            feriadoService.RegistrarFeriadosDoAno(dataAtual.getYear());
        }

        List<FeriadoDto> feriados = feriadoService.listarFeriados(dataAtual.getMonthValue(), dataAtual.getYear());

        Set<LocalDate> feriadosSet = feriados.stream()
                .map(feriado -> feriado.data())
                .collect(Collectors.toSet());

        for (LocalDate data = inicioMes; !data.isAfter(fimMes); data = data.plusDays(1)) {
            if (feriadosSet.contains(data)) {
                continue;
            }
            Ponto ponto = pontoRepository.findByUsuarioAndData(usuario, data)
                    .orElse(new Ponto(usuario, usuario.getJornada(), data));
            pontoRepository.save(ponto);
        }
    }

}

