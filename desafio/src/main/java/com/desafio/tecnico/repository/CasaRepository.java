package com.desafio.tecnico.repository;

import java.util.List;

import com.desafio.tecnico.entity.Casa;

public interface CasaRepository {
    
    Casa save(Casa casa);

    List<Casa> findAllByOwner(Long propietario);

    Casa findById(Long idCasa);

    Casa update(Casa casa);

    void deactivate(Long idCasa);
}
