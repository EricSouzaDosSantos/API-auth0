package com.senai.anchieta.api.student.model;

import com.senai.anchieta.api.auth.model.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "alunos")
@Data
public class Aluno {

    @Id
    @Size(min = 11, max = 11)
    @NotBlank(message = "O CPF é obrigatório")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório")
    @Email
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    private String cadastradoPor;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "matricula", unique = true)
    private String matricula;

}
