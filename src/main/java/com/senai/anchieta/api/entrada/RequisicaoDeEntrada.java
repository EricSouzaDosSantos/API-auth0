package com.senai.anchieta.api.entrada;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RequisicaoDeEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cpfAluno;
    private String nomeAluno;
    private String liberadoPor;
    private boolean aprovado;
}
