package com.tgi.safeher.entity;
// default package

import java.sql.Timestamp;
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

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * UserPromotionUseEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="user_promotion_use"
    ,schema="dbo"
    
)

public class UserPromotionUseEntity extends BaseEntity  implements java.io.Serializable {


    // Fields    

     private Integer userPromotionUseId;
     private RideBillEntity rideBill;
     private UserPromotionEntity userPromotionEntity;
     private Timestamp useDate;
     private Integer useAmount;
     private Integer rideCountRequired;
     private Integer rideCountUsed;
     private String isCompleted;
     private Set<GeneralLedgerEntity> generalLedgerEntities = new HashSet<GeneralLedgerEntity>(0);


    // Constructors

    /** default constructor */
    public UserPromotionUseEntity() {
    }

    
    /** full constructor */
    public UserPromotionUseEntity(RideBillEntity rideBill, UserPromotionEntity userPromotionEntity, Timestamp useDate, Integer useAmount, Integer rideCountRequired, Integer rideCountUsed, String isCompleted, Set<GeneralLedgerEntity> generalLedgerEntities) {
        this.rideBill = rideBill;
        this.userPromotionEntity = userPromotionEntity;
        this.useDate = useDate;
        this.useAmount = useAmount;
        this.rideCountRequired = rideCountRequired;
        this.rideCountUsed = rideCountUsed;
        this.isCompleted = isCompleted;
        this.generalLedgerEntities = generalLedgerEntities;
    }

   
    // Property accessors
    @GenericGenerator(name = "generator", strategy = "increment")
  	@Id
  	@GeneratedValue(generator = "generator")
  	@Column(name = "user_promotion_use_id", unique = true, nullable = false, precision = 9, scale = 0)
    public Integer getUserPromotionUseId() {
        return this.userPromotionUseId;
    }
    
    public void setUserPromotionUseId(Integer userPromotionUseId) {
        this.userPromotionUseId = userPromotionUseId;
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
        @JoinColumn(name="user_promotion_id")

    public UserPromotionEntity getUserPromotionEntity() {
        return this.userPromotionEntity;
    }
    
    public void setUserPromotionEntity(UserPromotionEntity userPromotionEntity) {
        this.userPromotionEntity = userPromotionEntity;
    }
    
    @Column(name="use_date", length=23)

    public Timestamp getUseDate() {
        return this.useDate;
    }
    
    public void setUseDate(Timestamp useDate) {
        this.useDate = useDate;
    }
    
    @Column(name="use_amount", precision=9, scale=0)

    public Integer getUseAmount() {
        return this.useAmount;
    }
    
    public void setUseAmount(Integer useAmount) {
        this.useAmount = useAmount;
    }
    
    @Column(name="ride_count_required", precision=6, scale=0)

    public Integer getRideCountRequired() {
        return this.rideCountRequired;
    }
    
    public void setRideCountRequired(Integer rideCountRequired) {
        this.rideCountRequired = rideCountRequired;
    }
    
    @Column(name="ride_count_used", precision=6, scale=0)

    public Integer getRideCountUsed() {
        return this.rideCountUsed;
    }
    
    public void setRideCountUsed(Integer rideCountUsed) {
        this.rideCountUsed = rideCountUsed;
    }
    
    @Column(name="is_completed", length=1)

    public String getIsCompleted() {
        return this.isCompleted;
    }
    
    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="userPromotionUseEntity")

    public Set<GeneralLedgerEntity> getGeneralLedgerEntities() {
        return this.generalLedgerEntities;
    }
    
    public void setGeneralLedgerEntities(Set<GeneralLedgerEntity> generalLedgerEntities) {
        this.generalLedgerEntities = generalLedgerEntities;
    }
   








}