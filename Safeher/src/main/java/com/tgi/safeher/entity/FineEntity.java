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
 * FineEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="fine"
    ,schema="dbo"
    
)

public class FineEntity  extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer fineId;
     private FineTypeEntity fineTypeEntity;
     private String isFix;
     private Double amountPct;
     private String name;
     private Set<FineChargeEntity> fineChargeEntities = new HashSet<FineChargeEntity>(0);


    // Constructors

    /** default constructor */
    public FineEntity() {
    }

    
    /** full constructor */
    public FineEntity(FineTypeEntity fineTypeEntity, String isFix, Double amountPct, String name, Set<FineChargeEntity> fineChargeEntities) {
        this.fineTypeEntity = fineTypeEntity;
        this.isFix = isFix;
        this.amountPct = amountPct;
        this.name = name;
        this.fineChargeEntities = fineChargeEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="fine_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getFineId() {
        return this.fineId;
    }
    
    public void setFineId(Integer fineId) {
        this.fineId = fineId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="fine_type_id")

    public FineTypeEntity getFineTypeEntity() {
        return this.fineTypeEntity;
    }
    
    public void setFineTypeEntity(FineTypeEntity fineTypeEntity) {
        this.fineTypeEntity = fineTypeEntity;
    }
    
    @Column(name="is_fix", length=20)

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
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="fineEntity")

    public Set<FineChargeEntity> getFineChargeEntities() {
        return this.fineChargeEntities;
    }
    
    public void setFineChargeEntities(Set<FineChargeEntity> fineChargeEntities) {
        this.fineChargeEntities = fineChargeEntities;
    }
   








}