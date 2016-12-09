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
 * ThirdPartyInfoEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="third_party_info"
    ,schema="dbo"
    
)

public class ThirdPartyInfoEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer thirdPartyInfoId;
     private ThirdPartyTypeEntity thirdPartyTypeEntity;
     private String isActive;
     private String name;
     private Set<LedgerSummaryEntity> ledgerSummaryEntities = new HashSet<LedgerSummaryEntity>(0);


    // Constructors

    /** default constructor */
    public ThirdPartyInfoEntity() {
    }

    
    /** full constructor */
    public ThirdPartyInfoEntity(ThirdPartyTypeEntity thirdPartyTypeEntity, String isActive, String name, Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.thirdPartyTypeEntity = thirdPartyTypeEntity;
        this.isActive = isActive;
        this.name = name;
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="third_party_info_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getThirdPartyInfoId() {
        return this.thirdPartyInfoId;
    }
    
    public void setThirdPartyInfoId(Integer thirdPartyInfoId) {
        this.thirdPartyInfoId = thirdPartyInfoId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="third_party_type_id")

    public ThirdPartyTypeEntity getThirdPartyTypeEntity() {
        return this.thirdPartyTypeEntity;
    }
    
    public void setThirdPartyTypeEntity(ThirdPartyTypeEntity thirdPartyTypeEntity) {
        this.thirdPartyTypeEntity = thirdPartyTypeEntity;
    }
    
    @Column(name="is_active", length=1)

    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="thirdPartyInfoEntity")

    public Set<LedgerSummaryEntity> getLedgerSummaryEntities() {
        return this.ledgerSummaryEntities;
    }
    
    public void setLedgerSummaryEntities(Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }
   








}