package com.desafio.tecnico.repository;

import com.desafio.tecnico.entity.Endereco;

public interface EnderecoRepository {
    
    Endereco save(Endereco endereco);
    Endereco update(Endereco endereco);
    Endereco findByAllFields(Long cepId, Long estadoId, Long cidadeId, String bairro, String logradouro, Integer numero, String complemento);
}
