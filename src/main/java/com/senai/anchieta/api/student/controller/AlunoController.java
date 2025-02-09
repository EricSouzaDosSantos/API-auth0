package com.senai.anchieta.api.student.controller;

import com.senai.anchieta.api.auth.model.User;
import com.senai.anchieta.api.student.model.Aluno;
import com.senai.anchieta.api.student.dto.AlunoDTO;
import com.senai.anchieta.domain.repository.AlunoRepository;
import com.senai.anchieta.domain.repository.UserRepository;
import com.senai.anchieta.domain.service.AlunoService;
import com.senai.anchieta.api.auth.model.UserRoles;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@RestController
@RequestMapping("aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAllAlunos(){
        var aluno = alunoRepository.findAll();
        return ResponseEntity.ok(aluno);
    }

    @GetMapping(path = {"/cpf/{cpf}"})
    public ResponseEntity pegarAlunoPeloCPF(@PathVariable String cpf){
        return alunoRepository.findById(cpf)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/nome/{nome}")
    public ResponseEntity<?> pegarAlunoPeloNome(@PathVariable String nome){
        List<Aluno> aluno = alunoRepository.findByNome(nome);
        if (aluno == null){
            return ResponseEntity.ok().body("Nehum aluno foi encontrado com esse nome");
        }
        return ResponseEntity.ok().body(aluno);
    }
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity pegarAlunoPelaMatricula(@PathVariable String matricula){
        Aluno aluno = alunoRepository.findByMatricula(matricula);
        if(aluno == null){
            return ResponseEntity.ok().body("Aluno não encontrado");
        }
        return ResponseEntity.ok().body(aluno);
    }

    @DeleteMapping(path = {"/cpf/{cpf}"})
    public ResponseEntity deletarAlunopeloCPF(@PathVariable String cpf, Principal principal){
        return alunoRepository.findById(cpf)
                .map(record -> {
                    User usuarioLogado =(User) userRepository.findByLogin(principal.getName());

                    if (!record.getEmail().equals(principal.getName()) &&
                            (usuarioLogado == null || !usuarioLogado.getRole().equals(UserRoles.AQV))) {
                        return ResponseEntity.status(403).body("Acesso negado. Você não tem permissão para deletar este aluno.");
                    }

                    alunoRepository.deleteById(cpf);
                return ResponseEntity.ok().body(record);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update/{cpf}")
    public ResponseEntity atualizarAluno(@PathVariable String cpf,
                                         @RequestParam(required = false) String nome,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String senha,
                                         @RequestParam(required = false) String telefone, Principal principal) {
        return alunoRepository.findById(cpf)
                .map(record -> {
                    User usuarioLogado =(User) userRepository.findByLogin(principal.getName());

                    if (!record.getEmail().equals(principal.getName()) &&
                            (usuarioLogado == null || !usuarioLogado.getRole().equals(UserRoles.AQV))) {
                        return ResponseEntity.status(403).body("Acesso negado. Você não tem permissão para atualizar este aluno.");
                    }


                    boolean atualizado = false;

                    if (nome != null && !nome.isBlank()) {
                        record.setNome(nome);
                        atualizado = true;
                    }

                    if (email != null && !email.isBlank() && !email.equals(record.getEmail())) {
                        if (alunoRepository.findByEmail(email) == null) {
                            record.setEmail(email);
                            atualizado = true;
                        } else {
                            return ResponseEntity.badRequest().body("E-mail já está em uso.");
                        }
                    }

                    if (senha != null && !senha.isBlank()) {
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        record.setSenha(encoder.encode(senha));
                        atualizado = true;
                    }

                    if (telefone != null && !telefone.isBlank()) {
                        record.setTelefone(telefone);
                        atualizado = true;
                    }

                    if (atualizado) {
                        alunoRepository.save(record);
                        return ResponseEntity.ok("Aluno atualizado com sucesso.");
                    } else {
                        return ResponseEntity.badRequest().body("Nenhuma alteração foi realizada.");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping("/create")
    public ResponseEntity criarAluno(@RequestParam("cpf") String cpf,
                                     @RequestParam("nome") String nome,
                                     @RequestParam("email") String email,
                                     @RequestParam("senha") String senha,
                                     @RequestParam("telefone") String telefone,
                                     @RequestParam("matricula") String matricula, Principal principal) {
        try {
            if (alunoRepository.findById(cpf).isPresent() || (alunoRepository.findByEmail(email) != null)){
                return ResponseEntity.ok().body("já existe um cpf ou email cadastrado com essas informações.");
            }
            AlunoDTO alunoDTO = new AlunoDTO(cpf, nome, email, senha, telefone, UserRoles.USER, matricula);

            Aluno aluno = this.alunoService.createAluno(alunoDTO, principal);
            return ResponseEntity.ok(aluno);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
