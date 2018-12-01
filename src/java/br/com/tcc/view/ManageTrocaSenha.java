/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.controller.ControlTrocaSenha;
import br.com.tcc.modal.TblUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageTrocaSenha")
@RequestScoped
public class ManageTrocaSenha {

    TblUsuario _usuario;
    private String _email;

    /**
     * Creates a new instance of ManageTrocaSenha
     */
    public ManageTrocaSenha() {
        this._usuario = new TblUsuario();
    }

    /**
     * Método que troca a senha
     */
    public void trocaSenha() {
        this._usuario.setEmail(this._email);
        //
        FacesMessage msg = null;
        String erro = new ControlTrocaSenha().recuperaSenha(this._usuario);
        if (erro.equals("")) {
            erro = "Sua Senha foi enviada para o email " + this._usuario.getEmail();
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, "");
        } else {
            erro = "Email não encontrado no nosso sistema.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, "");
        }
        this._email = "";
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return _email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this._email = email;
    }
}
