package com.desafio.tecnico.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.desafio.tecnico.dto.CasaDTO;
import com.desafio.tecnico.dto.EnderecoDTO;
import com.desafio.tecnico.dto.PropietarioDTO;
import com.desafio.tecnico.entity.Casa;
import com.desafio.tecnico.entity.Endereco;
import com.desafio.tecnico.entity.Propietario;
import com.desafio.tecnico.repository.CasaRepository;

@ApplicationScoped
public class CasaService {

    @Inject
    private EnderecoService enderecoService;

    @Inject
    private PropietarioService propietarioService;

    @Inject
    private CasaRepository casaRepository;

    public Casa salvar(CasaDTO casaDTO, String cpf) {

        Casa casa = toEntity(casaDTO, cpf);
        casaRepository.save(casa);

        return casa;
    }

    public List<Casa> listarPorPropietario(PropietarioDTO propietarioDTO) {

        String cleanCpf = propietarioDTO.getCpf().replaceAll("[^0-9]", "");

        Propietario propietario = propietarioService.encontrarPorCpf(cleanCpf);
        return casaRepository.findAllByOwner(propietario.getId());
    }

    public CasaDTO buscarPorId(Long idCasa) {
        Casa casa = casaRepository.findById(idCasa);

        return toDTO(casa);
    }

    public void atualizar(Long idCasa, CasaDTO novaCasa, String cpf) {
        Casa casa = casaRepository.findById(idCasa);

        atualizarCampos(casa, novaCasa);

        casaRepository.update(casa);
    }

    public void excluir(Long idCasa, String cpf) {
        Casa casa = casaRepository.findById(idCasa);

        if (casa == null) {
            throw new RuntimeException("Casa não encontrada");
        }

        if (!casa.getPropietario().getCpf().equals(cpf)) {
            throw new RuntimeException("Você não tem permissão para excluir esta casa");
        }

        casaRepository.deactivate(idCasa);
    }

    private void atualizarCampos(Casa casa, CasaDTO novaCasa) {
        casa.setTipo(novaCasa.getTipo());
        casa.setArea(novaCasa.getArea());
        casa.setDescricao(novaCasa.getDescricao());

        Endereco endereco = casa.getEndereco();
        EnderecoDTO enderecoDTO = novaCasa.getEndereco();

        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(Integer.parseInt(enderecoDTO.getNumero()));
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setComplemento(enderecoDTO.getComplemento());

        if (enderecoDTO.getNumero() != null && !enderecoDTO.getNumero().isBlank()) {
            endereco.setNumero(Integer.parseInt(enderecoDTO.getNumero()));
        }

        enderecoService.atualizar(endereco);

    }

    // Mappers -> To Entity e To DTO
    private Casa toEntity(CasaDTO casaDTO, String cpf) {
        Casa casa = new Casa();

        casa.setTipo(casaDTO.getTipo());
        casa.setArea(casaDTO.getArea());
        casa.setDescricao(casaDTO.getDescricao());

        Propietario propietario = propietarioService.encontrarPorCpf(cpf);

        Endereco endereco = enderecoService.salvar(casaDTO.getEndereco());

        casa.setPropietario(propietario);
        casa.setEndereco(endereco);

        casa.setDataCadastro(LocalDateTime.now());
        casa.setAtivo(true);

        return casa;
    }

    private CasaDTO toDTO(Casa casaSelecionada) {
        CasaDTO casaDTO = new CasaDTO();

        casaDTO.setTipo(casaSelecionada.getTipo());
        casaDTO.setArea(casaSelecionada.getArea());
        casaDTO.setDescricao(casaSelecionada.getDescricao());

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        String cep = casaSelecionada.getEndereco().getCep().getCep();
        String cepFormatado = cep.substring(0,5) + "-" + cep.substring(5); 

        enderecoDTO.setEstado(casaSelecionada.getEndereco().getEstado().getUf());
        enderecoDTO.setCidade(casaSelecionada.getEndereco().getCidade().getNomeCidade());
        enderecoDTO.setCep(cepFormatado);
        enderecoDTO.setBairro(casaSelecionada.getEndereco().getBairro());
        enderecoDTO.setLogradouro(casaSelecionada.getEndereco().getLogradouro());
        enderecoDTO.setNumero(String.valueOf(casaSelecionada.getEndereco().getNumero()));
        enderecoDTO.setComplemento(casaSelecionada.getEndereco().getComplemento());

        casaDTO.setEndereco(enderecoDTO);

        return casaDTO;
    }
}
