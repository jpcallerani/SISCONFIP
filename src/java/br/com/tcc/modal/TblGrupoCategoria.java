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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_GRUPO_CATEGORIA")
public class TblGrupoCategoria implements Serializable {
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
    @Column(name = "GRUPO")
    private String grupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoCategoria")
    private Collection<TblCategoria> tblCategoriaCollection;
    @JoinColumn(name = "ID_TIPO_MOVIMENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblTipoMovimento idTipoMovimento;

    public TblGrupoCategoria() {
    }

    public TblGrupoCategoria(BigDecimal id) {
        this.id = id;
    }

    public TblGrupoCategoria(BigDecimal id, String grupo) {
        this.id = id;
        this.grupo = grupo;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Collection<TblCategoria> getTblCategoriaCollection() {
        return tblCategoriaCollection;
    }

    public void setTblCategoriaCollection(Collection<TblCategoria> tblCategoriaCollection) {
        this.tblCategoriaCollection = tblCategoriaCollection;
    }

    public TblTipoMovimento getIdTipoMovimento() {
        return idTipoMovimento;
    }

    public void setIdTipoMovimento(TblTipoMovimento idTipoMovimento) {
        this.idTipoMovimento = idTipoMovimento;
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
        if (!(object instanceof TblGrupoCategoria)) {
            return false;
        }
        TblGrupoCategoria other = (TblGrupoCategoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblGrupoCategoria[ id=" + id + " ]";
    }
    
}
