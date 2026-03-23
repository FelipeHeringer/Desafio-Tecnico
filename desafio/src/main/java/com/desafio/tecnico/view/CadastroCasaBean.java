package com.desafio.tecnico.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.desafio.tecnico.dto.CasaDTO;
import com.desafio.tecnico.entity.Casa;
import com.desafio.tecnico.service.CasaService;

@Named("cadastroCasaBean")
@ViewScoped
public class CadastroCasaBean implements Serializable {

    @Inject
    private CasaService casaService;

    @Inject
    private PropietarioBean propietarioBean;

    private CasaDTO casa;

    @PostConstruct
    public void init() {
        if (propietarioBean.getPropietario() == null) {
            addMensagemInfo("Sessão expirada Faça login novamente.");
            return;
        }

        novaCasa();
    }

    public String salvar() {
        try {
            String cleanCpf = propietarioBean.getPropietario().getCpf().replaceAll("[^0-9]", "");
            Casa casaSalva = casaService.salvar(casa, cleanCpf);
            if (casaSalva != null) {
                return "GestaoCasas.xhtml?faces-redirect=true";
            }

            return "CadastroCasa.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
            return null;
        }
    }

    public String voltar() {
        return "GestaoCasas.xhtml?faces-redirect=true";
    }

    private void novaCasa() {
        this.casa = new CasaDTO();
    }

    private void addMensagemInfo(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Informação", mensagem));
    }

    public CasaDTO getCasa() {
        return casa;
    }

    public void setCasa(CasaDTO casa) {
        this.casa = casa;
    }
}
