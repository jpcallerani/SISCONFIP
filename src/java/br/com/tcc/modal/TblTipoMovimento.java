/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_TIPO_MOVIMENTO")
public class TblTipoMovimento implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOME")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoMovimento")
    private Collection<TblMovimento> tblMovimentoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoMovimento")
    private Collection<TblContaPagarReceber> tblContaPagarReceberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoMovimento")
    private Collection<TblGrupoCategoria> tblGrupoCategoriaCollection;

    public TblTipoMovimento() {
    }

    public TblTipoMovimento(Integer id) {
        this.id = id;
    }

    public TblTipoMovimento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public Collection<TblMovimento> getTblMovimentoCollection() {
        return tblMovimentoCollection;
    }

    public void setTblMovimentoCollection(Collection<TblMovimento> tblMovimentoCollection) {
        this.tblMovimentoCollection = tblMovimentoCollection;
    }

    public Collection<TblContaPagarReceber> getTblContaPagarReceberCollection() {
        return tblContaPagarReceberCollection;
    }

    public void setTblContaPagarReceberCollection(Collection<TblContaPagarReceber> tblContaPagarReceberCollection) {
        this.tblContaPagarReceberCollection = tblContaPagarReceberCollection;
    }

    public Collection<TblGrupoCategoria> getTblGrupoCategoriaCollection() {
        return tblGrupoCategoriaCollection;
    }

    public void setTblGrupoCategoriaCollection(Collection<TblGrupoCategoria> tblGrupoCategoriaCollection) {
        this.tblGrupoCategoriaCollection = tblGrupoCategoriaCollection;
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
        if (!(object instanceof TblTipoMovimento)) {
            return false;
        }
        TblTipoMovimento other = (TblTipoMovimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblTipoMovimento[ id=" + id + " ]";
    }
    
}
