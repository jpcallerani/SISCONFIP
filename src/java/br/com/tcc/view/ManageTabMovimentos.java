package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlCategoria;
import br.com.tcc.controller.ControlConta;
import br.com.tcc.controller.ControlMovimento;
import br.com.tcc.modal.TblCategoria;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblMovimento;
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
@ManagedBean(name = "ManageTabMovimentos")
@ViewScoped
public class ManageTabMovimentos {

    private List<TblMovimento> _movimentos;
    private List<TblConta> _contas;
    private TblUsuario _usuario;
    private TblConta _conta;
    private String action;
    private Integer _tipoMovimento;
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
    private TblMovimento _newMovimento;
    private TblMovimento _selectedMovimento;
    private TblCategoria _newCategoria;
    private List<TblCategoria> _categorias;
    private List<TblMovimento> _newMovimentos;
    private DateTime _selectedDate;
    //

    public ManageTabMovimentos() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        this._usuario = (TblUsuario) session.getAttribute("usuario");
        this._movimentos = new ArrayList<TblMovimento>();
        this._contas = new ArrayList<TblConta>();
        this._auxAbrir = true;
        this._newConta = new TblConta();
        this._newMovimento = new TblMovimento();
        this._newCategoria = new TblCategoria();
        this._selectedMovimento = new TblMovimento();
        this._eventModel = new DefaultScheduleModel();
        this._categorias = new ArrayList<TblCategoria>();
        this._newMovimentos = new ArrayList<TblMovimento>();
        this._movimentos = new ControlMovimento().movimentosUsuario(this._usuario);
        this._tipoMovimento = 1;
    }

    @PostConstruct
    public void init() {
        this.returnEvents();
        this.findCategorias();
        this.findAllContas();
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return action;
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
        if (this._movimentos != null) {
            for (int i = 0; i < this._movimentos.size(); i++) {
                TblMovimento tblMovimento = this._movimentos.get(i);
                this._eventModel.addEvent(new DefaultScheduleEvent(tblMovimento.getDescricao(), tblMovimento.getDataMovimento().toDate(), tblMovimento.getDataMovimento().toDate()));
            }
        }
    }

    /**
     * Executa a consulta do filtro
     */
    public void findContasPagRecByFilter() {
        this._movimentos = new ControlMovimento().PesquisaMovimentos(this._contaFiltro, this._usuario, this._dateIniFiltro, this._dateEndFiltro);
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
     * Deleta a conta selecionada.
     *
     * @param contasPagRec
     */
    public void deleteFromTbl(TblMovimento movimento) {

        FacesMessage msg = null;
        String erro;
        RequestContext context = RequestContext.getCurrentInstance();
        erro = new ControlMovimento().deletaConta(movimento);
        if (erro.toUpperCase().contains("SUCESSO")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
            this._newMovimentos.remove(movimento);
            this._movimentos.remove(movimento);

            this.clearFields(2);
            for (int i = 0; i < this._eventModel.getEvents().size(); i++) {
                ScheduleEvent event = this._eventModel.getEvents().get(i);
                if (movimento.getDescricao().equals(event.getTitle())) {
                    this._eventModel.deleteEvent(event);
                }
            }

        } else {
            erro = "Selecione uma linha para excluir.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }
        context.execute("carregaInformacoes()");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Insere nova conta no banco de dados
     */
    public void insertNewMovimento() {

        FacesMessage msg;
        String erro;
        RequestContext context = RequestContext.getCurrentInstance();

        if (this._newMovimento.getDescricao().equals("")) {
            erro = "Descrição não pode estar em branco.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        } else if (this._newMovimento.getDescricao().length() > 99) {
            erro = "Descrição muito grande.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

        } else {
            this._newMovimento.setIdUsuario(this._usuario);
            this._newMovimento.setIdTipoMovimento(new TblTipoMovimento(this._tipoMovimento));
            this._newMovimento.setIdConta(this._newConta);
            this._newMovimento.setDataMovimento(this._selectedDate);

            if (this._newCategoria.getId() != null) {
                this._newMovimento.setCategoria(this._newCategoria);
            }

            erro = new ControlMovimento().insereNovoMovimento(this._newMovimento);

            if (erro.toUpperCase().contains("SUCESSO")) {
                //this._contas.remove(this._selectedConta);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                this._movimentos.add(0, this._newMovimento);
                this._eventModel.addEvent(new DefaultScheduleEvent(this._newMovimento.getDescricao(), this._newMovimento.getDataMovimento().toDate(), this._newMovimento.getDataMovimento().toDate()));
                this._newMovimentos.add(this._newMovimento);

                this.clearFields(2);

                context.execute("carregaInformacoes()");

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void UpdateMovimento() {

        FacesMessage msg;
        String erro;
        RequestContext context = RequestContext.getCurrentInstance();

        if (this._selectedMovimento.getDescricao().equals("")) {
            erro = "Descrição não pode estar em branco.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        } else if (this._selectedMovimento.getDescricao().length() > 99) {
            erro = "Descrição muito grande.";
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);

        } else {

            erro = new ControlMovimento().insereNovoMovimento(this._selectedMovimento);

            if (erro.toUpperCase().contains("SUCESSO")) {
                //this._contas.remove(this._selectedConta);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);

                this._eventModel.updateEvent(new DefaultScheduleEvent(this._selectedMovimento.getDescricao(),
                        this._selectedMovimento.getDataMovimento().toDate(),
                        this._selectedMovimento.getDataMovimento().toDate()));

                this.clearFields(2);

                context.execute("carregaInformacoes()");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void deleteMovimento() {
        FacesMessage msg = null;
        String erro;
        RequestContext context = RequestContext.getCurrentInstance();

        if (this._selectedMovimento.getId() != null) {
            erro = new ControlMovimento().deletaConta(this._selectedMovimento);
            if (erro.toUpperCase().contains("SUCESSO")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
                this._movimentos.remove(this._selectedMovimento);

                for (int i = 0; i < this._eventModel.getEvents().size(); i++) {
                    ScheduleEvent event = this._eventModel.getEvents().get(i);
                    if (this._selectedMovimento.getDescricao().equals(event.getTitle())) {
                        this._eventModel.deleteEvent(event);
                    }
                }
                context.execute("carregaInformacoes()");
            } else {
                erro = "Selecione uma linha para excluir.";
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
            }
            this._selectedMovimento.setId(null);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param evt
     */
    public void onRowSelect(SelectEvent evt) {
        this._selectedMovimento = (TblMovimento) evt.getObject();
    }

    /**
     *
     * @param evt
     */
    public void onRowSelectCateg(SelectEvent evt) {
        this._newCategoria = (TblCategoria) evt.getObject();
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
            this._newMovimentos.clear();
        }
        this._newCategoria = new TblCategoria();
        this._newMovimento = null;
        this._newMovimento = new TblMovimento();
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
    public List<TblMovimento> getMovimentos() {
        return _movimentos;
    }

    /**
     *
     * @param _movimentos
     */
    public void setMovimentos(List<TblMovimento> _movimentos) {
        this._movimentos = _movimentos;
    }

    /**
     *
     * @return
     */
    public TblMovimento getNewMovimento() {
        return _newMovimento;
    }

    /**
     *
     * @param _newMovimento
     */
    public void setNewMovimento(TblMovimento _newMovimento) {
        this._newMovimento = _newMovimento;
    }

    /**
     *
     * @return
     */
    public TblMovimento getSelectedMovimento() {
        return _selectedMovimento;
    }

    /**
     *
     * @param _selectedMovimento
     */
    public void setSelectedMovimento(TblMovimento _selectedMovimento) {
        this._selectedMovimento = _selectedMovimento;
    }

    /**
     *
     * @return
     */
    public List<TblMovimento> getNewMovimentos() {
        return _newMovimentos;
    }

    /**
     *
     * @param _newMovimentos
     */
    public void setNewMovimentos(List<TblMovimento> _newMovimentos) {
        this._newMovimentos = _newMovimentos;
    }

    /**
     *
     * @return
     */
    public Integer getTipoMovimento() {
        return _tipoMovimento;
    }

    /**
     *
     * @param _tipoMovimento
     */
    public void setTipoMovimento(Integer _tipoMovimento) {
        this._tipoMovimento = _tipoMovimento;
    }
}
