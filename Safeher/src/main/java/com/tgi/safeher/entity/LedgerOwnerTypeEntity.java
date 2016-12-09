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
 * LedgerOwnerTypeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ledger_owner_type"
    ,schema="dbo"
    
)

public class LedgerOwnerTypeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer ledgerOwnerTypeId;
     private String name;
     private Set<LedgerSummaryEntity> ledgerSummaryEntities = new HashSet<LedgerSummaryEntity>(0);


    // Constructors

    /** default constructor */
    public LedgerOwnerTypeEntity() {
    }

    
    /** full constructor */
    public LedgerOwnerTypeEntity(String name, Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.name = name;
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ledger_owner_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getLedgerOwnerTypeId() {
        return this.ledgerOwnerTypeId;
    }
    
    public void setLedgerOwnerTypeId(Integer ledgerOwnerTypeId) {
        this.ledgerOwnerTypeId = ledgerOwnerTypeId;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ledgerOwnerTypeEntity")

    public Set<LedgerSummaryEntity> getLedgerSummaryEntities() {
        return this.ledgerSummaryEntities;
    }
    
    public void setLedgerSummaryEntities(Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }
   








}