package br.com.helton.projeto_ponto_eletronico.domain;

import br.com.helton.projeto_ponto_eletronico.dto.JornadaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jornadas")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_jornada", nullable = false)
    private TipoJornada tipoJornada;

    @OneToMany(mappedBy = "jornada")
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "jornada", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ponto> pontos = new ArrayList<>();


    public Jornada(JornadaDto jornadaDto) {
        this.tipoJornada = jornadaDto.tipoJornada();
    }

    public Jornada() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(TipoJornada tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    public void setPontos(List<Ponto> pontos) {
        this.pontos = pontos;
    }

}


