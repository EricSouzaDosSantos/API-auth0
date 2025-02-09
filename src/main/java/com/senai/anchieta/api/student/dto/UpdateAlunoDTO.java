package com.senai.anchieta.api.student.dto;

import com.senai.anchieta.api.auth.model.UserRoles;

public record UpdateAlunoDTO(String nome, String email, String senha, String telefone, UserRoles role, String matricula) {
}
