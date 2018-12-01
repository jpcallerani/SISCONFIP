/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.Utils;

import br.com.tcc.modal.TblContaPagarReceber;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Jp
 */
public class Utils {

    /**
     *
     * @param nome
     * @return
     */
    public static String getCaminhoImage(String nome) {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = context.getRealPath("");
        File img = new File(path, nome);
        return img.getPath();
    }

    /**
     * Formata qualquer valor double para o padrão monetário local
     *
     * @param valor
     * @return
     */
    public static String moneyFormat(Double valor) {
        String formatedValue;
        if (!valor.equals("")) {
            formatedValue = NumberFormat.getCurrencyInstance().format(valor);
        } else {
            formatedValue = "";
        }
        return formatedValue;
    }

    /**
     * Formata qualquer valor double para o padrão monetário local
     *
     * @param valor
     * @return
     */
    public static String moneyFormat(BigDecimal valor) {
        String formatedValue;
        if (!valor.equals("")) {
            formatedValue = NumberFormat.getCurrencyInstance().format(valor);
        } else {
            formatedValue = "";
        }
        return formatedValue;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        String dateFormated = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateFormated = sdf.format(date);
        }
        return dateFormated;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String dateTimeFormat(DateTime date) {
        String dateFormated = "";
        if (date != null) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
            dateFormated = fmt.print(date);
        }
        return dateFormated;
    }

    /**
     * Retorna do context a váriavel chamado do parâmetro.
     *
     * @param objeto
     * @return Váriavel do contexto.
     */
    public static Object getFromContext(String objeto) {
        Object obj;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            obj = session.getAttribute(objeto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            obj = null;
        }
        return obj;
    }

    /**
     * Remove a varíavel da sessão
     *
     * @param objeto
     */
    public static void removeFromContext(String objeto) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            session.removeAttribute(objeto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adiciona um objeto no context
     *
     * @param nome Nome do objeto
     * @param obj Objeto a ser gravado.
     */
    public static void setFromContext(String nome, Object obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.setAttribute(nome, obj);
    }
}
