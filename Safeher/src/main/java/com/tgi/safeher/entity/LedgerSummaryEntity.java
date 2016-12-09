package com.tgi.safeher.entity;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * LedgerSummaryEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="ledger_summary"
    ,schema="dbo"
    
)

public class LedgerSummaryEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer ledgerSummaryId;
     private CharitiesEntity charities;
     private LedgerOwnerTypeEntity ledgerOwnerTypeEntity;
     private SafeHerAccountInfoEntity safeHerAccountInfoEntity;
     private AppUserEntity appUser;
     private ThirdPartyInfoEntity thirdPartyInfoEntity;
     private Double totalAmount;
     private String isCr;
     private Timestamp lastTrasactionDate;
     private String isLastTrsactionCr;
     private Double lastTractionAmount;
     private Set<GeneralLedgerEntity> generalLedgerEntities = new HashSet<GeneralLedgerEntity>(0);
     private Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities = new HashSet<GeneralLedgerDetailEntity>(0);


    // Constructors

    /** default constructor */
    public LedgerSummaryEntity() {
    }

    
    /** full constructor */
    public LedgerSummaryEntity(CharitiesEntity charities, LedgerOwnerTypeEntity ledgerOwnerTypeEntity, SafeHerAccountInfoEntity safeHerAccountInfoEntity, AppUserEntity appUser, ThirdPartyInfoEntity thirdPartyInfoEntity, Double totalAmount, String isCr, Timestamp lastTrasactionDate, String isLastTrsactionCr, Double lastTractionAmount, Set<GeneralLedgerEntity> generalLedgerEntities, Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.charities = charities;
        this.ledgerOwnerTypeEntity = ledgerOwnerTypeEntity;
        this.safeHerAccountInfoEntity = safeHerAccountInfoEntity;
        this.appUser = appUser;
        this.thirdPartyInfoEntity = thirdPartyInfoEntity;
        this.totalAmount = totalAmount;
        this.isCr = isCr;
        this.lastTrasactionDate = lastTrasactionDate;
        this.isLastTrsactionCr = isLastTrsactionCr;
        this.lastTractionAmount = lastTractionAmount;
        this.generalLedgerEntities = generalLedgerEntities;
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ledger_summary_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getLedgerSummaryId() {
        return this.ledgerSummaryId;
    }
    
    public void setLedgerSummaryId(Integer ledgerSummaryId) {
        this.ledgerSummaryId = ledgerSummaryId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="charities_id")

    public CharitiesEntity getCharities() {
        return this.charities;
    }
    
    public void setCharities(CharitiesEntity charities) {
        this.charities = charities;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ledger_owner_type_id")

    public LedgerOwnerTypeEntity getLedgerOwnerTypeEntity() {
        return this.ledgerOwnerTypeEntity;
    }
    
    public void setLedgerOwnerTypeEntity(LedgerOwnerTypeEntity ledgerOwnerTypeEntity) {
        this.ledgerOwnerTypeEntity = ledgerOwnerTypeEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="safe_accounts_info_id")

    public SafeHerAccountInfoEntity getSafeHerAccountInfoEntity() {
        return this.safeHerAccountInfoEntity;
    }
    
    public void setSafeHerAccountInfoEntity(SafeHerAccountInfoEntity safeHerAccountInfoEntity) {
        this.safeHerAccountInfoEntity = safeHerAccountInfoEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="app_user_id")

    public AppUserEntity getAppUser() {
        return this.appUser;
    }
    
    public void setAppUser(AppUserEntity appUser) {
        this.appUser = appUser;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="third_party_info_id")

    public ThirdPartyInfoEntity getThirdPartyInfoEntity() {
        return this.thirdPartyInfoEntity;
    }
    
    public void setThirdPartyInfoEntity(ThirdPartyInfoEntity thirdPartyInfoEntity) {
        this.thirdPartyInfoEntity = thirdPartyInfoEntity;
    }
    
    @Column(name="total_amount", precision=13)

    public Double getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    @Column(name="is_cr", length=1)

    public String getIsCr() {
        return this.isCr;
    }
    
    public void setIsCr(String isCr) {
        this.isCr = isCr;
    }
    
    @Column(name="last_trasaction_date", length=23)

    public Timestamp getLastTrasactionDate() {
        return this.lastTrasactionDate;
    }
    
    public void setLastTrasactionDate(Timestamp lastTrasactionDate) {
        this.lastTrasactionDate = lastTrasactionDate;
    }
    
    @Column(name="is_last_trsaction_cr", length=1)

    public String getIsLastTrsactionCr() {
        return this.isLastTrsactionCr;
    }
    
    public void setIsLastTrsactionCr(String isLastTrsactionCr) {
        this.isLastTrsactionCr = isLastTrsactionCr;
    }
    
    @Column(name="last_traction_amount", precision=9)

    public Double getLastTractionAmount() {
        return this.lastTractionAmount;
    }
    
    public void setLastTractionAmount(Double lastTractionAmount) {
        this.lastTractionAmount = lastTractionAmount;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ledgerSummaryEntity")

    public Set<GeneralLedgerEntity> getGeneralLedgerEntities() {
        return this.generalLedgerEntities;
    }
    
    public void setGeneralLedgerEntities(Set<GeneralLedgerEntity> generalLedgerEntities) {
        this.generalLedgerEntities = generalLedgerEntities;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ledgerSummaryEntity")

    public Set<GeneralLedgerDetailEntity> getGeneralLedgerDetailEntities() {
        return this.generalLedgerDetailEntities;
    }
    
    public void setGeneralLedgerDetailEntities(Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }
   








}