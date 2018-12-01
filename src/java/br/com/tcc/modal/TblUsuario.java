/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_USUARIO")
@SequenceGenerator(name = "SEQUENCE_USUARIO", sequenceName = "SEQ_ID_USUARIO", allocationSize = 1)
public class TblUsuario implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<TblLogSaldo> tblLogSaldoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<TblContaPagarReceber> tblContaPagarReceberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<TblConta> tblContaCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_USUARIO")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USERNAME")
    private String username;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 1)
    @Column(name = "PRIMEIRA_VEZ")
    private String primeiraVez;
    @Column(name = "DATA_CRIACAO")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;

    public TblUsuario() {
    }

    public TblUsuario(Integer id) {
        this.id = id;
    }

    public TblUsuario(Integer id, String nome, String password, String username, String email) {
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.username = username;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimeiraVez() {
        return primeiraVez;
    }

    public void setPrimeiraVez(String primeiraVez) {
        this.primeiraVez = primeiraVez;
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
        if (!(object instanceof TblUsuario)) {
            return false;
        }
        TblUsuario other = (TblUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblUsuario[ id=" + id + " ]";
    }

    public Collection<TblConta> getTblContaCollection() {
        return tblContaCollection;
    }

    public void setTblContaCollection(Collection<TblConta> tblContaCollection) {
        this.tblContaCollection = tblContaCollection;
    }

    @XmlTransient
    public Collection<TblContaPagarReceber> getTblContaPagarReceberCollection() {
        return tblContaPagarReceberCollection;
    }

    public void setTblContaPagarReceberCollection(Collection<TblContaPagarReceber> tblContaPagarReceberCollection) {
        this.tblContaPagarReceberCollection = tblContaPagarReceberCollection;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @XmlTransient
    public Collection<TblLogSaldo> getTblLogSaldoCollection() {
        return tblLogSaldoCollection;
    }

    public void setTblLogSaldoCollection(Collection<TblLogSaldo> tblLogSaldoCollection) {
        this.tblLogSaldoCollection = tblLogSaldoCollection;
    }
}
