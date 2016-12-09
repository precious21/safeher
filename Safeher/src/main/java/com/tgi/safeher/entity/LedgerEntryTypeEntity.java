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
 * LedgerEntryType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ledger_entry_type"
    ,schema="dbo"
    
)

public class LedgerEntryTypeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer ledgerEntryTypeId;
     private String name;
     private Set<GeneralLedgerEntity> generalLedgerEntities = new HashSet<GeneralLedgerEntity>(0);


    // Constructors

    /** default constructor */
    public LedgerEntryTypeEntity() {
    }

    
    /** full constructor */
    public LedgerEntryTypeEntity(String name, Set<GeneralLedgerEntity> generalLedgerEntities) {
        this.name = name;
        this.generalLedgerEntities = generalLedgerEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ledger_entry_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getLedgerEntryTypeId() {
        return this.ledgerEntryTypeId;
    }
    
    public void setLedgerEntryTypeId(Integer ledgerEntryTypeId) {
        this.ledgerEntryTypeId = ledgerEntryTypeId;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ledgerEntryType")

    public Set<GeneralLedgerEntity> getGeneralLedgerEntities() {
        return this.generalLedgerEntities;
    }
    
    public void setGeneralLedgerEntities(Set<GeneralLedgerEntity> generalLedgerEntities) {
        this.generalLedgerEntities = generalLedgerEntities;
    }
   








}