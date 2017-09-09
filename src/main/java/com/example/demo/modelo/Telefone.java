package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(length = 2, nullable = false)
    private String ddd;

    @Column(length = 9, nullable = false)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    @JsonIgnore
    private Pessoa pessoa;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telefone telefone = (Telefone) o;

        return getCodigo() != null ? getCodigo().equals(telefone.getCodigo()) : telefone.getCodigo() == null;
    }

    @Override
    public int hashCode() {
        return getCodigo() != null ? getCodigo().hashCode() : 0;
    }
}
