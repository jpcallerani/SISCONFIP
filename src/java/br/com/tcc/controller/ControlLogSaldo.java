/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.controller;

import br.com.tcc.DAO.SysDAO;
import br.com.tcc.modal.TblLogSaldo;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jp
 */
public class ControlLogSaldo {

    private String _erro;
    List<TblLogSaldo> _logSaldo;
    List<Criterion> parameters;

    public ControlLogSaldo() {
        parameters = new ArrayList<Criterion>();
        _logSaldo = new ArrayList<TblLogSaldo>();
    }

    
    
    public List<TblLogSaldo> carregaLogSaldo(TblUsuario usuario) {
        
        parameters.add(Restrictions.eq("idUsuario", usuario));
        
        _logSaldo = (List<TblLogSaldo>) (List<?>) new SysDAO().busca(TblLogSaldo.class, parameters, 10, Order.asc("dataAtualizacao"), null);
        
        return _logSaldo;
    }
}
