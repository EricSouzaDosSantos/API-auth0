package com.senai.anchieta.domain.repository;

import com.senai.anchieta.api.student.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {

    UserDetails findByEmail(String email);
    Aluno findByMatricula(String matricula);
    List<Aluno> findByNome(String nome);
}
