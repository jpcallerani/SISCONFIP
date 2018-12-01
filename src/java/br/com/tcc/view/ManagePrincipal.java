/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.TabItem;
import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlConta;
import br.com.tcc.controller.ControlContasPagar;
import br.com.tcc.controller.ControlContasReceber;
import br.com.tcc.controller.ControlUsuario;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManagePrincipal")
@SessionScoped
public class ManagePrincipal {

    private TblUsuario _usuario;
    private List<TabItem> tabList;
    private int activeIndex = 0;

    /**
     * Creates a new instance of ManagePrincipal
     */
    public ManagePrincipal() {
        this._usuario = (TblUsuario) Utils.getFromContext("usuario");
        this.carregaContas();
        tabList = new ArrayList<TabItem>();
        this.carregaContasPagRec();
    }

    @PostConstruct
    public void init() {
        tabList = new ArrayList<TabItem>();
        tabList.add(new TabItem("Home", "/resources/views/tabHome.xhtml", 0, "/resources/img/tab_home.png", false));
    }

    /**
     *
     */
    private void carregaContasPagRec() {
        Utils.setFromContext("contasPag", new ControlContasPagar().procuraContasPorFiltro(null, this._usuario, null, null, false));
        Utils.setFromContext("contasRec", new ControlContasReceber().procuraContasPorFiltro(null, this._usuario, null, null, false));
    }

    public boolean retornaContasPagasVencidas() {
        return new ControlContasPagar().retornaContasVencidas((List<TblContaPagarReceber>) Utils.getFromContext("contasPag"));
    }

    public boolean retornaContasRecVencidas() {
        return new ControlContasReceber().retornaContasVencidas((List<TblContaPagarReceber>) Utils.getFromContext("contasRec"));
    }

    /**
     *
     */
    private void carregaContas() {
        Utils.setFromContext("contas", new ControlConta().contasFilter(new TblConta(), this._usuario));
    }

    /**
     *
     */
    public void deleteContaUsuario() {
        FacesMessage msg = null;

        String erro = new ControlUsuario().deletaUsuario(this._usuario);

        if (erro.toUpperCase().contains("SUCESSO")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.logoff("index");
    }

    /**
     *
     */
    public void salvaUsuario() {
        FacesMessage msg = null;

        String erro = new ControlUsuario().alteraUsuario(this._usuario);

        if (erro.toUpperCase().contains("SUCESSO")) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, erro, erro);
            Utils.setFromContext("usuario", this._usuario);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, erro);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param nome
     */
    public void addTab(String nome) {
        int newActiveIndex = tabList.size();
        boolean encontrou = false;
        for (int i = 0; i < tabList.size(); i++) {
            TabItem tabItem = tabList.get(i);
            if (tabItem.getName().equalsIgnoreCase(nome)) {
                encontrou = true;
                this.setActiveIndex(i);
                break;
            }
        }
        if (!encontrou) {
            this.tabList.add(new TabItem(nome, "/resources/views/tab" + nome + ".xhtml", newActiveIndex, nome, true));
            this.setActiveIndex(newActiveIndex);
        }
    }

    /**
     *
     * @param event
     */
    public void onTabClose(TabCloseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        TabView tabView = (TabView) event.getComponent();
        String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");
        int index = Integer.parseInt(activeIndexValue);
        tabList.remove(index);
        if (tabList.size() == 1) {
            this.setActiveIndex(0);
        }
    }

    /**
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        /*FacesContext context = FacesContext.getCurrentInstance();
         Map<String, String> params = context.getExternalContext().getRequestParameterMap();
         TabView tabView = (TabView) event.getComponent();
         String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");

         this.activeIndex = Integer.parseInt(activeIndexValue);
         TabItem tab = tabList.get(activeIndex);*/

        /*RequestContext Rcontext = RequestContext.getCurrentInstance();

         if (Rcontext != null && this.activeIndex == 0) {
         Rcontext.update("tabView:financeiro");
         }*/
        /*System.out.println(tab.getName());
         this.setActiveIndex(activeIndex);*/
        TabView tabView = (TabView) event.getComponent();

        //this.activeIndex = Integer.parseInt();
        TabItem tab = tabList.get(tabView.getActiveIndex());

        System.out.println(tab.getName());
        this.setActiveIndex(activeIndex);
    }

    /**
     *
     * @param data
     * @return
     */
    public String DataFormatada(Date data) {
        return Utils.dateFormat(data);
    }

    /**
     *
     * @return
     */
    public List<TabItem> getTabList() {
        return tabList;
    }

    /**
     *
     * @param tabList
     */
    public void setTabList(List<TabItem> tabList) {
        this.tabList = tabList;
    }

    /**
     *
     * @return
     */
    public int getActiveIndex() {
        return activeIndex;
    }

    /**
     *
     * @param activeIndex
     */
    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
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
    public String logoff(String pagina) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return pagina + "?faces-redirect=true";
    }
    }
