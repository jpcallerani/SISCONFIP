package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlCategoria;
import br.com.tcc.controller.ControlConta;
import br.com.tcc.controller.ControlContasPagar;
import br.com.tcc.modal.TblCategoria;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblTipoMovimento;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageTabContasPagar")
@ViewScoped
public class ManageTabContasPagar {

    private List<TblContaPagarReceber> _contasPagar;
    private List<TblContaPagarReceber> _contasPagarContext;
    private List<TblConta> _contas;
    private TblUsuario _usuario;
    private TblConta _conta;
    private String action;
    //
    // Variáveis para utilização de filtro
    //
    private DateTime _dateIniFiltro, _dateEndFiltro;
    private TblConta _contaFiltro;
    private boolean _auxAbrir;
    private boolean _pago = false;
    //
    // Variáveis para o cadastro da conta via schedule;
    //
    private ScheduleEvent _event;
    private ScheduleModel _eventModel;
    private TblConta _newConta;
    private TblContaPagarReceber _newContaPagRec;
    private TblContaPagarReceber _selectedContaPagRec;
    private TblCategoria _newCategoria;
    private List<TblCategoria> _categorias;
    private List<TblContaPagarReceber> _newContasPagRec;
    private DateTime _selectedDate;
    //

    public ManageTabContasPagar() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        this._usuario = (TblUsuario) session.getAttribute("usuario");
        this._contasPagar = new ArrayList<TblContaPagarReceber>();
        this._contas = new ArrayList<TblConta>();
        this._auxAbrir = true;
        this._newConta = new TblConta();
        this._newContaPagRec = new TblContaPagarReceber();
        this._contasPagarContext = new ArrayList<TblContaPagarReceber>();
        this._newCategoria = new TblCategoria();
        this._selectedContaPagRec = new TblContaPagarReceber();
        this._eventModel = new DefaultScheduleModel();
        this._categorias = new ArrayList<TblCategoria>();
        this._newContasPagRec = new ArrayList<TblContaPagarReceber>();
        this._contasPagar = new ControlContasPagar().contasUsuario(this._usuario);
    }

    @PostConstruct
    public void init() {
        this.returnEvents();
        this.findCategorias();
        this.findAllContas();
    }

    /**
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     */
    private void findCategorias() {
        this._categorias = new ControlCategoria().buscaCategoriasPagar();
    }

    /**
     *
     */
    public void findAllContas() {
        this._contas = new ControlConta().contasFilter(new TblConta(), this._usuario);
    }

    /**
     * Procura as contas ao expandir o filtro
     */
    public void findContas() {
        if (this._auxAbrir) {
            this._auxAbrir = false;
            this.findAllContas();
        } else {
            this._auxAbrir = true;
        }
    }

    /**
     *
     * @return
     */
    private void returnEvents() {
        for (int i = 0; i < _contasPagar.size(); i++) {
            TblContaPagarReceber tblContaPagarReceber = _contasPagar.get(i);
            this._eventModel.addEvent(new DefaultScheduleEvent(tblContaPagarReceber.getDescricao(), tblContaPagarReceber.getDataMovimento().toDate(), tblContaPagarReceber.getDataMovimento().toDate()));
        }
    }

    /**
     * Executa a consulta do filtro
     */
    public void findContasPagRecByFilter() {
        this._contasPagar = new ControlContasPagar().procuraContasPorFiltro(this._contaFiltro, this._usuario, this._dateIniFiltro, this._dateEndFiltro, this._pago);
    }

    /**
     *
     * @param selectEvent
     */
    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        _event = selectEvent.getScheduleEvent();
    }

    /**
     *
     * @param selectEvent
     */
    public void onDateSelect(DateSelectEvent selectEvent) {
        this._selectedDate = new DateTime(selectEvent.getDate());
    }

    /**
     *
     * @param event
     */
    public void dateIniSelect(DateSelectEvent event) {
        this._dateIniFiltro = new DateTime(event.getDate());
    }

    /**
     *
     * @param event
     */
    public void dateEndSelect(DateSelectEvent event) {
        this._dateEndFiltro = new DateTime(event.getDate());
    }

    /**
     *
     * @param event
     */
    public void dateIniFiltro(DateSelectEvent event) {
        this._dateIniFiltro = new DateTime(event.getDate());
    }

    /**
     *
     * @param event
     */
    public void dateEndFiltro(DateSelectEvent event) {
        this._dateEndFiltro = new DateTime(event.getDate());
    }

    /**
     * Retorna a soma dos recebimentos ainda não recebidas
     */
    public String returnWorthRec() {
        Double valor = 0.0;
        if (this._contasPagar != null) {
            for (int i = 0; i < _contasPagar.size(); i++) {
                TblContaPagarReceber contaPagar = _contasPagar.get(i);
                if (contaPagar.getPago().equals("N")) {
                    valor = valor + contaPagar.getValor();
                }
            }
        }
        return Utils.moneyFormat(valor);
    }

    /**
     * 
     * @param contasPagRec
     * @param acao 
     */
    private void updateContasNaoPagasContexto(TblContaPagarReceber contasPagRec, String acao) {
        RequestContext context = RequestContext.getCurrentInstance();
        this._contasPagarContext = (List<TblContaPagarReceber>) Utils.getFromContext("contasPag");
        if (acao.equalsIgnoreCase("remove")) {
            this._contasPagarContext.remove(contasPagRec);
        } else if (acao.equalsIgnoreCase("add")) {
            this._contasPagarContext.add(contasPagRec);
        } else if (acao.equalsIgnoreCase("update")) {
            for (int i = 0; i < this._contasPagarContext.size(); i++) {
                if (_contasPagarContext.get(i).equals(contasPagRec)) {
                    _contasPagarContext.remove(i);
                    _contasPagarContext.add(i, contasPagRec);
                    break;
                } else {
                    continue;
                }
            }
        }
        Utils.setFromContext("contasPag", _contasPagarContext);
        context.execute("carregaInformacoes()");
    }

    /**
     * Deleta a conta selecionada.
     *
     * @param contasPagRec
     */
    public void deleteFromTbl(TblContaPagarReceber contasPagRec) {

        FacesMessage msg = null;
        String erro;

        erro = new ControlContasPagar().deletaConta(contasPagRec);
        if (erro.toUpperCase().contains("SUCESSO")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
            this._newContasPagRec.remove(contasPagRec);
            this._contasPagar.remove(contasPagRec);

            this.clearFields(2);
            for (int i = 0; i < this._eventModel.getEvents().size(); i++) {
                ScheduleEvent event = this._eventModel.getEvents().get(i);
                if (contasPagRec.getDescricao().equals(event.getTitle())) {
                    this._eventModel.deleteEvent(event);
                }
            }

            this.updateContasNaoPagasContexto(contasPagRec, "remove");

        } else {
            erro = "Selecione uma linha para excluir.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Insere nova conta no banco de dados
     */
    public void insertNewContaPagRec() {

        FacesMessage msg;
        String erro;

        if (this._newContaPagRec.getDescricao().equals("")) {
            erro = "Descrição não pode estar em branco.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        } else if (this._newContaPagRec.getDescricao().length() > 99) {
            erro = "Descrição muito grande.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

        } else {
            this._newContaPagRec.setIdUsuario(this._usuario);
            this._newContaPagRec.setIdTipoMovimento(new TblTipoMovimento(1));
            this._newContaPagRec.setPago("N");
            this._newContaPagRec.setDataMovimento(this._selectedDate);

            if (this._newCategoria.getId() != null) {
                this._newContaPagRec.setIdCategoria(this._newCategoria);
            }

            erro = new ControlContasPagar().insereNovaConta(this._newContaPagRec);

            if (erro.toUpperCase().contains("SUCESSO")) {
                //this._contas.remove(this._selectedConta);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                this._contasPagar.add(0, _newContaPagRec);
                this._eventModel.addEvent(new DefaultScheduleEvent(_newContaPagRec.getDescricao(), _newContaPagRec.getDataMovimento().toDate(), _newContaPagRec.getDataMovimento().toDate()));
                this._newContasPagRec.add(_newContaPagRec);

                this.updateContasNaoPagasContexto(_newContaPagRec, "add");

                this.clearFields(2);

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void RecebContaPag() {

        FacesMessage msg;
        String erro;

        if (this._selectedContaPagRec.getPago().equals("N")) {
            if (this._selectedContaPagRec.getDescricao().equals("")) {
                erro = "Descrição não pode estar em branco.";
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            } else if (this._selectedContaPagRec.getDescricao().length() > 99) {
                erro = "Descrição muito grande.";
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

            } else if (this._selectedContaPagRec.getValor() == 0.0) {
                erro = "O valor não pode ser zero.";
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

            } else {

                this._selectedContaPagRec.setIdConta(this._newConta);
                this._selectedContaPagRec.setPago("S");

                erro = new ControlContasPagar().RecebeConta(this._selectedContaPagRec);

                if (erro.toUpperCase().contains("SUCESSO")) {
                    //this._contas.remove(this._selectedConta);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                    this._eventModel.updateEvent(new DefaultScheduleEvent(_selectedContaPagRec.getDescricao(),
                            _selectedContaPagRec.getDataMovimento().toDate(),
                            _selectedContaPagRec.getDataMovimento().toDate()));

                    this.updateContasNaoPagasContexto(_selectedContaPagRec, "remove");

                    this.clearFields(2);

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
                }
            }
        } else {
            erro = "Essa conta já foi paga.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void openDialogPagar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (this._selectedContaPagRec.getPago().equals("S")) {
            context.execute("dlg_lbErrorPag.show()");
        } else {
            context.execute("recebDialogPag.show()");
        }
    }

    /**
     *
     */
    public void openDialogEdit() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (this._selectedContaPagRec.getPago().equals("S")) {
            context.execute("dlg_lbErrorPag.show()");
        } else {
            context.execute("editDialogPag.show()");
        }
    }

    /**
     *
     */
    public void UpdateContaPagRec() {

        FacesMessage msg;
        String erro;

        if (this._selectedContaPagRec.getDescricao().equals("")) {
            erro = "Descrição não pode estar em branco.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        } else if (this._selectedContaPagRec.getDescricao().length() > 99) {
            erro = "Descrição muito grande.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

        } else {

            erro = new ControlContasPagar().insereNovaConta(this._selectedContaPagRec);

            if (erro.toUpperCase().contains("SUCESSO")) {
                //this._contas.remove(this._selectedConta);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);

                this._eventModel.updateEvent(new DefaultScheduleEvent(_selectedContaPagRec.getDescricao(),
                        _selectedContaPagRec.getDataMovimento().toDate(),
                        _selectedContaPagRec.getDataMovimento().toDate()));

                this.updateContasNaoPagasContexto(_selectedContaPagRec, "update");

                this.clearFields(2);

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void deleteContaPag() {
        FacesMessage msg = null;
        String erro;
        
        if (this._selectedContaPagRec.getId() != null) {
            erro = new ControlContasPagar().deletaConta(this._selectedContaPagRec);
            if (erro.toUpperCase().contains("SUCESSO")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                this._contasPagar.remove(this._selectedContaPagRec);

                this.updateContasNaoPagasContexto(_selectedContaPagRec, "remove");

                for (int i = 0; i < this._eventModel.getEvents().size(); i++) {
                    ScheduleEvent event = this._eventModel.getEvents().get(i);
                    if (_selectedContaPagRec.getDescricao().equals(event.getTitle())) {
                        this._eventModel.deleteEvent(event);
                    }
                }
            } else {
                erro = "Selecione uma linha para excluir.";
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
            this._selectedContaPagRec.setId(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param evt
     */
    public void onRowSelect(SelectEvent evt) {
        this._selectedContaPagRec = (TblContaPagarReceber) evt.getObject();
        this._selectedContaPagRec.setDataPagamento(new DateTime(new Date()));
    }

    /**
     *
     * @param evt
     */
    public void onRowSelectCateg(SelectEvent evt) {
        if (this.action.equals("edit")) {
            this._selectedContaPagRec.setIdCategoria((TblCategoria) evt.getObject());
        } else if (this.action.equals("new")) {
            this._newCategoria = (TblCategoria) evt.getObject();
        }
    }

    /**
     *
     */
    public void clearFields(Integer acao) {
        /*
         1 - Chamada da tela
         2 - Chamado do bean
         */
        if (acao == 1) {
            this._newContasPagRec.clear();
        }
        this._newCategoria = new TblCategoria();
        _newContaPagRec = null;
        this._newContaPagRec = new TblContaPagarReceber();
        this._newConta = new TblConta();
    }

    /**
     *
     * @param date
     * @return
     */
    public String dateFormat(DateTime date) {
        return Utils.dateTimeFormat(date);
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
     * @return
     */
    public List<TblContaPagarReceber> getContasPagar() {
        return _contasPagar;
    }

    /**
     *
     * @param _contasPagar
     */
    public void setContasPagar(List<TblContaPagarReceber> _contasPagar) {
        this._contasPagar = _contasPagar;
    }

    /**
     *
     * @param date
     * @return
     */
    public String returnDateMovimento(DateTime date) {
        return Utils.dateTimeFormat(date);
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
    public boolean isPago() {
        return _pago;
    }

    /**
     *
     * @param _Pagos
     */
    public void setPago(boolean _pago) {
        this._pago = _pago;
    }

    /**
     *
     * @return
     */
    public TblConta getContaFiltro() {
        return _contaFiltro;
    }

    /**
     *
     * @param _contaFiltro
     */
    public void setContaFiltro(TblConta _contaFiltro) {
        this._contaFiltro = _contaFiltro;
    }

    /**
     *
     * @return
     */
    public DateTime getDateIniFiltro() {
        return _dateIniFiltro;
    }

    /**
     *
     * @param _dateIniFiltro
     */
    public void setDateIniFiltro(Date _dateIniFiltro) {
        this._dateIniFiltro = new DateTime(_dateIniFiltro);
    }

    /**
     *
     * @return
     */
    public DateTime getDateEndFiltro() {
        return _dateEndFiltro;
    }

    /**
     *
     * @param _dateFimFiltro
     */
    public void setDateEndFiltro(Date _dateEndFiltro) {
        this._dateEndFiltro = new DateTime(_dateEndFiltro);
    }

    /**
     *
     * @return
     */
    public ScheduleEvent getEvent() {
        return _event;
    }

    /**
     *
     * @param _event
     */
    public void setEvent(ScheduleEvent _event) {
        this._event = _event;
    }

    /**
     *
     * @return
     */
    public TblConta getNewConta() {
        return _newConta;
    }

    /**
     *
     * @param _newConta
     */
    public void setNewConta(TblConta _newConta) {
        this._newConta = _newConta;
    }

    /**
     *
     * @return
     */
    public TblContaPagarReceber getNewContaPagRec() {
        return _newContaPagRec;
    }

    /**
     *
     * @param _newContaPagRec
     */
    public void setNewContaPagRec(TblContaPagarReceber _newContaPagRec) {
        this._newContaPagRec = _newContaPagRec;
    }

    /**
     *
     * @return
     */
    public TblCategoria getNewCategoria() {
        return _newCategoria;
    }

    /**
     *
     * @param _newCategoria
     */
    public void setNewCategoria(TblCategoria _newCategoria) {
        this._newCategoria = _newCategoria;
    }

    /**
     *
     * @return
     */
    public ScheduleModel getEventModel() {
        return _eventModel;
    }

    /**
     *
     * @param _eventModel
     */
    public void setEventModel(ScheduleModel _eventModel) {
        this._eventModel = _eventModel;
    }

    /**
     *
     * @return
     */
    public List<TblCategoria> getCategorias() {
        return _categorias;
    }

    /**
     *
     * @param _categorias
     */
    public void setCategorias(List<TblCategoria> _categorias) {
        this._categorias = _categorias;
    }

    /**
     *
     * @return
     */
    public List<TblContaPagarReceber> getNewContasPagRec() {
        return _newContasPagRec;
    }

    /**
     *
     * @param _newContasPagRec
     */
    public void setNewContasPagRec(List<TblContaPagarReceber> _newContasPagRec) {
        this._newContasPagRec = _newContasPagRec;
    }

    /**
     *
     * @return
     */
    public DateTime getSelectedDate() {
        return _selectedDate;
    }

    /**
     *
     * @param _selectedDate
     */
    public void setSelectedDate(DateTime _selectedDate) {
        this._selectedDate = _selectedDate;
    }

    /**
     *
     * @return
     */
    public TblContaPagarReceber getSelectedContaPagRec() {
        return _selectedContaPagRec;
    }

    /**
     *
     * @param _selectedContaPagRec
     */
    public void setSelectedContaPagRec(TblContaPagarReceber _selectedContaPagRec) {
        this._selectedContaPagRec = _selectedContaPagRec;
    }
}
