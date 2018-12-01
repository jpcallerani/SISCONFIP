/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.Utils.Mail;
import br.com.tcc.modal.TblUsuario;

/**
 *
 * @author Jp
 */
public class ControlContato {

    private String _erro;

    public ControlContato() {
        this._erro = "";
    }

    public String enviaEmailContato(TblUsuario usuario, String mensagem) {
        StringBuilder sMensagem;
        sMensagem = new StringBuilder();
        //
        sMensagem.append("Nome: ");
        sMensagem.append(usuario.getNome());
        sMensagem.append("<br><br>");
        sMensagem.append(mensagem.replace("\n", "<br>"));
        //
        if (Mail.enviaEmail(usuario, sMensagem, "contato")) {
            this._erro = "sucesso";
        } else {
            this._erro = "erro";
        }

        
        return this._erro;
    }
}
