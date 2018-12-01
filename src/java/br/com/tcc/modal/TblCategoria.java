/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_CATEGORIA")
public class TblCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOME")
    private String nome;
    @JoinColumn(name = "ID_GRUPO_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblGrupoCategoria idGrupoCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoria")
    private Collection<TblClassificaAuto> tblClassificaAutoCollection;
    @OneToMany(mappedBy = "idCategoria")
    private Collection<TblContaPagarReceber> tblContaPagarReceberCollection;

    public TblCategoria() {
    }

    public TblCategoria(BigDecimal id) {
        this.id = id;
    }

    public TblCategoria(BigDecimal id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TblGrupoCategoria getIdGrupoCategoria() {
        return idGrupoCategoria;
    }

    public void setIdGrupoCategoria(TblGrupoCategoria idGrupoCategoria) {
        this.idGrupoCategoria = idGrupoCategoria;
    }

    public Collection<TblClassificaAuto> getTblClassificaAutoCollection() {
        return tblClassificaAutoCollection;
    }

    public void setTblClassificaAutoCollection(Collection<TblClassificaAuto> tblClassificaAutoCollection) {
        this.tblClassificaAutoCollection = tblClassificaAutoCollection;
    }

    public Collection<TblContaPagarReceber> getTblContaPagarReceberCollection() {
        return tblContaPagarReceberCollection;
    }

    public void setTblContaPagarReceberCollection(Collection<TblContaPagarReceber> tblContaPagarReceberCollection) {
        this.tblContaPagarReceberCollection = tblContaPagarReceberCollection;
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
        if (!(object instanceof TblCategoria)) {
            return false;
        }
        TblCategoria other = (TblCategoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblCategoria[ id=" + id + " ]";
    }
    
}
