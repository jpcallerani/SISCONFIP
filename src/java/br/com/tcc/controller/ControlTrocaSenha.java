/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.Utils.Mail;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jp
 */
public class ControlTrocaSenha {

    TblUsuario _usuario;
    private String _erro;
    List<Criterion> parameters;

    public ControlTrocaSenha() {
        this._erro = "";
        parameters = new ArrayList<Criterion>();
    }

    /**
     * Método que recupera a senha do usuário no sistema.
     * @return
     */
    public String recuperaSenha(TblUsuario usuario) {

        parameters.add(Restrictions.eq("email", usuario.getEmail()));

        try {
            this._usuario = (TblUsuario) new SysDAO().busca(TblUsuario.class, parameters, 0, null, null).get(0);
        } catch (Exception e) {
            this._usuario = null;
        }

        if (this._usuario != null) {
            StringBuilder msg = new StringBuilder();
            msg.append("Nome: " + this._usuario.getNome() + "<br>");
            msg.append("Email: " + this._usuario.getEmail() + "<br>");
            msg.append("Username: " + this._usuario.getUsername() + "<br>");
            msg.append("Senha: " + this._usuario.getPassword() + "<br>");
            Mail.enviaEmail(this._usuario, msg, "trocaSenha");
            return "";
        } else {
            return "Email não encontrado!";
        }
    }
}
