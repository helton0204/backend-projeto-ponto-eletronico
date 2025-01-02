package br.com.helton.projeto_ponto_eletronico.domain;

import br.com.helton.projeto_ponto_eletronico.dto.FeriadoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "feriados")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;


    public Feriado() {

    }

    public Feriado(FeriadoDto feriadoDto) {
        this.data = feriadoDto.data();
        this.nome = feriadoDto.nome();
        this.tipo = feriadoDto.tipo();
    }


    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
