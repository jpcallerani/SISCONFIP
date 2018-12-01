/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.controller.ControlUsuario;
import br.com.tcc.modal.TblUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ManageUsuario")
@RequestScoped
public class ManageUsuario {

    private TblUsuario _usuario;
    private String page;

    public ManageUsuario() {
        _usuario = new TblUsuario();
        page = "index";
    }

    /**
     * 
     * @return 
     */
    public void login() {

        RequestContext context = RequestContext.getCurrentInstance();

        boolean loggedIn = false;
        FacesMessage msg = null;
        
        if (new ControlUsuario().validaLogin(this._usuario)) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem-Vindo " + this._usuario.getUsername(),
                    this._usuario.getUsername());
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos!",
                    "Usuário/Senha inválidos");
            _usuario = new TblUsuario();
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);

        context.addCallbackParam("loggedIn", loggedIn);
    }

    /**
     * @return the ManageUsuario
     */
    public TblUsuario getTblUsuario() {
        return _usuario;
    }

    /**
     * @param ManageUsuario the ManageUsuario to set
     */
    public void setTblUsuario(TblUsuario usuario) {
        this._usuario = usuario;
    }

    /**
     * 
     * @return 
     */
    public String retornaPagina(String pagina) {
        return pagina + "?faces-redirect=true";
    }
}