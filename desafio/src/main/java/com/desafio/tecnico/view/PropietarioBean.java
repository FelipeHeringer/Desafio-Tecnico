package com.desafio.tecnico.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.desafio.tecnico.dto.PropietarioDTO;
import com.desafio.tecnico.entity.Propietario;
import com.desafio.tecnico.service.PropietarioService;

@Named("propietarioBean")
@SessionScoped
public class PropietarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PropietarioService propietarioService;
    
    private PropietarioDTO propietario = new PropietarioDTO();

    public PropietarioBean() {
        
    }

    public String salvar() {
        propietarioService.salvar(propietario);

        return "GestaoCasas.xhtml?faces-redirect=true";
    }

    public String entrar() {
        Propietario propietarioLogado = propietarioService.entrar(propietario);

        if (propietarioLogado != null) {
            return "GestaoCasas.xhtml?faces-redirect=true";
        }

        return "Login.xhtml";
    }

    public PropietarioDTO getPropietario() {
        return propietario;
    }

    public void setPropietario(PropietarioDTO propietario) {
        this.propietario = propietario;
    }
}
