package com.senai.anchieta.api.entrada.controller;

import com.senai.anchieta.api.auth.model.User;
import com.senai.anchieta.api.auth.model.UserRoles;
import com.senai.anchieta.api.entrada.RequisicaoDeEntrada;
import com.senai.anchieta.api.student.model.Aluno;
import com.senai.anchieta.domain.repository.AlunoRepository;
import com.senai.anchieta.domain.repository.RequisicaoDeEntradaRepository;
import com.senai.anchieta.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrada")
public class RequisicaoDeEntradaController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequisicaoDeEntradaRepository requisicaoDeEntradaRepository;

    @PostMapping("/requisitar-entrada")
    public ResponseEntity requisitarEntrada(@RequestParam String cpf) {


        Optional<Aluno> aluno = alunoRepository.findById(cpf);
        if (aluno.isPresent()) {
            RequisicaoDeEntrada novaRequisicao = new RequisicaoDeEntrada();
            novaRequisicao.setCpfAluno(cpf);
            novaRequisicao.setNomeAluno(aluno.get().getNome());
            novaRequisicao.setAprovado(false);
            requisicaoDeEntradaRepository.save(novaRequisicao);
            return ResponseEntity.ok("Requisição enviada com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listar-requisicoes")
    public ResponseEntity listarRequisicoes(Principal principal) {

        User usuarioLoggado = (User)  userRepository.findByLogin(principal.getName());

        if(!(usuarioLoggado.getRole().equals(UserRoles.AQV) || usuarioLoggado.getRole().equals(UserRoles.COORDENADOR)))
            return ResponseEntity.status(403).body("Acesso negado. Você não tem permissão para listar as requisições de entrada.");


        List<RequisicaoDeEntrada> requisicoes = requisicaoDeEntradaRepository.findAll();
        return ResponseEntity.ok(requisicoes);
    }

    @PutMapping("/decidir-requisicao/{id}")
    public ResponseEntity decidirRequisicao(@PathVariable long id, @RequestParam boolean aprovado, Principal principal) {
        Optional<RequisicaoDeEntrada> requisicao = requisicaoDeEntradaRepository.findById(id);
        if (requisicao.isPresent()) {
            RequisicaoDeEntrada req = requisicao.get();
            req.setAprovado(aprovado);
            req.setLiberadoPor(principal.getName());
            requisicaoDeEntradaRepository.save(req);
            return ResponseEntity.ok(aprovado ? "Requisição aprovada." : "Requisição negada.");
        }
        return ResponseEntity.notFound().build();
    }
}
