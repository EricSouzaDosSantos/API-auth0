package com.senai.anchieta.api.fucionario.controller;

import com.senai.anchieta.api.auth.model.UserRoles;
import com.senai.anchieta.api.fucionario.dto.FuncionarioDTO;
import com.senai.anchieta.api.fucionario.model.Funcionarios;
import com.senai.anchieta.domain.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/create")
    public ResponseEntity<?> criarFuncionario(@RequestParam String cpf,
                                              @RequestParam String nome,
                                              @RequestParam String email,
                                              @RequestParam String senha,
                                              @RequestParam String matricula,
                                              @RequestParam UserRoles role) {
        try {
            FuncionarioDTO funcionarioDTO = new FuncionarioDTO(nome, email, senha, cpf, role, matricula);
            Funcionarios funcionario = funcionarioService.createFuncionario(funcionarioDTO);
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar funcionário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarFuncionarios() {
        try {
            return ResponseEntity.ok(funcionarioService.listarTodosFuncionarios());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao listar funcionários: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
        try {
            this.funcionarioService.deletarFuncionarioPorCpf(id);
            return ResponseEntity.ok("Funcionário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

}
