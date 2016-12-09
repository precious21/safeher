package com.tgi.safeher.entity;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * GeneralLedgerDetailEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="gl_ledger_detial"
    ,schema="dbo"
    
)

public class GeneralLedgerDetailEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer glLedgerDetialId;
     private ReasonEntity reason;
     private BankEntity bank;
     private GeneralLedgerEntity generalLedgerEntity;
     private LedgerSummaryEntity ledgerSummaryEntity;
     private FineChargeEntity fineChargeEntity;
     private String chequeNo;
     private Double bankFine;
     private Double safeherFine;


    // Constructors

    /** default constructor */
    public GeneralLedgerDetailEntity() {
    }

    
    /** full constructor */
    public GeneralLedgerDetailEntity(ReasonEntity reason, BankEntity bank, GeneralLedgerEntity generalLedgerEntity, LedgerSummaryEntity ledgerSummaryEntity, FineChargeEntity fineChargeEntity, String chequeNo, Double bankFine, Double safeherFine) {
        this.reason = reason;
        this.bank = bank;
        this.generalLedgerEntity = generalLedgerEntity;
        this.ledgerSummaryEntity = ledgerSummaryEntity;
        this.fineChargeEntity = fineChargeEntity;
        this.chequeNo = chequeNo;
        this.bankFine = bankFine;
        this.safeherFine = safeherFine;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="gl_ledger_detial_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getGlLedgerDetialId() {
        return this.glLedgerDetialId;
    }
    
    public void setGlLedgerDetialId(Integer glLedgerDetialId) {
        this.glLedgerDetialId = glLedgerDetialId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="reason_reverse")

    public ReasonEntity getReason() {
        return this.reason;
    }
    
    public void setReason(ReasonEntity reason) {
        this.reason = reason;
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
        @JoinColumn(name="general_ledger_id")

    public GeneralLedgerEntity getGeneralLedgerEntity() {
        return this.generalLedgerEntity;
    }
    
    public void setGeneralLedgerEntity(GeneralLedgerEntity generalLedgerEntity) {
        this.generalLedgerEntity = generalLedgerEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ledger_summary_id")

    public LedgerSummaryEntity getLedgerSummaryEntity() {
        return this.ledgerSummaryEntity;
    }
    
    public void setLedgerSummaryEntity(LedgerSummaryEntity ledgerSummaryEntity) {
        this.ledgerSummaryEntity = ledgerSummaryEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="fine_charge_id")

    public FineChargeEntity getFineChargeEntity() {
        return this.fineChargeEntity;
    }
    
    public void setFineChargeEntity(FineChargeEntity fineChargeEntity) {
        this.fineChargeEntity = fineChargeEntity;
    }
    
    @Column(name="cheque_no", length=20)

    public String getChequeNo() {
        return this.chequeNo;
    }
    
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }
    
    @Column(name="bank_fine", precision=9)

    public Double getBankFine() {
        return this.bankFine;
    }
    
    public void setBankFine(Double bankFine) {
        this.bankFine = bankFine;
    }
    
    @Column(name="safeher_fine", precision=9)

    public Double getSafeherFine() {
        return this.safeherFine;
    }
    
    public void setSafeherFine(Double safeherFine) {
        this.safeherFine = safeherFine;
    }
   








}