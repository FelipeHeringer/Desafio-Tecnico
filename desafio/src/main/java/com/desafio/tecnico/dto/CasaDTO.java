package com.desafio.tecnico.dto;

import java.math.BigDecimal;

public class CasaDTO {
    
    private String tipo;
    private String descricao;
    private BigDecimal area;
    private EnderecoDTO endereco;

    public CasaDTO() {
        this.endereco = new EnderecoDTO();
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public BigDecimal getArea() {
        return area;
    }
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    public EnderecoDTO getEndereco() {
        return endereco;
    }
    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
