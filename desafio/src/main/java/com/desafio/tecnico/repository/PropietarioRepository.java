package com.desafio.tecnico.repository;

import com.desafio.tecnico.entity.Propietario;

public interface PropietarioRepository {
    
    void save(Propietario propietario);
    Propietario findByCpf(String cpf);
}
