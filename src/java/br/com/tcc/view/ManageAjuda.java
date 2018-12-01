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
@ManagedBean(name = "ManageAjuda")
@RequestScoped
public class ManageAjuda {

    private String _texto;

    public ManageAjuda() {
        setTexto("<p>Na central de ajuda do <b>SISCONFIP</b> você aprende a utilizar todos os recursos <br/>"
                + " disponíveis.</p> <br/>"
                + "<ul>"
                 + "<li> <img src='resources/img/idea2.png' />                                 <p:lightBox>                                    <h:outputLink value='#'>                                        <h:outputText value='Como cadastrar e remover uma Conta Banária/Cartão de Crédito?'/>                                    </h:outputLink>                                    <f:facet name='inline'>                                        <video controls='controls'>                                            <source src='resources/videos/conta.mp4' type='video/mp4' autoplay='false'/>                                        </video>                                    </f:facet>                                </p:lightBox></li>"
                + "</ul><br/>"
                + "<p>Caso ainda exista dúvida entre em contato.</p>");
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
