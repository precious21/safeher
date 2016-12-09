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
 * GeneralLedgerEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="general_ledger"
    ,schema="dbo"
    
)

public class GeneralLedgerEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer generalLedgerId;
     private LedgerEntryTypeEntity ledgerEntryType;
     private RideBillEntity rideBill;
     private StatusEntity statusByStatusPayment;
     private StatusEntity statusByStatusTrans;
     private PaymentModeEntity paymentMode;
     private RidePaymnetDistributionEntity ridePaymnetDistribution;
     private LedgerSummaryEntity ledgerSummaryEntity;
     private UserPromotionUseEntity userPromotionUseEntity;
     private Double amount;
     private String isCr;
     private Timestamp invoiceDate;
     private Timestamp paymentDate;
     private String isPartial;
     private Double partialAmount;
     private String isReversed;
     private String isFineCharge;
     private String isPromption;
     private Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities = new HashSet<GeneralLedgerDetailEntity>(0);


    // Constructors

    /** default constructor */
    public GeneralLedgerEntity() {
    }

    
    /** full constructor */
    public GeneralLedgerEntity(LedgerEntryTypeEntity ledgerEntryType, RideBillEntity rideBill, StatusEntity statusByStatusPayment, StatusEntity statusByStatusTrans, PaymentModeEntity paymentMode, RidePaymnetDistributionEntity ridePaymnetDistribution, LedgerSummaryEntity ledgerSummaryEntity, Double amount, String isCr, Timestamp invoiceDate, Timestamp paymentDate, String isPartial, Double partialAmount, String isReversed, String isFineCharge, Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.ledgerEntryType = ledgerEntryType;
        this.rideBill = rideBill;
        this.statusByStatusPayment = statusByStatusPayment;
        this.statusByStatusTrans = statusByStatusTrans;
        this.paymentMode = paymentMode;
        this.ridePaymnetDistribution = ridePaymnetDistribution;
        this.ledgerSummaryEntity = ledgerSummaryEntity;
        this.amount = amount;
        this.isCr = isCr;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.isPartial = isPartial;
        this.partialAmount = partialAmount;
        this.isReversed = isReversed;
        this.isFineCharge = isFineCharge;
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="general_ledger_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getGeneralLedgerId() {
        return this.generalLedgerId;
    }
    
    public void setGeneralLedgerId(Integer generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ledger_entry_type_id")

    public LedgerEntryTypeEntity getLedgerEntryType() {
        return this.ledgerEntryType;
    }
    
    public void setLedgerEntryType(LedgerEntryTypeEntity ledgerEntryType) {
        this.ledgerEntryType = ledgerEntryType;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ride_bill_id")

    public RideBillEntity getRideBill() {
        return this.rideBill;
    }
    
    public void setRideBill(RideBillEntity rideBill) {
        this.rideBill = rideBill;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="status_payment")

    public StatusEntity getStatusByStatusPayment() {
        return this.statusByStatusPayment;
    }
    
    public void setStatusByStatusPayment(StatusEntity statusByStatusPayment) {
        this.statusByStatusPayment = statusByStatusPayment;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="status_trans")

    public StatusEntity getStatusByStatusTrans() {
        return this.statusByStatusTrans;
    }
    
    public void setStatusByStatusTrans(StatusEntity statusByStatusTrans) {
        this.statusByStatusTrans = statusByStatusTrans;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="payment_mode_id")

    public PaymentModeEntity getPaymentMode() {
        return this.paymentMode;
    }
    
    public void setPaymentMode(PaymentModeEntity paymentMode) {
        this.paymentMode = paymentMode;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ride_paymnet_distribution_id")

    public RidePaymnetDistributionEntity getRidePaymnetDistribution() {
        return this.ridePaymnetDistribution;
    }
    
    public void setRidePaymnetDistribution(RidePaymnetDistributionEntity ridePaymnetDistribution) {
        this.ridePaymnetDistribution = ridePaymnetDistribution;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ledger_summary_id")

    public LedgerSummaryEntity getLedgerSummaryEntity() {
        return this.ledgerSummaryEntity;
    }
    
    public void setLedgerSummaryEntity(LedgerSummaryEntity ledgerSummaryEntity) {
        this.ledgerSummaryEntity = ledgerSummaryEntity;
    }
    
    @Column(name="amount", precision=9, scale=3)

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    @Column(name="is_cr", length=1)

    public String getIsCr() {
        return this.isCr;
    }
    
    public void setIsCr(String isCr) {
        this.isCr = isCr;
    }
    
    @Column(name="invoice_date", length=23)

    public Timestamp getInvoiceDate() {
        return this.invoiceDate;
    }
    
    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    @Column(name="payment_date", length=23)

    public Timestamp getPaymentDate() {
        return this.paymentDate;
    }
    
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    @Column(name="is_partial", length=1)

    public String getIsPartial() {
        return this.isPartial;
    }
    
    public void setIsPartial(String isPartial) {
        this.isPartial = isPartial;
    }
    
    @Column(name="partial_amount", precision=9)

    public Double getPartialAmount() {
        return this.partialAmount;
    }
    
    public void setPartialAmount(Double partialAmount) {
        this.partialAmount = partialAmount;
    }
    
    @Column(name="is_reversed", length=1)

    public String getIsReversed() {
        return this.isReversed;
    }
    
    public void setIsReversed(String isReversed) {
        this.isReversed = isReversed;
    }
    
    @Column(name="is_fine_charge", length=1)

    public String getIsFineCharge() {
        return this.isFineCharge;
    }
    
    public void setIsFineCharge(String isFineCharge) {
        this.isFineCharge = isFineCharge;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="generalLedgerEntity")

    public Set<GeneralLedgerDetailEntity> getGeneralLedgerDetailEntities() {
        return this.generalLedgerDetailEntities;
    }
    
    public void setGeneralLedgerDetailEntities(Set<GeneralLedgerDetailEntity> generalLedgerDetailEntities) {
        this.generalLedgerDetailEntities = generalLedgerDetailEntities;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_promotion_use_id")
	public UserPromotionUseEntity getUserPromotionUseEntity() {
		return userPromotionUseEntity;
	}


	public void setUserPromotionUseEntity(UserPromotionUseEntity userPromotionUseEntity) {
		this.userPromotionUseEntity = userPromotionUseEntity;
	}


	public String getIsPromption() {
		return isPromption;
	}


	public void setIsPromption(String isPromption) {
		this.isPromption = isPromption;
	}
   








}