/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblBanco;
import br.com.tcc.modal.TblCategoria;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblMovimento;
import br.com.tcc.modal.TblTipoMovimento;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

/**
 *
 * @author Alan
 */
public class ControlMovimento {

    private String _erro;
    List<Criterion> parameters;
    List<TblMovimento> tblMovimentos;

    public ControlMovimento() {
        this.parameters = new ArrayList<Criterion>();
        this.tblMovimentos = new ArrayList<TblMovimento>();
    }

    /**
     * Método para listar todas as contas do usuário logado.
     *
     * @param _contasReceber
     * @return
     */
    public List<TblMovimento> movimentosUsuario(TblUsuario usuario) {

        parameters.add(Restrictions.eq("idUsuario", usuario));
        tblMovimentos = (List<TblMovimento>) (List<?>) new SysDAO().busca(TblMovimento.class, parameters, 0, null, null);

        return tblMovimentos;
    }

    /**
     * Retorna todas as movimentos do usuário.
     *
     * @param movimento
     * @return
     */
    public List<TblMovimento> returnMovimentosFilter(TblMovimento movimento) {
        List<TblMovimento> movimentos = (List<TblMovimento>) (List<?>) new SysDAO().returnMovimentosFilter(movimento);
        return movimentos;
    }

    /**
     *
     * @param banco
     * @return
     */
    public List<TblBanco> returnAllBancos(TblBanco banco) {
        List<TblBanco> bancos = (List<TblBanco>) (List<?>) new SysDAO().listagem(banco);
        return bancos;
    }

    /**
     *
     * @param @return
     */
    public List<TblCategoria> returnAllCategorias() {
        List<TblCategoria> categorias = (List<TblCategoria>) (List<?>) new SysDAO().listagem(new TblCategoria());
        return categorias;
    }

    /**
     * Método para inserção/update de uma nova conta.
     *
     * @param conta
     * @return
     */
    public String insereNovoMovimento(TblMovimento movimento) {
        this._erro = new SysDAO().save(movimento);
        if (this._erro.equals("")) {
            this._erro = "Registro inserido com sucesso.";
        }
        return this._erro;
    }

    /**
     *
     * @param usuario
     * @return
     */
    public String updateMovimento(TblMovimento movimento) {
        boolean mov = new SysDAO().update(movimento);
        if (mov) {
            this._erro = "Registro atualizado com sucesso.";
        } else {
            this._erro = "Erro ao atualizar movimento.";
        }
        return this._erro;
    }

     /**
     * Método para deleção das contas.
     *
     * @param conta
     * @return
     */
    public String deletaConta(TblMovimento movimento) {
        this._erro = new SysDAO().delete(movimento);
        if (this._erro.equals("")) {
            this._erro = "Registro deletado com sucesso.";
        }
        return this._erro;
    }

    public List<TblMovimento> PesquisaMovimentos(TblConta conta, 
            TblUsuario usuario,
            DateTime dateIni,
            DateTime dateEnd) {

        if (conta != null) {
            parameters.add(Restrictions.eq("IdConta", conta));
        }
        
        if (dateIni != null) {
            parameters.add(Restrictions.ge("dataMovimento", dateIni));
        }
        //
        if (dateEnd != null) {
            parameters.add(Restrictions.le("dataMovimento", dateEnd));
        }

        //
        parameters.add(Restrictions.eq("idUsuario", usuario));
        //
        Order orderBy = Order.asc("dataMovimento");
        //
        tblMovimentos = (List<TblMovimento>) (List<?>) new SysDAO().busca(TblMovimento.class, parameters, 0, orderBy, null);
        return tblMovimentos;

    }
}
