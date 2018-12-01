/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlRelatContasPag;
import br.com.tcc.modal.TblContaPagarReceber;
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
 * @author Jp
 */
@ManagedBean(name = "ManageRelatContasPag")
@RequestScoped
public class ManageRelatContasPag {
    //

    private List<TblContaPagarReceber> _tblContasPag;
    //
    private TblUsuario _usuario;
    //
    private DateTime _dt_dataVencimentoIni, _dt_dataVencimentoFim;
    private DateTime _dt_dataPagamentoIni, _dt_dataPagamentoFim;
    //
    private String _groupBy;
    private String _pagas;
    private String _vencer;
    private String _vencidas;
    //
    private ArrayListMultimap<Object, TblContaPagarReceber> map;
    //

    public ManageRelatContasPag() {
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
        if (facesContext.getExternalContext().getRequestParameterMap().get("Vencidas") != null) {
            this._vencidas = (String) facesContext.getExternalContext().getRequestParameterMap().get("Vencidas");
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("Vencer") != null) {
            this._vencer = (String) facesContext.getExternalContext().getRequestParameterMap().get("Vencer");
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("Pagas") != null) {
            this._pagas = (String) facesContext.getExternalContext().getRequestParameterMap().get("Pagas");
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataVencimentoIni") != null) {
            this._dt_dataVencimentoIni = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataVencimentoIni"));
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataVencimentoFim") != null) {
            this._dt_dataVencimentoFim = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataVencimentoFim"));
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataPagamentoIni") != null) {
            this._dt_dataPagamentoIni = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataPagamentoIni"));
        }
        //
        if (facesContext.getExternalContext().getRequestParameterMap().get("dataPagamentoFim") != null) {
            this._dt_dataPagamentoFim = new DateTime(facesContext.getExternalContext().getRequestParameterMap().get("dataPagamentoFim"));
        }
        //
    }

    @PostConstruct
    public void init() throws IOException {
        _tblContasPag = new ControlRelatContasPag()
                .procuraContasPagas(_usuario, new TblTipoMovimento(1), _dt_dataVencimentoIni, _dt_dataVencimentoFim, _dt_dataPagamentoIni, _dt_dataPagamentoFim, this._vencidas, this._vencer, this._pagas);
        Object key;
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
    }

    /**
     *
     * @param data
     * @return
     */
    public List<TblContaPagarReceber> retornContas(Object data) {
        return (List<TblContaPagarReceber>) this.map.get(data);
    }

    /**
     *
     * @return
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
    public String retornaSaldo() {
        return Utils.moneyFormat(new ControlRelatContasPag().retornaSaldo(_tblContasPag));
    }

    /**
     *
     * @return
     */
    public String retornaSaldoPag() {
        return Utils.moneyFormat(new ControlRelatContasPag().retornaSaldoPag(_tblContasPag));
    }

    /**
     *
     * @return
     */
    public DateTime getDt_dataVencimentoIni() {
        return _dt_dataVencimentoIni;
    }

    /**
     *
     * @param _dt_dataVencimentoIni
     */
    public void setDt_dataVencimentoIni(DateTime _dt_dataVencimentoIni) {
        this._dt_dataVencimentoIni = _dt_dataVencimentoIni;
    }

    /**
     *
     * @return
     */
    public DateTime getDt_dataVencimentoFim() {
        return _dt_dataVencimentoFim;
    }

    /**
     *
     * @param _dt_dataVencimentoFim
     */
    public void setDt_dataVencimentoFim(DateTime _dt_dataVencimentoFim) {
        this._dt_dataVencimentoFim = _dt_dataVencimentoFim;
    }

    /**
     *
     * @return
     */
    public DateTime getDt_dataPagamentoIni() {
        return _dt_dataPagamentoIni;
    }

    /**
     *
     * @param _dt_dataPagamentoIni
     */
    public void setDt_dataPagamentoIni(DateTime _dt_dataPagamentoIni) {
        this._dt_dataPagamentoIni = _dt_dataPagamentoIni;
    }

    /**
     *
     * @return
     */
    public DateTime getDt_dataPagamentoFim() {
        return _dt_dataPagamentoFim;
    }

    /**
     *
     * @param _dt_dataPagamentoFim
     */
    public void setDt_dataPagamentoFim(DateTime _dt_dataPagamentoFim) {
        this._dt_dataPagamentoFim = _dt_dataPagamentoFim;
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
    public String getPagas() {
        return _pagas;
    }

    /**
     *
     * @param _pagas
     */
    public void setPagas(String _pagas) {
        this._pagas = _pagas;
    }

    /**
     *
     * @return
     */
    public String getVencer() {
        return _vencer;
    }

    /**
     *
     * @param _vencer
     */
    public void setVencer(String _vencer) {
        this._vencer = _vencer;
    }

    /**
     *
     * @return
     */
    public String getVencidas() {
        return _vencidas;
    }

    /**
     *
     * @param _vencidas
     */
    public void setVencidas(String _vencidas) {
        this._vencidas = _vencidas;
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
}
