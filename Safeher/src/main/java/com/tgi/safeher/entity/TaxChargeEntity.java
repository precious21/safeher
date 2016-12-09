package com.tgi.safeher.entity;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * TaxChargeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="tax_charge"
    ,schema="dbo"
    
)

public class TaxChargeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer chargeId;
     private TaxChargeTypeEntity taxChargeTypeEntity;
     private String isFix;
     private Double amountPct;
     private String name;
     private Set<FineChargeEntity> fineChargeEntities = new HashSet<FineChargeEntity>(0);


    // Constructors

    /** default constructor */
    public TaxChargeEntity() {
    }

    
    /** full constructor */
    public TaxChargeEntity(TaxChargeTypeEntity taxChargeTypeEntity, String isFix, Double amountPct, String name, Set<FineChargeEntity> fineChargeEntities) {
        this.taxChargeTypeEntity = taxChargeTypeEntity;
        this.isFix = isFix;
        this.amountPct = amountPct;
        this.name = name;
        this.fineChargeEntities = fineChargeEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="charge_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getChargeId() {
        return this.chargeId;
    }
    
    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="charge_type_id")

    public TaxChargeTypeEntity getTaxChargeTypeEntity() {
        return this.taxChargeTypeEntity;
    }
    
    public void setTaxChargeTypeEntity(TaxChargeTypeEntity taxChargeTypeEntity) {
        this.taxChargeTypeEntity = taxChargeTypeEntity;
    }
    
    @Column(name="is_fix", length=1)

    public String getIsFix() {
        return this.isFix;
    }
    
    public void setIsFix(String isFix) {
        this.isFix = isFix;
    }
    
    @Column(name="amount_pct", precision=9, scale=3)

    public Double getAmountPct() {
        return this.amountPct;
    }
    
    public void setAmountPct(Double amountPct) {
        this.amountPct = amountPct;
    }
    
    @Column(name="name", length=18)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="taxChargeEntity")

    public Set<FineChargeEntity> getFineChargeEntities() {
        return this.fineChargeEntities;
    }
    
    public void setFineChargeEntities(Set<FineChargeEntity> fineChargeEntities) {
        this.fineChargeEntities = fineChargeEntities;
    }
   








}