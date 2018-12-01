/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.view;

import br.com.tcc.Utils.Utils;
import br.com.tcc.controller.ControlConta;
import br.com.tcc.controller.ControlContasPagar;
import br.com.tcc.controller.ControlContasReceber;
import br.com.tcc.controller.ControlLogSaldo;
import br.com.tcc.modal.TblConta;
import br.com.tcc.modal.TblContaPagarReceber;
import br.com.tcc.modal.TblLogSaldo;
import br.com.tcc.modal.TblUsuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.joda.time.DateTime;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Jp
 */
@ManagedBean(name = "ManageTabHome")
@ViewScoped
public class ManageTabHome {

    @ManagedProperty(name = "usuario", value = "#{ManagePrincipal.usuario}")
    private TblUsuario usuario;
    private Double _saldo, _saldoContasPagar, _saldoContasReceber, _saldoConsolidado;
    private List<TblConta> _contas;
    private TblConta _contaSelecionada;
    private CartesianChartModel linearModel;
    private List<TblContaPagarReceber> _contasPag;
    private List<TblContaPagarReceber> _contasRec;
    private List<TblLogSaldo> _logSaldo;

    public ManageTabHome() {
        this._logSaldo = new ArrayList<TblLogSaldo>();
        this._saldoConsolidado = 0.0;
    }

    @PostConstruct
    public void init() {
        this.carregaContas();
        if (this._contas != null) {
            this.carregaSaldo();
        } else {
            this._saldo = 0.0;
        }
        this.carregaContasPagRec();
        if (this._contasPag != null) {
            this.somaContasPagar();
        }
        if (this._contasRec != null) {
            this.somaContasRec();
        }
        this.somaSaldoConsolidado();
        createLinearModel();
    }

    /**
     *
     */
    public void carregaInformacoes() {
        this.carregaContas();
        if (this._contas != null) {
            this.carregaSaldo();
        } else {
            this._saldo = 0.0;
        }
        this.carregaContasPagRec();
        if (this._contasPag != null) {
            this.somaContasPagar();
        }
        if (this._contasRec != null) {
            this.somaContasRec();
        }
        this.somaSaldoConsolidado();
        createLinearModel();
    }

    /**
     *
     */
    private void createLinearModel() {
        linearModel = new CartesianChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Log Total do Saldo");

        this._logSaldo = new ControlLogSaldo().carregaLogSaldo(this.usuario);

        if (this._logSaldo.isEmpty()) {
            series1.set(Utils.dateTimeFormat(new DateTime()), 0.0);
            
        } else {
            for (int i = 0; i < this._logSaldo.size(); i++) {
                TblLogSaldo logSaldo = this._logSaldo.get(i);
                series1.set(Utils.dateTimeFormat(logSaldo.getDataAtualizacao()), logSaldo.getSaldoInicial());
            }
        }

        linearModel.addSeries(series1);
    }

    /**
     *
     */
    private void carregaContasPagRec() {
        this._contasPag = (List<TblContaPagarReceber>) Utils.getFromContext("contasPag");
        this._contasRec = (List<TblContaPagarReceber>) Utils.getFromContext("contasRec");

    }

    /**
     *
     */
    public String somaContasPagar() {
        this._saldoContasPagar = new ControlContasPagar().retornaSaldo(this._contasPag);
        return Utils.moneyFormat(this._saldoContasPagar);
    }

    /**
     *
     */
    public String somaContasRec() {
        this._saldoContasReceber = new ControlContasReceber().retornaSaldo(this._contasRec);
        return Utils.moneyFormat(this._saldoContasReceber);
    }

    /**
     *
     * @return
     */
    public String somaSaldoConsolidado() {
        this._saldoConsolidado = this._saldo;
        this._saldoConsolidado = this._saldoConsolidado - this._saldoContasPagar;
        this._saldoConsolidado = this._saldoConsolidado + this._saldoContasReceber;
        return Utils.moneyFormat(this._saldoConsolidado);
    }

