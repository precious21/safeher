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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * TaxChargeTypeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="tax_charge_type"
    ,schema="dbo"
    
)

public class TaxChargeTypeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer chargeTypeId;
     private String name;
     private Set<TaxChargeEntity> taxChargeEntities = new HashSet<TaxChargeEntity>(0);


    // Constructors

    /** default constructor */
    public TaxChargeTypeEntity() {
    }

    
    /** full constructor */
    public TaxChargeTypeEntity(String name, Set<TaxChargeEntity> taxChargeEntities) {
        this.name = name;
        this.taxChargeEntities = taxChargeEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="charge_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getChargeTypeId() {
        return this.chargeTypeId;
    }
    
    public void setChargeTypeId(Integer chargeTypeId) {
        this.chargeTypeId = chargeTypeId;
    }
    
    @Column(name="name", length=18)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="taxChargeTypeEntity")

    public Set<TaxChargeEntity> getTaxChargeEntities() {
        return this.taxChargeEntities;
    }
    
    public void setTaxChargeEntities(Set<TaxChargeEntity> taxChargeEntities) {
        this.taxChargeEntities = taxChargeEntities;
    }
   








}