/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblBanco;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblTipoConta;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jp
 */
public class ControlConta {

    private String _erro;

    /**
     *
     * @param conta
     * @return
     */
    public List<TblConta> contasFilter(TblConta conta, TblUsuario usuario) {

        List<Criterion> list = new ArrayList<Criterion>();

        if (conta != null ) {
            if (conta.getIdTipoConta() != null && conta.getIdTipoConta().getId() != 0) {
                list.add(Restrictions.eq("idTipoConta", conta.getIdTipoConta()));
            }
            if (conta.getNome() != null && !conta.getNome().equals("")) {
                list.add(Restrictions.ilike("nome", "%" + conta.getNome() + "%"));
            }
        }

        list.add(Restrictions.eq("idUsuario", usuario));
        
        List<TblConta> contas = (List<TblConta>) (List<?>) new SysDAO().busca(TblConta.class, list, 0, null, null);
        return contas;
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
     * @param usuario
     * @return
     */
    public String insertNewConta(TblConta conta) {
        this._erro = new SysDAO().save(conta);
        if (this._erro.equals("")) {
            this._erro = "Registro inserido com sucesso.";
        }
        return this._erro;
    }

    /**
     *
     * @param conta
     * @return
     */
    public String deleteConta(TblConta conta) {
        this._erro = new SysDAO().delete(conta);
        if (this._erro.equals("")) {
            this._erro = "Registro deletado com sucesso.";
        }
        return this._erro;
    }

    /**
     * Retorna todos os tipos de contas disponíveis
     *
     * @return
     */
    public List<TblTipoConta> returnTipoContas() {
        List<TblTipoConta> contas = new SysDAO().listagem(new TblTipoConta());
        return contas;
    }

    /**
     * Retorna a soma do saldo de todas as contas.
     *
     * @param contas
     * @return
     */
    public Double retornaSaldo(List<TblConta> contas) {
        Double saldo = 0.0;
        for (int i = 0; i < contas.size(); i++) {
            saldo = saldo + contas.get(i).getSaldoInicial();
        }
        return saldo;
    }

    /**
     *
     * @param id
     * @return
     */
    public TblConta getById(Integer id) {
        return (TblConta) new SysDAO().getById(TblConta.class, id);
    }
}
