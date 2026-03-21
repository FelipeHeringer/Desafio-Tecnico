package com.desafio.tecnico.repository;

import com.desafio.tecnico.entity.CEP;

public interface CEPRepository {
    
    void save(CEP cep); 
    CEP findByCEPNumber(String cepNumber);
}
