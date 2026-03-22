package com.desafio.tecnico.service;

import javax.enterprise.context.ApplicationScoped;

import com.desafio.tecnico.dto.EnderecoDTO;
import com.desafio.tecnico.entity.CEP;
import com.desafio.tecnico.entity.Cidade;
import com.desafio.tecnico.entity.Endereco;
import com.desafio.tecnico.entity.Estado;
import com.desafio.tecnico.repository.CEPRepository;
import com.desafio.tecnico.repository.CidadeRepository;
import com.desafio.tecnico.repository.EnderecoRepository;
import com.desafio.tecnico.repository.EstadoRepository;
import com.desafio.tecnico.repository.impl.CEPRepositoryImpl;
import com.desafio.tecnico.repository.impl.CidadeRepositoryImpl;
import com.desafio.tecnico.repository.impl.EnderecoRepositoryImpl;
import com.desafio.tecnico.repository.impl.EstadoRepositoryImpl;

@ApplicationScoped
public class EnderecoService {

    private EnderecoRepository enderecoRepository = new EnderecoRepositoryImpl();

    private EstadoRepository estadoRepository = new EstadoRepositoryImpl();

    private CidadeRepository cidadeRepository = new CidadeRepositoryImpl();

    private CEPRepository cepRepository = new CEPRepositoryImpl();

    public Endereco salvar(EnderecoDTO enderecoDTO) {
        return encontrarOuCriaEndereco(enderecoDTO);
    }

    public Endereco atualizar(Endereco endereco) {
        return enderecoRepository.update(endereco);
    }

    private Endereco encontrarOuCriaEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = toEntity(enderecoDTO);

        Endereco existente = enderecoRepository.findByAllFields(
                endereco.getCep().getId(), endereco.getEstado().getId(),
                endereco.getCidade().getId(),endereco.getBairro(), endereco.getLogradouro(),
                endereco.getNumero(), endereco.getComplemento());

        if (existente != null) return existente;

        return enderecoRepository.save(endereco);
    }

    private Endereco toEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();

        Cidade cidade = cidadeRepository.findByCityName(enderecoDTO.getCidade());
        Estado estado = estadoRepository.findByUF(enderecoDTO.getEstado());
        CEP cep = findOrCreateCEP(enderecoDTO.getCep());

        endereco.setEstado(estado);
        endereco.setCidade(cidade);
        endereco.setCep(cep);
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(Integer.parseInt(enderecoDTO.getNumero()));
        endereco.setComplemento(enderecoDTO.getComplemento());

        return endereco;
    }

    private CEP findOrCreateCEP(String cepNumber) {
        String cleanCepNumber = cepNumber.replace("-", "");
        CEP cep = cepRepository.findByCEPNumber(cleanCepNumber);

        if (cep == null) {
            cep = new CEP();
            cep.setCep(cleanCepNumber);
            cepRepository.save(cep);
        }

        return cep;
    }

}
