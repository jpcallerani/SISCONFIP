/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_MOVIMENTO")
@SequenceGenerator(name = "SEQUENCE_MOVIMENTO", sequenceName = "SEQ_ID_MOVIMENTO", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "TblMovimento.findAll", query = "SELECT t FROM TblMovimento t"),
    @NamedQuery(name = "TblMovimento.findById", query = "SELECT t FROM TblMovimento t WHERE t.id = :id"),
    @NamedQuery(name = "TblMovimento.findByNumeroDocumento", query = "SELECT t FROM TblMovimento t WHERE t.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "TblMovimento.findByDescricao", query = "SELECT t FROM TblMovimento t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TblMovimento.findByValor", query = "SELECT t FROM TblMovimento t WHERE t.valor = :valor"),
    @NamedQuery(name = "TblMovimento.findByCategoria", query = "SELECT t FROM TblMovimento t WHERE t.categoria = :categoria"),
    @NamedQuery(name = "TblMovimento.Pesquisa", query = "SELECT t FROM TblMovimento t WHERE t.dataMovimento between :dataMovimento AND :dataMovimento2"),
    @NamedQuery(name = "TblMovimento.findByDataMovimento", query = "SELECT t FROM TblMovimento t WHERE t.dataMovimento = :dataMovimento")})
public class TblMovimento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_MOVIMENTO")
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NUMERO_DOCUMENTO")
    private Integer numeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private Double valor = 0.0;
    @Size(max = 100)
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne
    private TblCategoria categoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_MOVIMENTO")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime dataMovimento;
    @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblConta idConta;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuario idUsuario;
    @JoinColumn(name = "ID_TIPO_MOVIMENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblTipoMovimento idTipoMovimento;

    public TblMovimento() {
    }

    public TblMovimento(Integer id) {
        this.id = id;
    }

    public TblMovimento(Integer id, String descricao, Double valor, DateTime dataMovimento) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataMovimento = dataMovimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TblCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(TblCategoria categoria) {
        this.categoria = categoria;
    }

    public DateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(DateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public TblUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TblUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public TblConta getIdConta() {
        return idConta;
    }

    public void setIdConta(TblConta idConta) {
        this.idConta = idConta;
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
        if (!(object instanceof TblMovimento)) {
            return false;
        }
        TblMovimento other = (TblMovimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblMovimento[ id=" + id + " ]";
    }
}
