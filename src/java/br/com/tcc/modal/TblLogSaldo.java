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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_LOG_SALDO")
@XmlRootElement
@SequenceGenerator(name = "SEQUENCE_LOG_SALDO", sequenceName = "SEQ_ID_LOG_SALDO", allocationSize = 1)
public class TblLogSaldo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_LOG_SALDO")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SALDO_INICIAL")
    private Double saldoInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_ATUALIZACAO")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime dataAtualizacao;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuario idUsuario;

    public TblLogSaldo() {
    }

    public TblLogSaldo(Integer id) {
        this.id = id;
    }

    public TblLogSaldo(Integer id, Double saldoInicial, DateTime dataAtualizacao) {
        this.id = id;
        this.saldoInicial = saldoInicial;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public DateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(DateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public TblUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TblUsuario idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof TblLogSaldo)) {
            return false;
        }
        TblLogSaldo other = (TblLogSaldo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblLogSaldo[ id=" + id + " ]";
    }
    
}
