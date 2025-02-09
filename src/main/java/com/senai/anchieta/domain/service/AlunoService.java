package com.senai.anchieta.domain.service;

import com.senai.anchieta.api.student.dto.AlunoDTO;
import com.senai.anchieta.api.student.model.Aluno;
import com.senai.anchieta.domain.repository.AlunoRepository;
import com.senai.anchieta.domain.repository.UserRepository;
import com.senai.anchieta.api.auth.model.User;
import com.senai.anchieta.api.auth.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AlunoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno createAluno(AlunoDTO alunoDTO, Principal principal) {

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(alunoDTO.senha());

            Aluno aluno = new Aluno();
            aluno.setCpf(alunoDTO.cpf());
            aluno.setNome(alunoDTO.nome());
            aluno.setEmail(alunoDTO.email());
            aluno.setSenha(encryptedPassword);
            aluno.setTelefone(alunoDTO.telefone());
            aluno.setRole(alunoDTO.role());
            aluno.setCadastradoPor(principal.getName());
            aluno.setMatricula(alunoDTO.matricula());

            this.register(alunoDTO.email(), alunoDTO.senha(), alunoDTO.role());
            alunoRepository.save(aluno);

            return aluno;
        } catch (Exception e) {
            throw new RuntimeException("Erro: erro ao inserir o usuario: " + e);
        }
    }

    public Aluno updateAluno(AlunoDTO alunoDTO){
        try{

            String encryptedPassword = new BCryptPasswordEncoder().encode(alunoDTO.senha());

            Aluno aluno = new Aluno();
            aluno.setCpf(alunoDTO.cpf());
            aluno.setNome(alunoDTO.nome());
            aluno.setEmail(alunoDTO.email());
            aluno.setSenha(encryptedPassword);
            aluno.setTelefone(alunoDTO.telefone());
            aluno.setRole(alunoDTO.role());

            alunoRepository.save(aluno);

            return aluno;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String register(String email, String senha, UserRoles role){
        if (this.userRepository.findByLogin(email) != null) return "erro ao registrar aluno";

        String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
        User user = new User(email, encryptedPassword, role);

        this.userRepository.save(user);
        return "Aluno registrado com sucesso";
    }
}
