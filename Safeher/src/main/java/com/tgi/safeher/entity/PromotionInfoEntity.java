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
 * PromotionInfoEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="promotion_info"
    ,schema="dbo"
    
)

public class PromotionInfoEntity extends BaseEntity  implements java.io.Serializable {


    // Fields    

     private Integer promotionInfoId;
     private AppUserTypeEntity appUserType;
     private AppUserEntity appUser;
     private PromotionType promotionType;
     private String isSingle;
     private String durationInDays;
     private Timestamp startDate;
     private Timestamp expiryDate;
     private String isActive;
     private Integer amountValue;
     private String isPercentage;
     private Integer maxUseCount;
     private String countValue;
     private String isCount;
     private String promotionDescription;
     private String isPartialUse;
     private Integer partialValue;
     private Set<PromotionCodesEntity> promotionCodesEntities = new HashSet<PromotionCodesEntity>(0);


    // Constructors

    /** default constructor */
    public PromotionInfoEntity() {
    }

    
    /** full constructor */
    public PromotionInfoEntity(AppUserTypeEntity appUserType, AppUserEntity appUser, PromotionType promotionType, String isSingle, String durationInDays, Timestamp startDate, Timestamp expiryDate, String isActive, Integer amountValue, String isPercentage, Integer maxUseCount, String countValue, String isCount, Set<PromotionCodesEntity> promotionCodesEntities) {
        this.appUserType = appUserType;
        this.appUser = appUser;
        this.promotionType = promotionType;
        this.isSingle = isSingle;
        this.durationInDays = durationInDays;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
        this.amountValue = amountValue;
        this.isPercentage = isPercentage;
        this.maxUseCount = maxUseCount;
        this.countValue = countValue;
        this.isCount = isCount;
        this.promotionCodesEntities = promotionCodesEntities;
    }

   
    // Property accessors
    @GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "promotion_info_id", unique = true, nullable = false, precision = 9, scale = 0)
    
   public Integer getPromotionInfoId() {
        return this.promotionInfoId;
    }
    
    public void setPromotionInfoId(Integer promotionInfoId) {
        this.promotionInfoId = promotionInfoId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="app_user_type_id")

    public AppUserTypeEntity getAppUserType() {
        return this.appUserType;
    }
    
    public void setAppUserType(AppUserTypeEntity appUserType) {
        this.appUserType = appUserType;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ref_app_user")

    public AppUserEntity getAppUser() {
        return this.appUser;
    }
    
    public void setAppUser(AppUserEntity appUser) {
        this.appUser = appUser;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="promotion_type_id")

    public PromotionType getPromotionType() {
        return this.promotionType;
    }
    
    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }
    
    @Column(name="is_single", length=1)

    public String getIsSingle() {
        return this.isSingle;
    }
    
    public void setIsSingle(String isSingle) {
        this.isSingle = isSingle;
    }
    
    @Column(name="duration_in_days", length=20)

    public String getDurationInDays() {
        return this.durationInDays;
    }
    
    public void setDurationInDays(String durationInDays) {
        this.durationInDays = durationInDays;
    }
    
    @Column(name="start_date", length=23)

    public Timestamp getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    
    @Column(name="expiry_date", length=23)

    public Timestamp getExpiryDate() {
        return this.expiryDate;
    }
    
    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    @Column(name="is_active", length=1)

    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
    @Column(name="amount_value", precision=9, scale=0)

    public Integer getAmountValue() {
        return this.amountValue;
    }
    
    public void setAmountValue(Integer amountValue) {
        this.amountValue = amountValue;
    }
    
    @Column(name="is_percentage", length=1)

    public String getIsPercentage() {
        return this.isPercentage;
    }
    
    public void setIsPercentage(String isPercentage) {
        this.isPercentage = isPercentage;
    }
    
    @Column(name="max_use_count", precision=9, scale=0)

    public Integer getMaxUseCount() {
        return this.maxUseCount;
    }
    
    public void setMaxUseCount(Integer maxUseCount) {
        this.maxUseCount = maxUseCount;
    }
    
    @Column(name="count_value", length=18)

    public String getCountValue() {
        return this.countValue;
    }
    
    public void setCountValue(String countValue) {
        this.countValue = countValue;
    }
    
    @Column(name="is_count", length=1)

    public String getIsCount() {
        return this.isCount;
    }
    
    public void setIsCount(String isCount) {
        this.isCount = isCount;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionInfoEntity")

    public Set<PromotionCodesEntity> getPromotionCodesEntities() {
        return this.promotionCodesEntities;
    }
    
    public void setPromotionCodesEntities(Set<PromotionCodesEntity> promotionCodesEntities) {
        this.promotionCodesEntities = promotionCodesEntities;
    }

    @Column(name="partial_value", precision=5, scale=0)
	public Integer getPartialValue() {
		return partialValue;
	}


	public void setPartialValue(Integer partialValue) {
		this.partialValue = partialValue;
	}

    @Column(name="is_partial_use", length=1)
	public String getIsPartialUse() {
		return isPartialUse;
	}


	public void setIsPartialUse(String isPartialUse) {
		this.isPartialUse = isPartialUse;
	}

	@Column(name="promotion_description", length=100)
	public String getPromotionDescription() {
		return promotionDescription;
	}


	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
   








}