package com.senai.anchieta.domain.service;

import com.senai.anchieta.api.auth.model.User;
import com.senai.anchieta.api.auth.model.UserRoles;
import com.senai.anchieta.api.fucionario.dto.FuncionarioDTO;
import com.senai.anchieta.api.fucionario.model.Funcionarios;
import com.senai.anchieta.domain.repository.FuncionarioRepository;
import com.senai.anchieta.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionarios createFuncionario(FuncionarioDTO funcionarioDTO) {

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(funcionarioDTO.senha());

            Funcionarios funcioarios = new Funcionarios();
            funcioarios.setCpf(funcionarioDTO.cpf());
            funcioarios.setNome(funcionarioDTO.nome());
            funcioarios.setEmail(funcionarioDTO.email());
            funcioarios.setSenha(encryptedPassword);
            funcioarios.setNumeroDeMatricula(funcionarioDTO.matricula());
            funcioarios.setRole(funcionarioDTO.role());

            this.register(funcionarioDTO.email(), funcionarioDTO.senha(), funcionarioDTO.role());
            funcionarioRepository.save(funcioarios);

            return funcioarios;
        } catch (Exception e) {
            throw new RuntimeException("Erro: erro ao inserir o usuario: " + e);
        }
    }

    public String register(String email, String senha, UserRoles role){
        if (this.userRepository.findByLogin(email) != null) return "erro ao registrar Funcionario";

        String encryptedPassword = new BCryptPasswordEncoder().encode(senha);
        User user = new User(email, encryptedPassword, role);

        this.userRepository.save(user);
        return "Funcionario registrado com sucesso";
    }

    public List<Funcionarios> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public void deletarFuncionarioPorCpf(Long id) {
        Funcionarios funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id: " + id));
        User user = (User) userRepository.findByLogin(funcionario.getEmail());

        userRepository.delete(user);
        funcionarioRepository.delete(funcionario);
    }
}
