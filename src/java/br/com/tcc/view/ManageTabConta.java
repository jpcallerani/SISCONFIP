/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.TabItem;
import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlConta;
import br.com.tcc.modal.TblBanco;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblTipoConta;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageTabConta")
@ViewScoped
public class ManageTabConta {

    private TblUsuario _usuario;
    private TblTipoConta _tipoConta;
    private List<TabItem> _tabList;
    private List<TblConta> _contas;
    private int _activeIndex;
    private TblConta _conta;
    private TblTipoConta _tipoContaNew;
    private TblConta _contaNew;
    private HashMap<Integer, String> _dynaForm;
    private int _activeForm;
    private List<TblBanco> _bancos;
    private TblBanco _banco;
    private TblConta _selectedConta;

    public ManageTabConta() {
        this._usuario = (TblUsuario) Utils.getFromContext("usuario");
        _activeIndex = 0;
        _activeForm = 0;
        _conta = new TblConta();
        _contaNew = new TblConta();
        _selectedConta = new TblConta();
        _tipoConta = new TblTipoConta();
        _tipoContaNew = new TblTipoConta();
        _bancos = new ArrayList<TblBanco>();
        _banco = new TblBanco();
        _contas = (ArrayList<TblConta>) Utils.getFromContext("contas");
        if (this._contas == null) {
            _contas = new ArrayList<TblConta>();
        }
        _dynaForm = new HashMap<Integer, String>();
        _dynaForm.put(0, "dynamicTabConta/addDefault.xhtml");
        _dynaForm.put(1, "dynamicTabConta/addCartaoCredito.xhtml");
        _dynaForm.put(2, "dynamicTabConta/addContaCorrente.xhtml");
        _dynaForm.put(3, "dynamicTabConta/addDinheiro.xhtml");
    }

