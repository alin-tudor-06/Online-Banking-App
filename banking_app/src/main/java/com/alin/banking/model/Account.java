package com.alin.banking.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)

    private String numar_cont;

    private String nume_detinator;

    private Double balanta;

    public Account() {}

    public Account(String numar_cont, String nume_detinator, Double balanta) {
        this.numar_cont = numar_cont;
        this.nume_detinator=nume_detinator;
        this.balanta=balanta;
    }

    public Long getId() {
        return id;
    }

    public String getNumar_cont() {
        return numar_cont;
    }

    public String getNume_detinator() {
        return nume_detinator;
    }

    public Double getBalanta() {
        return balanta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumar_cont(String numar_cont) {
        this.numar_cont = numar_cont;
    }

    public void setNume_detinator(String nume_detinator) {
        this.nume_detinator = nume_detinator;
    }

    public void setBalanta(Double balanta) {
        this.balanta = balanta;
    }
}

