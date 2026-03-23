package com.desafio.tecnico.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.desafio.tecnico.dto.CasaDTO;
import com.desafio.tecnico.entity.Casa;
import com.desafio.tecnico.service.CasaService;

@Named("gestaoCasasBean")
@ViewScoped
public class GestaoCasasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PropietarioBean propietarioBean;

    @Inject
    private CasaService casaService;

    private CasaDTO casa;

    private List<Casa> casas;

    private boolean modoEdicao;

    @PostConstruct
    public void init() {

        if (propietarioBean.getPropietario() == null) {
            addMensagemInfo("Sessão expirada Faça login novamente.");
            return;
        }

        carregarCasas();
    }

    public String novaCasa() {
        return "CadastroCasa.xhtml?faces-redirect=true";
    }

    public String prepararEdicao(Casa casa) {
        return "EdicaoCasa.xhtml?faces-redirect=true&id=" + casa.getId();
    }
    
    public void excluir(Casa casaExcluir) {
        try {
            String cleanCpf = propietarioBean.getPropietario().getCpf().replaceAll("[^0-9]", "");
            casaService.excluir(casaExcluir.getId(), cleanCpf);
            carregarCasas();
            addMensagemSucesso("Casa excluída com sucesso");
        } catch (Exception e) {
            addMensagemErro("Erro ao excluir a casa: " + e.getMessage());
        }
    }

    private void carregarCasas() {
        casas = casaService.listarPorPropietario(propietarioBean.getPropietario());
    }

    // Mensagens (utils)
    private void addMensagemSucesso(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", mensagem));
    }

    private void addMensagemErro(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagem));
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

    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }

    public boolean isModoEdicao() {
        return modoEdicao;
    }

    public void setModoEdicao(boolean modoEdicao) {
        this.modoEdicao = modoEdicao;
    }
}
