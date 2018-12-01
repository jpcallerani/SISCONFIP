/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_CLASSIFICA_AUTO")
public class TblClassificaAuto implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EXPRESSAO")
    private String expressao;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblCategoria idCategoria;

    public TblClassificaAuto() {
    }

    public TblClassificaAuto(BigDecimal id) {
        this.id = id;
    }

    public TblClassificaAuto(BigDecimal id, String expressao) {
        this.id = id;
        this.expressao = expressao;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public TblCategoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(TblCategoria idCategoria) {
        this.idCategoria = idCategoria;
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
        if (!(object instanceof TblClassificaAuto)) {
            return false;
        }
        TblClassificaAuto other = (TblClassificaAuto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblClassificaAuto[ id=" + id + " ]";
    }
    
}
