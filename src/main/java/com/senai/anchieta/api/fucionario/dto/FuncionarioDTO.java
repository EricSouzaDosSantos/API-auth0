package com.senai.anchieta.api.fucionario.dto;

import com.senai.anchieta.api.auth.model.UserRoles;

public record FuncionarioDTO(String nome, String email, String senha, String cpf, UserRoles role, String matricula) {
}