    /**
     * Retorna o CSS de acordo com o saldo
     *
     * @return
     */
    public String retornaPosNeg(Double saldo) {
        String valor;
        if (saldo >= 0) {
            valor = "positivo";
        } else {
            valor = "negativo";
        }
        return valor;
    }

        /**
     * 
     * @return 
     */
    public String retornaUrlFeed() {
        File termos;
        String url="";
        try {
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = context.getRealPath("");
            termos = new File(path, "url_feed.txt");
            FileReader fileReader = new FileReader(termos);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                url = linha + "\r\n";
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    
    /**
     *
     * @param valor
     * @return
     */
    public String retornaSaldoDinheiro() {
        return Utils.moneyFormat(this._saldo);
    }

    /**
     *
     * @param valor
     * @return
     */
    public String retornaSaldoDinheiro(Double valor) {
        return Utils.moneyFormat(valor);
    }

    /**
     * Retorna o Saldo em questão
     *
     * @return
     */
    public void carregaSaldo() {
        this._saldo = new ControlConta().retornaSaldo(this._contas);
    }

    /**
     * Retorna o Saldo em questão
     *
     * @return
     */
    public String retornaSaldoConta(Double valor) {
        return Utils.moneyFormat(valor);
    }

    /**
     * Retorna Data Formatada
     *
     * @return
     */
    public String retornaData(DateTime data) {
        return Utils.dateTimeFormat(data);
    }

    /**
     *
     * @return
     */
    public CartesianChartModel getLinearModel() {
        return linearModel;
    }

    /**
     *
     * @param linearModel
     */
    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
    }

    /**
     *
     */
    private void carregaContas() {
        this._contas = new ControlConta().contasFilter(new TblConta(), this.usuario);
    }

    /**
     *
     * @return
     */
    public TblConta getContaSelecionada() {
        return _contaSelecionada;
    }

    /**
     *
     * @param _contaSelecionada
     */
    public void setContaSelecionada(TblConta _contaSelecionada) {
        if (_contaSelecionada != null) {
            this._contaSelecionada = _contaSelecionada;
        }
    }

    /**
     *
     * @return
     */
    public TblUsuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @param _usuario
     */
    public void setUsuario(TblUsuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public Double getSaldo() {
        return _saldo;
    }

    /**
     *
     * @param _saldo
     */
    public void setSaldo(Double _saldo) {
        this._saldo = _saldo;
    }

    /**
     *
     * @return
     */
    public List<TblConta> getConta() {
        return _contas;
    }

    /**
     *
     * @param _conta
     */
    public void setConta(List<TblConta> _contas) {
        this._contas = _contas;
    }

    /**
     *
     * @return
     */
    public List<TblContaPagarReceber> getContasPag() {
        return _contasPag;
    }

    /**
     *
     * @param _contasPag
     */
    public void setContasPag(List<TblContaPagarReceber> _contasPag) {
        this._contasPag = _contasPag;
    }

    /**
     *
     * @return
     */
    public List<TblContaPagarReceber> getContasRec() {
        return _contasRec;
    }

    /**
     *
     * @param _contasRec
     */
    public void setContasRec(List<TblContaPagarReceber> _contasRec) {
        this._contasRec = _contasRec;
    }

    /**
     *
     * @return
     */
    public Double getSaldoContasPagar() {
        return _saldoContasPagar;
    }

    /**
     *
     * @param _saldoContasPagar
     */
    public void setSaldoContasPagar(Double _saldoContasPagar) {
        this._saldoContasPagar = _saldoContasPagar;
    }

    /**
     *
     * @return
     */
    public Double getSaldoContasReceber() {
        return _saldoContasReceber;
    }

    /**
     *
     * @param _saldoContasReceber
     */
    public void setSaldoContasReceber(Double _saldoContasReceber) {
        this._saldoContasReceber = _saldoContasReceber;
    }

    /**
     *
     * @return
     */
    public Double getSaldoConsolidado() {
        return _saldoConsolidado;
    }

    /**
     *
     * @param _saldoConsolidado
     */
    public void setSaldoConsolidado(Double _saldoConsolidado) {
        this._saldoConsolidado = _saldoConsolidado;
    }
}