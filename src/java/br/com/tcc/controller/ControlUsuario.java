/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.Utils.Mail;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jp
 */
public class ControlUsuario {

    private String _erro;
    private boolean _validacao;
    private TblUsuario _usuario;
    List<Criterion> parameters;

    public ControlUsuario() {
        _usuario = new TblUsuario();
        _validacao = false;
        _erro = "";
        parameters = new ArrayList<Criterion>();
    }

    /**
     * Método para geração de senha automatica
     *
     * @return Senha
     */
    private String geraPassword() {
        int n = rand(5, 8);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) rand('a', 'z');
        }
        return new String(b, 0);
    }

    /**
     * Método para calculo da senha
     *
     * @param tamanho minimo
     * @param tamanho máximo
     * @return
     */
    private int rand(int lo, int hi) {
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt(n);
        if (i < 0) {
            i = -i;
        }
        return lo + i;
    }

    /**
     * 
     * @param usuario
     * @return 
     */
    public String deletaUsuario(TblUsuario usuario) {
        this._erro = new SysDAO().delete(usuario);        
        if (this._erro.equals("")) {
            this._erro = "Usuário deletado com sucesso!";
        }
        return this._erro;
    }
    
    /**
     *
     * @param usuario
     * @return
     */
    public String alteraUsuario(TblUsuario usuario) {
        this._erro = new SysDAO().save(usuario);
        if (this._erro.contains("AK_EMAIL")) {
            this._erro = "Email já cadastrado! Tente novamente.";
        } else if (this._erro.equals("")) {
            this._erro = "Usuário alterado com sucesso!";
        }
        return this._erro;
    }

    /**
     *
     * @param usuario
     * @return
     */
    public String insertNewUser(TblUsuario usuario) {
        usuario.setPassword(this.geraPassword());
        usuario.setPrimeiraVez("S");
        usuario.setDataCriacao(new Date());
        this._erro = new SysDAO().save(usuario);
        if (this._erro.contains("AK_USERNAME")) {
            this._erro = "Usuário já cadastrado!\nTente novamente.";
        } else if (this._erro.contains("AK_EMAIL")) {
            this._erro = "Email já cadastrado!\nTente novamente.";
        } else if (this._erro.equals("")) {
            this._erro = "Usuário Cadastrado com sucesso!\nFoi enviado uma senha no seu email!";
            Mail.enviaEmail(usuario, new StringBuilder("Obrigado por usar nosso sistema.<br><br>Seus dados para acesso são: <br>Usuário: " + usuario.getUsername() + "<br>Senha: " + usuario.getPassword()), "cadastro");
        }
        return this._erro;
    }

    /**
     *
     * @param Modal
     * @return
     */
    public boolean validaLogin(TblUsuario usuario) {

        parameters.add(Restrictions.eq("username", usuario.getUsername()));
        parameters.add(Restrictions.eq("password", usuario.getPassword()));

        try {
            this._usuario = (TblUsuario) new SysDAO().busca(TblUsuario.class, parameters, 0, null, null).get(0);
        } catch (Exception e) {
            this._usuario = null;
        }

        if (this._usuario != null) {
            if (this._usuario.getUsername().equalsIgnoreCase(usuario.getUsername()) && this._usuario.getPassword().equalsIgnoreCase(usuario.getPassword())) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("usuario", this._usuario);
                _validacao = true;
            }
        }
        return _validacao;
    }
}
