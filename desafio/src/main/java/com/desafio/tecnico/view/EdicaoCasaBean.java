package com.desafio.tecnico.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.desafio.tecnico.dto.CasaDTO;
import com.desafio.tecnico.service.CasaService;

@Named("edicaoCasaBean")
@ViewScoped
public class EdicaoCasaBean implements Serializable {

    @Inject
    private CasaService casaService;

    @Inject
    private PropietarioBean propietarioBean;

    private Long idCasa;
    private CasaDTO casa;

    public void carregarCasa() {
        if (idCasa != null) {
            casa = casaService.buscarPorId(idCasa);
        }
    }

    public String atualizar() {
        try {
            casaService.atualizar(idCasa, casa, propietarioBean.getPropietario().getCpf());
            return "GestaoCasas.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
            return null;
        }
    }

    public String cancelar() {
        return "GestaoCasas.xhtml?faces-redirect=true";
    }

    public Long getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(Long idCasa) {
        this.idCasa = idCasa;
    }

    public CasaDTO getCasa() {
        return casa;
    }

    public void setCasa(CasaDTO casa) {
        this.casa = casa;
    }
}
