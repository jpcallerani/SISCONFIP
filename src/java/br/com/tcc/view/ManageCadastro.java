/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.controller.ControlUsuario;
import br.com.tcc.modal.TblUsuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageCadastro")
@RequestScoped
public class ManageCadastro {

    private StringBuilder _termosUso;
    private boolean bol_termosUso;
    private TblUsuario _usuario;

    /**
     * 
     */
    public ManageCadastro() {
        _termosUso = new StringBuilder();
        this._usuario = new TblUsuario();
    }

    /**
     * 
     * @return 
     */
    public String CadastrarNovoUsuario() {
        FacesMessage msg = null;
        if (!this.bol_termosUso) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Obrigatório aceitar os termos de uso!","");
        } else {
            String erro = new ControlUsuario().insertNewUser(this._usuario);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, "");
            this._usuario = new TblUsuario();
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "index";
    }

    /**
     * 
     * @return 
     */
    public StringBuilder get_termosUso() {
        File termos;
        try {
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = context.getRealPath("");
            termos = new File(path, "termos.txt");
            FileReader fileReader = new FileReader(termos);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                _termosUso.append(linha + "\r\n");
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _termosUso;
    }

    /**
     * 
     * @param v_s_termosUso 
     */
    public void set_termosUso(StringBuilder _termosUso) {
        this._termosUso = _termosUso;
    }

    /**
     * 
     * @return 
     */
    public boolean isBol_termosUso() {
        return bol_termosUso;
    }

    /**
     * 
     * @param bol_termosUso 
     */
    public void setBol_termosUso(boolean bol_termosUso) {
        this.bol_termosUso = bol_termosUso;
    }

    /**
     * 
     * @return 
     */
    public TblUsuario getUsuario() {
        return _usuario;
    }

    /**
     * 
     * @param _usuario 
     */
    public void setUsuario(TblUsuario _usuario) {
        this._usuario = _usuario;
    }
}