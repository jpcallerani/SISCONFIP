/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblMovimento;
import br.com.tcc.modal.TblTipoMovimento;
import br.com.tcc.modal.TblUsuario;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class ControlRelatMovimentos {

    private String _erro;
    List<Criterion> parameters;
    List<TblMovimento> lstMovimento;

    public ControlRelatMovimentos() {
        this._erro = "";
        this.parameters = new ArrayList<Criterion>();
    }

    /**
     *
     * @param usuario
     * @param dateIni
     * @param dateEnd
     * @return
     */
    public List<TblMovimento> procuraMovimentos(TblUsuario usuario, DateTime dateIni, DateTime dateEnd) {
        
        if (dateIni != null) {
            parameters.add(Restrictions.ge("dataMovimento", dateIni));
        }
        //
        if (dateEnd != null) {
            parameters.add(Restrictions.le("dataMovimento", dateEnd));
        }

        //
        parameters.add(Restrictions.eq("idUsuario", usuario));

        //parameters.add(Restrictions.eq("idTipoMovimento", tipoMovimento));
        
        //
        Order orderBy = Order.asc("data_Movimento");
        //
        lstMovimento = (List<TblMovimento>) (List<?>) new SysDAO().busca(TblMovimento.class, parameters, 0, orderBy, null);
        return lstMovimento;

    }

    /**
     * Método para somar todas os Movimentos
     *
     * @param movimentos
     * @return
     */
    public BigDecimal retornaSaldoDebito(List<TblMovimento> mov) {
        Double saldo = 0.0;
        for (int i = 0; i < mov.size(); i++) {
            if (mov.get(i).getIdTipoMovimento().getId() == 1) {
                saldo = saldo + mov.get(i).getValor();
            }
        }
        return this.round2Decimals(saldo);
    }

    /**
     *
     * @param movimentos
     * @return
     */
    public BigDecimal retornaSaldoCredito(List<TblMovimento> mov) {
        Double saldo = 0.0;
        for (int i = 0; i < mov.size(); i++) {
            if (mov.get(i).getIdTipoMovimento().getId() == 2) {
                saldo = saldo + mov.get(i).getValor();
            }
        }
        return this.round2Decimals(saldo);
    }

    /**
     * Método para somar todas as contas a pagar
     *
     * @param contas
     * @return
     */
    public BigDecimal retornaSaldo(List<TblContaPagarReceber> contas) {
        Double saldo = 0.0;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getPago().equals("N")) {
                saldo = saldo + contas.get(i).getValor();
            }
        }
        return this.round2Decimals(saldo);
    }

    /**
     * 
     * @param contas
     * @return 
     */
    public BigDecimal retornaSaldoPag(List<TblContaPagarReceber> contas) {
        Double saldo = 0.0;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getPago().equals("S")) {
                saldo = saldo + contas.get(i).getValor();
            }
        }
        return this.round2Decimals(saldo);
    }
    
    
    /**
     *
     * @param numero
     * @return
     */
    private BigDecimal round2Decimals(double numero) {
        final BigDecimal decimal = new BigDecimal(numero);

        // numero de casas da parte inteira (para 683.0699 serão 3 casas)   
        int inteiros = decimal.precision() - decimal.scale();
        int fracao = 2;

        MathContext mathContext = new MathContext(inteiros + fracao, RoundingMode.HALF_UP);
        return decimal.round(mathContext);
    }
    
    
    
}
