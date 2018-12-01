/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.controller.ControlContato;
import br.com.tcc.modal.TblUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageContato")
@RequestScoped
public class ManageContato {

    private String _nome;
    private String _email;
    private String _mensagem;
    private TblUsuario _usuario;

    /**
     * Creates a new instance of ManageContato
     */
    public ManageContato() {
        _usuario = new TblUsuario();
    }

    /**
     * Método para enviar email
     *
     * @return true = enviou / false = falhou
     */
    public void enviaContato() {
        _usuario.setEmail(_email);
        _usuario.setNome(_nome);
        //
        FacesMessage msg = null;
        String erro = new ControlContato().enviaEmailContato(this._usuario, this._mensagem);
        if (erro.equalsIgnoreCase("sucesso")) {
            erro = "Mensagem enviada com sucesso!";
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, "");
        } else {
            erro = "Falha no envio de emai. Contate o administrador do sistema.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, "");
        }

        //this._usuario = new TblUsuario();
        this._email = "";
        this._mensagem = "";
        this._nome = "";
        //
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
     * @param _email
     */
    public void setEmail(String _email) {
        this._email = _email;
    }

    /**
     *
     * @return
     */
    public String getMensagem() {
        return _mensagem;
    }

    /**
     *
     * @param _mensagem
     */
    public void setMensagem(String _mensagem) {
        this._mensagem = _mensagem;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return _nome;
    }

    /**
     *
     * @param _nome
     */
    public void setNome(String _nome) {
        this._nome = _nome;
    }
}