    /**
     *
     */
    public void deleteConta() {
        FacesMessage msg = null;
        String erro;
        RequestContext context = RequestContext.getCurrentInstance();
        if (this._selectedConta.getId() != null) {
            erro = new ControlConta().deleteConta(this._selectedConta);
            if (erro.toUpperCase().contains("SUCESSO")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this._contas.remove(this._selectedConta);
                Utils.setFromContext("contas", this._contas);
                context.execute("carregaInformacoes()");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

            this._selectedConta.setId(null);
        } else {
            erro = "Selecione uma linha para excluir.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     */
    public void insertNewConta() {
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();

        this._contaNew.setIdUsuario(this._usuario);
        this._contaNew.setIdTipoConta(this._tipoContaNew);

        if (this._banco.getId() != null) {
            this._contaNew.setIdBanco(this._banco);
        }

        //CARTÃO
        if (this._contaNew.getIdTipoConta().getId() == 1) {
            if (this._contaNew.getLimiteCartao().doubleValue() <= 0.0){
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"O limite do cartão deve ser maior que zero.","");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
            
        if (this._contaNew.getSaldoInicial().doubleValue() <= 0.0){
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"O saldo inicial da conta deve ser maior que zero.","");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
        }
        
        String erro = new ControlConta().insertNewConta(this._contaNew);

        if (erro.toUpperCase().contains("SUCESSO")) {
            this._contas.add(_contaNew);
            Utils.setFromContext("contas", this._contas);
            context.execute("carregaInformacoes()");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);

        this._contaNew = new TblConta();
        this._contaNew.setIdTipoConta(this._tipoContaNew);
        this._contaNew.setIdUsuario(this._usuario);
        this._banco = new TblBanco();
    }

    /**
     *
     */
    public void findContas() {
        this.setActiveIndex(1);
        this._conta.setIdTipoConta(_tipoConta);
        this._contas = new ControlConta().contasFilter(this._conta, this._usuario);
        this._conta.setNome("");
        this._tipoConta.setId(0);
        this._tipoContaNew.setId(0);
        this.setActiveForm(0);
    }

    /**
     *
     */
    public void changeDynaForm() {
        this.setActiveForm(this._tipoContaNew.getId());
        this.setActiveIndex(2);
    }

    /**
     *
     * @param evt
     */
    public void onRowSelect(SelectEvent evt) {
        this._selectedConta = (TblConta) evt.getObject();
    }

    /**
     *
     * @return
     */
    public String returnUrlForm() {
        if (this._activeForm == 2 && this._bancos.isEmpty()) {
            this._bancos = new ControlConta().returnAllBancos(_banco);
        }
        return this._dynaForm.get(this._activeForm);
    }

    /**
     *
     * @return
     */
    public List<TabItem> getTabList() {
        return _tabList;
    }

    /**
     *
     * @param _tabList
     */
    public void setTabList(List<TabItem> _tabList) {
        this._tabList = _tabList;
    }

    /**
     *
     * @return
     */
    public int getActiveIndex() {
        return _activeIndex;
    }

    /**
     *
     * @param _activeIndex
     */
    public void setActiveIndex(int _activeIndex) {
        this._activeIndex = _activeIndex;
    }

    /**
     *
     * @return
     */
    public TblConta getConta() {
        return _conta;
    }

    /**
     *
     * @param _conta
     */
    public void setConta(TblConta _conta) {
        this._conta = _conta;
    }

    /**
     *
     * @return
     */
    public TblTipoConta getTipoConta() {
        return _tipoConta;
    }

    /**
     *
     * @param teste
     */
    public void setTipoConta(TblTipoConta tipoConta) {
        this._tipoConta = tipoConta;
    }

    /**
     *
     * @return
     */
    public List<TblConta> getContas() {
        return _contas;
    }

    /**
     *
     * @param _contas
     */
    public void setContas(List<TblConta> _contas) {
        this._contas = _contas;
    }

    /**
     * Retorna o Saldo em questão
     *
     * @return
     */
    public String moneyFormat(Double valor) {
        return Utils.moneyFormat(valor);
    }

    /**
     *
     * @param date
     * @return
     */
    public String dateFormat(Date date) {
        return Utils.dateFormat(date);
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, String> getDynaForm() {
        return _dynaForm;
    }

    /**
     *
     * @param _dynaForm
     */
    public void setDynaForm(HashMap<Integer, String> _dynaForm) {
        this._dynaForm = _dynaForm;
    }

    /**
     *
     * @return
     */
    public int getActiveForm() {
        return _activeForm;
    }

    /**
     *
     * @param _activeForm
     */
    public void setActiveForm(int _activeForm) {
        this._activeForm = _activeForm;
    }

    /**
     *
     * @return
     */
    public TblTipoConta getTipoContaNew() {
        return _tipoContaNew;
    }

    /**
     *
     * @param _tipoContaNew
     */
    public void setTipoContaNew(TblTipoConta _tipoContaNew) {
        this._tipoContaNew = _tipoContaNew;
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
    public TblConta getContaNew() {
        return _contaNew;
    }

    /**
     *
     * @param _contaNew
     */
    public void setContaNew(TblConta _contaNew) {
        this._contaNew = _contaNew;
    }

    /**
     *
     * @return
     */
    public List<TblBanco> getBancos() {
        return _bancos;
    }

    /**
     *
     * @param _banco
     */
    public void setBancos(List<TblBanco> _bancos) {
        this._bancos = _bancos;
    }

    /**
     *
     * @return
     */
    public TblBanco getBanco() {
        return _banco;
    }

    /**
     *
     * @param _banco
     */
    public void setBanco(TblBanco _banco) {
        this._banco = _banco;
    }

    /**
     *
     * @return
     */
    public TblConta getSelectedConta() {
        return _selectedConta;
    }

    /*
     * 
     */
    public void setSelectedConta(TblConta _selectedConta) {
        this._selectedConta = _selectedConta;
    }
}
