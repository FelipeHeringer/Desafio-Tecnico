package com.desafio.tecnico.repository;

import com.desafio.tecnico.entity.Cidade;

public interface CidadeRepository {
    
    Cidade findByCityName(String cityName);
}
