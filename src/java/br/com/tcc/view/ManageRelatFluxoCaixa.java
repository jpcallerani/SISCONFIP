/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlRelatContasPag;
import br.com.tcc.controller.ControlRelatContasRec;
import br.com.tcc.controller.ControlRelatMovimentos;
import br.com.tcc.controller.ControlMovimento;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblMovimento;
import br.com.tcc.modal.TblTipoMovimento;
import br.com.tcc.modal.TblUsuario;
import com.google.common.collect.ArrayListMultimap;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;

/**
 *
 * @author Alan
 */
@ManagedBean(name = "ManageRelatoFluxoCaixa")
@RequestScoped
public class ManageRelatFluxoCaixa {
    //

    private List<TblContaPagarReceber> _tblContasPag;
    private List<TblMovimento> _tblMovimentos;
    private List<TblContaPagarReceber> _tblContasRec;
    //
    private TblUsuario _usuario;
    private DateTime _dt_PeriodoIni, _dt_PeriodoFim;
    private String _groupBy;
    //
    private ArrayListMultimap<Object, TblContaPagarReceber> map;
    //

    public ManageRelatFluxoCaixa() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //
        this._tblContasPag = new ArrayList<TblContaPagarReceber>();
        //
        this._usuario = (TblUsuario) Utils.getFromContext("usuario");
        //
        this._groupBy = facesContext.getExternalContext().getRequestParameterMap().get("groupBy");
        //
        map = ArrayListMultimap.create();
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataPeriodoIni") != null) {
            this._dt_PeriodoIni = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataPeriodoIni"));
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataPeriodoFim") != null) {
            this._dt_PeriodoFim = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataPeriodoFim"));
        }
        //
        this._tblMovimentos = new ArrayList<TblMovimento>();
    }

    @PostConstruct
    public void init() throws IOException {
        Object key;

        // CONTAS A PAGAR
        _tblContasPag = new ControlRelatContasPag().procuraContasPagas(_usuario,
                new TblTipoMovimento(1),
                _dt_PeriodoIni,
                _dt_PeriodoFim,
                null,
                null,
                null,
                null,
                null);

        for (TblContaPagarReceber tblConta : _tblContasPag) {
            if (!this._groupBy.equals("Categoria")) {
                key = tblConta.getDataMovimento();
            } else {
                if (tblConta.getIdCategoria() == null) {
                    key = "(Sem Categoria)";
                } else {
                    key = tblConta.getIdCategoria().getNome();
                }
            }
            map.put(key, tblConta);
        }

        // CONTAS A RECEBER
        _tblContasRec = new ControlRelatContasRec().procuraContasPagas(_usuario,
                new TblTipoMovimento(2),
                _dt_PeriodoIni,
                _dt_PeriodoFim,
                null,
                null,
                null,
                null,
                null);
        for (TblContaPagarReceber tblConta : _tblContasRec) {
            if (!this._groupBy.equals("Categoria")) {
                key = tblConta.getDataMovimento();
            } else {
                if (tblConta.getIdCategoria() == null) {
                    key = "(Sem Categoria)";
                } else {
                    key = tblConta.getIdCategoria().getNome();
                }
            }
            map.put(key, tblConta);
        }


        this._tblMovimentos = new ControlMovimento().PesquisaMovimentos(null, this._usuario,
                _dt_PeriodoIni,
                _dt_PeriodoFim);

        if (this._tblMovimentos != null) {
            for (int i = 0; i < this._tblMovimentos.size(); i++) {
                TblContaPagarReceber tblConta = new TblContaPagarReceber();

                tblConta.setId(this._tblMovimentos.get(i).getId());
                // VERIFICAR
                tblConta.setNumeroDocumento(this._tblMovimentos.get(i).getNumeroDocumento());
                tblConta.setDescricao(this._tblMovimentos.get(i).getDescricao());
                tblConta.setIdTipoMovimento(new TblTipoMovimento(this._tblMovimentos.get(i).getIdTipoMovimento().getId()));
                tblConta.setDataMovimento(new DateTime(this._tblMovimentos.get(i).getDataMovimento()));

                tblConta.setValor(this._tblMovimentos.get(i).getValor());

                if (!this._groupBy.equals("Categoria")) {
                    key = tblConta.getDataMovimento();
                } else {
                    if (tblConta.getIdCategoria() == null) {
                        key = "(Sem Categoria)";
                    } else {
                        key = tblConta.getIdCategoria().getNome();
                    }
                }
                map.put(key, tblConta);
            }
        } else {
            this._tblMovimentos = new ArrayList<TblMovimento>();
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public List<TblContaPagarReceber> retornContas(Object data) {
        //## VALIDAR SE O TIPO E MOVIMENTO TBM
        return (List<TblContaPagarReceber>) this.map.get(data);
    }

    /**
     *
     * @return Object
     */
    public List<Object> retornaKeys() {
        List<Object> dateTimeAux = new ArrayList<Object>();

        Set s = map.keySet();

        Iterator e = s.iterator();

        while (e.hasNext()) {
            dateTimeAux.add((Object) e.next());
        }
        return dateTimeAux;
    }

    /**
     *
     * @return
     */
    public String retorna_data() {
        DateFormat dfmt = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
        Date hoje = Calendar.getInstance(Locale.getDefault()).getTime();
        return dfmt.format(hoje);
    }

    /**
     *
     * @param data
     * @return
     */
    public String formataData(DateTime data) {
        return Utils.dateTimeFormat(data);
    }

    /**
     *
     * @param data
     * @return
     */
    public String formataValor(Double valor) {
        return Utils.moneyFormat(valor);
    }

    /**
     *
     * @return
     */
    public String retornaSaldoPAGO() {
        Double saldo = new Double(0.0);

        saldo = (new ControlRelatContasRec().retornaSaldo(_tblContasPag)).doubleValue() + (new ControlRelatMovimentos().retornaSaldoDebito(_tblMovimentos)).doubleValue();

        return Utils.moneyFormat(saldo);
    }

    /**
     *
     * @return
     */
    public String retornaSaldoRECEBIDO() {
        Double saldo = 0.0;
        saldo = (new ControlRelatContasRec().retornaSaldo(_tblContasRec)).doubleValue() + (new ControlRelatMovimentos().retornaSaldoCredito(_tblMovimentos)).doubleValue();
        return Utils.moneyFormat(saldo);
    }

    /**
     *
     * @return
     */
    public List<TblContaPagarReceber> getTblContasPag() {
        return _tblContasPag;
    }

    /**
     *
     * @param _tblContasPag
     */
    public void setTblContasPag(List<TblContaPagarReceber> _tblContasPag) {
        this._tblContasPag = _tblContasPag;
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
    public ArrayListMultimap<Object, TblContaPagarReceber> getMap() {
        return map;
    }

    /**
     *
     * @param map
     */
    public void setMap(ArrayListMultimap<Object, TblContaPagarReceber> map) {
        this.map = map;
    }

    /**
     * @return the _dt_PeriodoIni
     */
    public DateTime getDt_PeriodoIni() {
        return _dt_PeriodoIni;
    }

    /**
     * @param dt_PeriodoIni the _dt_PeriodoIni to set
     */
    public void setDt_PeriodoIni(DateTime dt_PeriodoIni) {
        this._dt_PeriodoIni = dt_PeriodoIni;
    }

    /**
     * @return the _dt_PeriodoFim
     */
    public DateTime getDt_PeriodoFim() {
        return _dt_PeriodoFim;
    }

    /**
     * @param dt_PeriodoFim the _dt_PeriodoFim to set
     */
    public void setDt_PeriodoFim(DateTime dt_PeriodoFim) {
        this._dt_PeriodoFim = dt_PeriodoFim;
    }

    /**
     * @return the _tblContasRec
     */
    public List<TblContaPagarReceber> getTblContasRec() {
        return _tblContasRec;
    }

    /**
     * @param tblContasRec the _tblContasRec to set
     */
    public void setTblContasRec(List<TblContaPagarReceber> tblContasRec) {
        this._tblContasRec = tblContasRec;
    }
}
