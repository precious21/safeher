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
 * FineChargeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="fine_charge"
    ,schema="dbo"
    
)

public class FineChargeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer fineChargeId;
     private FineEntity fineEntity;
     private TaxChargeEntity taxChargeEntity;
     private ReasonEntity reason;
     private Status status;
     private AppUserEntity appUser;
     private String isCharge;
     private Double amountDue;
     private Timestamp imposeDate;
     private Timestamp dueDate;
     private Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities = new HashSet<GeneralLedgerDetailEntity>(0);


    // Constructors

    /** default constructor */
    public FineChargeEntity() {
    }

    
    /** full constructor */
    public FineChargeEntity(FineEntity fineEntity, TaxChargeEntity taxChargeEntity, ReasonEntity reason, Status status, AppUserEntity appUser, String isCharge, Double amountDue, Timestamp imposeDate, Timestamp dueDate, Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.fineEntity = fineEntity;
        this.taxChargeEntity = taxChargeEntity;
        this.reason = reason;
        this.status = status;
        this.appUser = appUser;
        this.isCharge = isCharge;
        this.amountDue = amountDue;
        this.imposeDate = imposeDate;
        this.dueDate = dueDate;
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="fine_charge_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getFineChargeId() {
        return this.fineChargeId;
    }
    
    public void setFineChargeId(Integer fineChargeId) {
        this.fineChargeId = fineChargeId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="fine_id")

    public FineEntity getFineEntity() {
        return this.fineEntity;
    }
    
    public void setFineEntity(FineEntity fineEntity) {
        this.fineEntity = fineEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="charge_id")

    public TaxChargeEntity getTaxChargeEntity() {
        return this.taxChargeEntity;
    }
    
    public void setTaxChargeEntity(TaxChargeEntity taxChargeEntity) {
        this.taxChargeEntity = taxChargeEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="reason_fine_charge")

    public ReasonEntity getReason() {
        return this.reason;
    }
    
    public void setReason(ReasonEntity reason) {
        this.reason = reason;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="status_fine_charge")

    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="app_user_id")

    public AppUserEntity getAppUser() {
        return this.appUser;
    }
    
    public void setAppUser(AppUserEntity appUser) {
        this.appUser = appUser;
    }
    
    @Column(name="is_charge", length=1)

    public String getIsCharge() {
        return this.isCharge;
    }
    
    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }
    
    @Column(name="amount_due", precision=9)

    public Double getAmountDue() {
        return this.amountDue;
    }
    
    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }
    
    @Column(name="impose_date", length=23)

    public Timestamp getImposeDate() {
        return this.imposeDate;
    }
    
    public void setImposeDate(Timestamp imposeDate) {
        this.imposeDate = imposeDate;
    }
    
    @Column(name="due_date", length=23)

    public Timestamp getDueDate() {
        return this.dueDate;
    }
    
    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="fineChargeEntity")

    public Set<GeneralLedgerDetailEntity> getGeneralLedgerDetailEntities() {
        return this.generalLedgerDetailEntities;
    }
    
    public void setGeneralLedgerDetailEntities(Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }
   








}