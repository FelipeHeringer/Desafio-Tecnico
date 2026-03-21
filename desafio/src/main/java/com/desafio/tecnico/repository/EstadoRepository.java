package com.desafio.tecnico.repository;

import com.desafio.tecnico.entity.Estado;

public interface EstadoRepository {
    
    Estado findByUF(String EstadoUF);
}
