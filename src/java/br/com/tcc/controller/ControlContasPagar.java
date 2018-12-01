/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblTipoMovimento;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

/**
 *
 * @author Jp
 */
public class ControlContasPagar {

    private String _erro;
    List<TblContaPagarReceber> contasPagRec;
    List<Criterion> parameters;

    public ControlContasPagar() {
        parameters = new ArrayList<Criterion>();
    }

    /**
     * Método para somar todas as contas a pagar
     * @param contas
     * @return 
     */
    public Double retornaSaldo(List<TblContaPagarReceber> contas) {
        Double saldo = 0.0;
        for (int i = 0; i < contas.size(); i++) {
            saldo = saldo + contas.get(i).getValor();
        }
        return saldo;
    }

    /**
     * Método para listar todas as contas do usuário logado.
     *
     * @param _contasReceber
     * @return
     */
    public List<TblContaPagarReceber> contasUsuario(TblUsuario usuario) {

        parameters.add(Restrictions.eq("idUsuario", usuario));
        parameters.add(Restrictions.eq("idTipoMovimento", new TblTipoMovimento(1)));
        contasPagRec = (List<TblContaPagarReceber>) (List<?>) new SysDAO().busca(TblContaPagarReceber.class, parameters, 0, null, null);

        return contasPagRec;
    }

    /**
     * Método para receber a conta.
     *
     * @param conta
     * @return
     */
    public String RecebeConta(TblContaPagarReceber contaPagRec) {
        this._erro = new SysDAO().save(contaPagRec);
        if (this._erro.equals("")) {
            this._erro = "Valor pago na conta \"" + contaPagRec.getIdConta().getNome() + "\" com sucesso.";
        }
        return this._erro;
    }

    /**
     * Método para inserção/update de uma nova conta.
     *
     * @param conta
     * @return
     */
    public String insereNovaConta(TblContaPagarReceber contaPagRec) {
        this._erro = new SysDAO().save(contaPagRec);
        if (this._erro.equals("")) {
            this._erro = "Registro inserido com sucesso.";
        }
        return this._erro;
    }

    /**
     * Método para deleção das contas.
     *
     * @param conta
     * @return
     */
    public String deletaConta(TblContaPagarReceber contaPagRec) {
        this._erro = new SysDAO().delete(contaPagRec);
        if (this._erro.equals("")) {
            this._erro = "Registro deletado com sucesso.";
        }
        return this._erro;
    }

    /**
     * Método usado para trazer as contas de acordo com o filtro selecionado.
     *
     * @param conta
     * @param usuario
     * @param dateIni
     * @param dateEnd
     * @param recebido
     * @return
     */
    public List<TblContaPagarReceber> procuraContasPorFiltro(TblConta conta, TblUsuario usuario, DateTime dateIni, DateTime dateEnd, boolean recebido) {

        if (conta != null && conta.getId() != null) {
            parameters.add(Restrictions.eq("idConta", conta));
        }
        if (dateIni != null) {
            parameters.add(Restrictions.ge("dataMovimento", dateIni));
        }
        if (dateEnd != null) {
            parameters.add(Restrictions.le("dataMovimento", dateEnd));
        }

        parameters.add(Restrictions.eq("pago", recebido == true ? "S" : "N"));
        parameters.add(Restrictions.eq("idUsuario", usuario));
        parameters.add(Restrictions.eq("idTipoMovimento", new TblTipoMovimento(1)));


        contasPagRec = (List<TblContaPagarReceber>) (List<?>) new SysDAO().busca(TblContaPagarReceber.class, parameters, 0, null, null);
        return contasPagRec;

    }
    
    /**
     *
     * @param contas
     * @return
     */
    public boolean retornaContasVencidas(List<TblContaPagarReceber> contas) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getDataMovimento().isBefore(new DateTime())) {
                return true;
            }
        }
        return false;
    }
}
