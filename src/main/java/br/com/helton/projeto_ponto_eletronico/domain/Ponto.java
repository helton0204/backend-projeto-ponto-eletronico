package br.com.helton.projeto_ponto_eletronico.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pontos")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "pausa_almoco")
    private LocalTime pausaAlmoco;

    @Column(name = "retorno_almoco")
    private LocalTime retornoAlmoco;

    @Column(name = "hora_saida")
    private LocalTime horaSaida;

    @Column(name = "horas_trabalhadas")
    private LocalTime horasTrabalhadas;

    @Column(name = "saldo_em_minutos")
    private Integer saldoEmMinutos;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "jornada_id", nullable = false)
    private Jornada jornada;


    public Ponto(Usuario usuario, Jornada jornada, LocalDate data) {
        this.usuario = usuario;
        this.jornada = jornada;
        this.data = data;
    }

    public Ponto() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getPausaAlmoco() {
        return pausaAlmoco;
    }

    public void setPausaAlmoco(LocalTime pausaAlmoco) {
        this.pausaAlmoco = pausaAlmoco;
    }

    public LocalTime getRetornoAlmoco() {
        return retornoAlmoco;
    }

    public void setRetornoAlmoco(LocalTime retornoAlmoco) {
        this.retornoAlmoco = retornoAlmoco;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public LocalTime getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas() {
        if (horaEntrada != null && horaSaida != null) {
            Duration duration = Duration.between(this.horaEntrada, this.horaSaida);
            this.horasTrabalhadas = LocalTime.of((int) duration.toHours(), (int) (duration.toMinutes() % 60));
        }
    }

    public Integer getSaldoEmMinutos() {
        return saldoEmMinutos;
    }

    public void setSaldoEmMinutos() {
        int expediente = this.jornada.getTipoJornada() == TipoJornada.SEIS_HORAS ? 360 : 480;
        if (this.horasTrabalhadas != null) {
            int totalMinutosTrabalhados = this.horasTrabalhadas.getHour() * 60 + this.horasTrabalhadas.getMinute();
            this.saldoEmMinutos = totalMinutosTrabalhados - expediente;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
