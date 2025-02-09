package com.senai.anchieta.api.fucionario.model;

import com.senai.anchieta.api.auth.model.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Funcionarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 11, max = 11)
    @NotBlank(message = "O CPF é obrigatório")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório")
    @Email
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Column(name = "matricula", nullable = false, unique = true)
    @NotBlank(message = "O numero de matricula é obrigatório")
    private String numeroDeMatricula;
}
