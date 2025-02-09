package com.senai.anchieta.domain.repository;

import com.senai.anchieta.api.fucionario.model.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionarios, Long> {
}
