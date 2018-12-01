/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_CONTA")
@SequenceGenerator(name = "SEQUENCE_CONTA", sequenceName = "SEQ_ID_CONTA", allocationSize = 1)
public class TblConta implements Serializable {

    @Basic(optional = false)
    @Column(name = "SALDO_INICIAL")
    private Double saldoInicial = 0.0;
    @Size(max = 20)
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CONTA")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_VALIDADE")
    @Temporal(TemporalType.DATE)
    private Date dataValidade;
    @Column(name = "LIMITE_CARTAO")
    private Double limiteCartao = 0.0;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuario idUsuario;
    @JoinColumn(name = "ID_TIPO_CONTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblTipoConta idTipoConta;
    @JoinColumn(name = "ID_BANCO", referencedColumnName = "ID")
    @ManyToOne
    private TblBanco idBanco;

    public TblConta() {
    }

    public TblConta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Double getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(Double limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public TblUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TblUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TblTipoConta getIdTipoConta() {
        return idTipoConta;
    }

    public void setIdTipoConta(TblTipoConta idTipoConta) {
        this.idTipoConta = idTipoConta;
    }

    public TblBanco getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(TblBanco idBanco) {
        this.idBanco = idBanco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblConta)) {
            return false;
        }
        TblConta other = (TblConta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblConta[ id=" + id + " ]";
    }
}
