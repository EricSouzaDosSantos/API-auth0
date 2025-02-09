package com.senai.anchieta.domain.repository;

import com.senai.anchieta.api.entrada.RequisicaoDeEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoDeEntradaRepository extends JpaRepository<RequisicaoDeEntrada, Long> {
}
