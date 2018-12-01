/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageTabRelatContasRec")
@RequestScoped
public class ManageTabRelatContasRec {

    private TblUsuario _usuario;
    //
    private Date _dataVencimentoIni, _dataVencimentoFim;
    private Date _dataPagamentoIni, _dataPagamentoFim;
    //
    private DateTime _dt_dataVencimentoIni, _dt_dataVencimentoFim;
    private DateTime _dt_dataPagamentoIni, _dt_dataPagamentoFim;
    //
    private String _groupBy;
    //
    private List<String> _tpContas;

    public ManageTabRelatContasRec() {
        _groupBy = "Categoria";
        this._usuario = (TblUsuario) Utils.getFromContext("usuario");
        _tpContas = new ArrayList<String>();
    }

    /**
     * 
     */
    public void abreRelatório() {
        String parameters = "";
        RequestContext context = RequestContext.getCurrentInstance();

        parameters += "groupBy=" + this._groupBy;
        parameters += "&";

        if (this._dt_dataVencimentoIni != null) {
            parameters += "dataVencimentoIni=" + this._dt_dataVencimentoIni;
            parameters += "&";
        }
        if (this._dt_dataVencimentoFim != null) {
            parameters += "dataVencimentoFim=" + this._dt_dataVencimentoFim;
            parameters += "&";
        }
        if (this._dt_dataPagamentoIni != null) {
            parameters += "dataPagamentoIni=" + this._dt_dataPagamentoIni;
            parameters += "&";
        }
        if (this._dt_dataPagamentoFim != null) {
            parameters += "dataPagamentoFim=" + this._dt_dataPagamentoFim;
            parameters += "&";
        }
        if (!this._tpContas.isEmpty()) {
            for (int i = 0; i < _tpContas.size(); i++) {
                parameters += this._tpContas.get(i) + "=" + this._tpContas.get(i);
                parameters += "&";
            }
        }

        parameters = parameters.substring(0, parameters.length() - 1);

        context.execute("open_new_window('/TCC/faces/resources/relatorios/relatContasRec.xhtml?" + parameters + "', 'Relat')");

    }

    /**
     *
     * @return
     */
    public TblUsuario getUsuario() {
        return _usuario;
    }

    /**
     *
     * @param _usuario
     */
    public void setUsuario(TblUsuario _usuario) {
        this._usuario = _usuario;
    }

    /**
     *
     * @return
     */
    public Date getDataVencimentoIni() {
        return _dataVencimentoIni;
    }

    /**
     *
     * @param _dataVencimentoIni
     */
    public void setDataVencimentoIni(Date _dataVencimentoIni) {
        if (_dataVencimentoIni != null) {
            this._dt_dataVencimentoIni = new DateTime(_dataVencimentoIni);
        }
    }

    /**
     *
     * @return
     */
    public Date getDataVencimentoFim() {
        return _dataVencimentoFim;
    }

    /**
     *
     * @param _dataVencimentoFim
     */
    public void setDataVencimentoFim(Date _dataVencimentoFim) {
        if (_dataVencimentoFim != null) {
            this._dt_dataVencimentoFim = new DateTime(_dataVencimentoFim);
        }
    }

    /**
     *
     * @return
     */
    public Date getDataPagamentoIni() {
        return _dataPagamentoIni;
    }

    /**
     *
     * @param _dataPagamentoIni
     */
    public void setDataPagamentoIni(Date _dataPagamentoIni) {
        if (_dataPagamentoIni != null) {
            this._dt_dataPagamentoIni = new DateTime(_dataPagamentoIni);
        }
    }

    /**
     *
     * @return
     */
    public Date getDataPagamentoFim() {
        return _dataPagamentoFim;
    }

    /**
     *
     * @param _dataPagamentoFim
     */
    public void setDataPagamentoFim(Date _dataPagamentoFim) {
        if (_dataPagamentoFim != null) {
            this._dt_dataPagamentoFim = new DateTime(_dataPagamentoFim);
        }
    }

    /**
     *
     * @return
     */
    public String getGroupBy() {
        return _groupBy;
    }

    /**
     *
     * @param _groupBy
     */
    public void setGroupBy(String _groupBy) {
        this._groupBy = _groupBy;
    }

    /**
     *
     * @return
     */
    public List<String> getTpContas() {
        return _tpContas;
    }

    /**
     *
     * @param _tpContas
     */
    public void setTpContas(List<String> _tpContas) {
        this._tpContas = _tpContas;
    }
}
