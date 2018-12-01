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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "TBL_CONTA_PAGAR_RECEBER")
@SequenceGenerator(name = "SEQUENCE_CONTA_PAG_REC", sequenceName = "SEQ_ID_CONTA_PAGAR_REC", allocationSize = 1)
@XmlRootElement
public class TblContaPagarReceber implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CONTA_PAG_REC")
    private Integer id;
    @Column(name = "NUMERO_DOCUMENTO")
    private Integer numeroDocumento;
    @NotNull
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "VALOR")
    private Double valor = 0.0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA_MOVIMENTO")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime dataMovimento;
    @Basic
    @NotNull
    @Column(name = "DATA_PAGAMENTO")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime dataPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PAGO")
    private String pago;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuario idUsuario;
    @JoinColumn(name = "ID_TIPO_MOVIMENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblTipoMovimento idTipoMovimento;
    @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    @ManyToOne
    private TblConta idConta;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
    @ManyToOne
    private TblCategoria idCategoria;

    public TblContaPagarReceber() {
    }

    public TblContaPagarReceber(Integer id) {
        this.id = id;
    }

    public TblContaPagarReceber(Integer id, String descricao, DateTime dataMovimento, String pago) {
        this.id = id;
        this.descricao = descricao;
        this.dataMovimento = dataMovimento;
        this.pago = pago;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public DateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(DateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public DateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(DateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public TblUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TblUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TblTipoMovimento getIdTipoMovimento() {
        return idTipoMovimento;
    }

    public void setIdTipoMovimento(TblTipoMovimento idTipoMovimento) {
        this.idTipoMovimento = idTipoMovimento;
    }

    public TblConta getIdConta() {
        return idConta;
    }

    public void setIdConta(TblConta idConta) {
        this.idConta = idConta;
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
        if (!(object instanceof TblContaPagarReceber)) {
            return false;
        }
        TblContaPagarReceber other = (TblContaPagarReceber) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.tcc.modal.TblContaPagarReceber[ id=" + id + " ]";
    }
}
