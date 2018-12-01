/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ALAN
 */
@ManagedBean(name = "ManageApresentacao")
@RequestScoped
public class ManageApresentacao {

    private String _texto;

    public ManageApresentacao() {
        setTexto("<p>O <b>Sisconfip</b> é um sistema destinado ao gerenciamento e controle</p> "
                + " financeiro pessoal e objetivo. Com ele você pode:</p> <br/>"
                + "<ul>"
                + "<li> <img src='resources/img/bullet-check.png' />  Controlar as contas a pagar e receber!</li>"
                + "<li> <img src='resources/img/bullet-check.png' />  Controlar de onde vem e para onde vai seu dinheiro!</li>"
                + "<li> <img src='resources/img/bullet-check.png' />  Obter relatórios detalhados das suas finanças!</li>"
                + "<li> <img src='resources/img/bullet-check.png' />  Controlar cartões de crédito (pagamento e recebimento) e muito mais.</ul></p><br/>"
                + "<p>Tudo voltado para o uso pessoal e totalmente gratuito, sem nenhuma</p><p> limitação.</p>");
    }

    /**
     * @return the _texto
     */
    public String getTexto() {
        return _texto;
    }

    /**
     * @param texto the _texto to set
     */
    public void setTexto(String texto) {
        this._texto = texto;
    }
}
