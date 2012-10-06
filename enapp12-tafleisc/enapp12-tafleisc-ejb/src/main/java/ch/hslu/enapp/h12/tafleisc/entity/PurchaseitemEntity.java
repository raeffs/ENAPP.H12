package ch.hslu.enapp.h12.tafleisc.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
@Entity
@Table(name = "purchaseitem")
@NamedQueries({
    @NamedQuery(name = "PurchaseitemEntity.findAll", query = "SELECT p FROM PurchaseitemEntity p")})
public class PurchaseitemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "purchaseid")
    private int purchaseid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "productid")
    private int productid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private long quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unitprice")
    private long unitprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lineamount")
    private long lineamount;
    @Size(max = 90)
    @Column(name = "description")
    private String description;

    public PurchaseitemEntity() {
    }

    public PurchaseitemEntity(Integer id) {
        this.id = id;
    }

    public PurchaseitemEntity(Integer id, int purchaseid, int productid, long quantity, long unitprice, long lineamount) {
        this.id = id;
        this.purchaseid = purchaseid;
        this.productid = productid;
        this.quantity = quantity;
        this.unitprice = unitprice;
        this.lineamount = lineamount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(int purchaseid) {
        this.purchaseid = purchaseid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(long unitprice) {
        this.unitprice = unitprice;
    }

    public long getLineamount() {
        return lineamount;
    }

    public void setLineamount(long lineamount) {
        this.lineamount = lineamount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof PurchaseitemEntity)) {
            return false;
        }
        PurchaseitemEntity other = (PurchaseitemEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.enapp.h12.tafleisc.entity.PurchaseitemEntity[ id=" + id + " ]";
    }
}
