/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblContaPagarReceber;
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
 * @author Jp
 */
public class ControlRelatContasPag {

    private String _erro;
    List<Criterion> parameters;
    List<TblContaPagarReceber> contasPagRec;

    public ControlRelatContasPag() {
        this._erro = "";
        this.parameters = new ArrayList<Criterion>();
    }

    /**
     *
     * @param usuario
     * @param tipoMovimento
     * @param dateIni
     * @param dateEnd
     * @param datePagIni
     * @param datePagEnd
     * @param vencidas
     * @param vencer
     * @param pagas
     * @return
     */
    public List<TblContaPagarReceber> procuraContasPagas(TblUsuario usuario, TblTipoMovimento tipoMovimento,
            DateTime dateIni, DateTime dateEnd, DateTime datePagIni, DateTime datePagEnd,
            String vencidas, String vencer, String pagas) {

        if (dateIni != null) {
            parameters.add(Restrictions.ge("dataMovimento", dateIni));
        }
        //
        if (dateEnd != null) {
            parameters.add(Restrictions.le("dataMovimento", dateEnd));
        }
        //
        if (datePagIni != null) {
            parameters.add(Restrictions.ge("dataPagamento", datePagIni));
        }
        //
        if (datePagEnd != null) {
            parameters.add(Restrictions.le("dataPagamento", datePagEnd));
        }
        // eq = equals - le = menor - ge = maior
        if (pagas != null && vencidas != null && vencer != null) {
            parameters.add(Restrictions.or(Restrictions.or(Restrictions.eq("pago", "S"), Restrictions.le("dataMovimento", new DateTime())), Restrictions.ge("dataMovimento", new DateTime())));
        } else if (pagas != null && vencidas != null) {
            parameters.add(Restrictions.or(Restrictions.eq("pago", "S"), Restrictions.le("dataMovimento", new DateTime())));
        } else if (pagas != null && vencer != null) {
            parameters.add(Restrictions.or(Restrictions.eq("pago", "S"), Restrictions.ge("dataMovimento", new DateTime())));
        } else if (vencidas != null && vencer != null) {
            parameters.add(Restrictions.or(Restrictions.le("dataMovimento", new DateTime()), Restrictions.ge("dataMovimento", new DateTime())));
            parameters.add(Restrictions.eq("pago", "N"));
        } else if (vencidas != null) {
            parameters.add(Restrictions.le("dataMovimento", new DateTime()));
            parameters.add(Restrictions.eq("pago", "N"));
        } else if (pagas != null) {
            parameters.add(Restrictions.eq("pago", "S"));
        } else if (vencer != null) {
            parameters.add(Restrictions.ge("dataMovimento", new DateTime()));
            parameters.add(Restrictions.eq("pago", "N"));
        }
        //
        parameters.add(Restrictions.eq("idUsuario", usuario));
        parameters.add(Restrictions.eq("idTipoMovimento", tipoMovimento));
        //
        Order orderBy = Order.asc("dataMovimento");
        //
        contasPagRec = (List<TblContaPagarReceber>) (List<?>) new SysDAO().busca(TblContaPagarReceber.class, parameters, 0, orderBy, null);
        return contasPagRec;

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

    public BigDecimal retornaSaldoPag(List<TblContaPagarReceber> contas) {
        Double saldo = 0.0;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getPago().equals("S")) {
                saldo = saldo + contas.get(i).getValor();
            }
        }
        return this.round2Decimals(saldo);
    }

    private BigDecimal round2Decimals(double numero) {
        final BigDecimal decimal = new BigDecimal(numero);

        // numero de casas da parte inteira (para 683.0699 serão 3 casas)   
        int inteiros = decimal.precision() - decimal.scale();
        int fracao = 2;

        MathContext mathContext = new MathContext(inteiros + fracao, RoundingMode.HALF_UP);
        return decimal.round(mathContext);
    }
}
