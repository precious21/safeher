package com.tgi.safeher.entity;
// default package

import static javax.persistence.GenerationType.IDENTITY;

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
 * SafeHerAccountInfoEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="safe_accounts_info"
    ,schema="dbo"
    
)

public class SafeHerAccountInfoEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer safeAccountsInfoId;
     private BankEntity bank;
     private CityEntity city;
     private CountryEntity country;
     private String accountNo;
     private String isActive;
     private Set<LedgerSummaryEntity> ledgerSummaryEntities = new HashSet<LedgerSummaryEntity>(0);


    // Constructors

    /** default constructor */
    public SafeHerAccountInfoEntity() {
    }

    
    /** full constructor */
    public SafeHerAccountInfoEntity(BankEntity bank, CityEntity city, CountryEntity country, String accountNo, String isActive, Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.bank = bank;
        this.city = city;
        this.country = country;
        this.accountNo = accountNo;
        this.isActive = isActive;
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="safe_accounts_info_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getSafeAccountsInfoId() {
        return this.safeAccountsInfoId;
    }
    
    public void setSafeAccountsInfoId(Integer safeAccountsInfoId) {
        this.safeAccountsInfoId = safeAccountsInfoId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="bank_id")

    public BankEntity getBank() {
        return this.bank;
    }
    
    public void setBank(BankEntity bank) {
        this.bank = bank;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="city_id")

    public CityEntity getCity() {
        return this.city;
    }
    
    public void setCity(CityEntity city) {
        this.city = city;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="country_id")

    public CountryEntity getCountry() {
        return this.country;
    }
    
    public void setCountry(CountryEntity country) {
        this.country = country;
    }
    
    @Column(name="account_no", length=30)

    public String getAccountNo() {
        return this.accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    @Column(name="is_active", length=1)

    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="safeHerAccountInfoEntity")

    public Set<LedgerSummaryEntity> getLedgerSummaryEntities() {
        return this.ledgerSummaryEntities;
    }
    
    public void setLedgerSummaryEntities(Set<LedgerSummaryEntity> ledgerSummaryEntities) {
        this.ledgerSummaryEntities = ledgerSummaryEntities;
    }
   








}