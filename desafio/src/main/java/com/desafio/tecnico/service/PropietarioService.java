package com.desafio.tecnico.service;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.desafio.tecnico.dto.PropietarioDTO;
import com.desafio.tecnico.entity.Endereco;
import com.desafio.tecnico.entity.Propietario;
import com.desafio.tecnico.repository.PropietarioRepository;
import com.desafio.tecnico.repository.impl.PropietarioRepositoryImpl;

@ApplicationScoped
public class PropietarioService {

    @Inject
    private EnderecoService enderecoService;

    private PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl();

    public void salvar(PropietarioDTO propietarioDTO) {
        Propietario propietario = toEntity(propietarioDTO);
        propietario.setDataCadastro(LocalDateTime.now());
        propietarioRepository.save(propietario);
    }

    public Propietario encontrarPorCpf(String cpf) {
        return propietarioRepository.findByCpf(cpf);
    }

    public Propietario entrar(PropietarioDTO propietarioDTO) {
        return propietarioRepository.findByCpf(propietarioDTO.getCpf());
    }

    private Propietario toEntity(PropietarioDTO propietario) {
        Propietario entity = new Propietario();
        entity.setNome(propietario.getNome());
        entity.setCpf(propietario.getCpf());
        entity.setEmail(propietario.getEmail());
        entity.setTelefone(propietario.getTelefone());

        // Mapear o endereço do DTO para a entidade
        Endereco endereco = enderecoService.salvar(propietario.getEndereco());
        entity.setEndereco(endereco);

        return entity;
    }
}
