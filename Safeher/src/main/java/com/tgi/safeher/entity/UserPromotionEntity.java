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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * UserPromotionEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="user_promotion"
    ,schema="dbo"
    
)

public class UserPromotionEntity extends BaseEntity  implements java.io.Serializable {


    // Fields    

     private Integer userPromotionId;
     private PromotionCodesEntity promotionCodes;
     private PromotionOfferToEntity promotionOfferTo;
     private PromotionInfoEntity promotionInfo;
     private AppUserEntity appUser;
     private PromotionType promotionType;
     private Timestamp useStartDate;
     private Integer totalValue;
     private Timestamp useExpiryDate;
     private Timestamp lastUsedDate;
     private Integer totalUsedValue;
     private String isRefCreaterUser;
     private String isCompleted;
     private String isActive;
     private Integer countValue;
     private Integer useCountValue;
     private Set<UserPromotionUseEntity> userPromotionUseEntities = new HashSet<UserPromotionUseEntity>(0);


    // Constructors

    /** default constructor */
    public UserPromotionEntity() {
    }

    
    /** full constructor */
    public UserPromotionEntity(PromotionCodesEntity promotionCodesEntity, PromotionOfferToEntity promotionOfferToEntity, AppUserEntity appUser, Timestamp useStartDate, Integer totalValue, Timestamp useExpiryDate, Timestamp lastUsedDate, Integer totalUsedValue, String isRefCreaterUser, Set<UserPromotionUseEntity> userPromotionUseEntities) {
        this.promotionCodes = promotionCodesEntity;
        this.promotionOfferTo = promotionOfferToEntity;
        this.appUser = appUser;
        this.useStartDate = useStartDate;
        this.totalValue = totalValue;
        this.useExpiryDate = useExpiryDate;
        this.lastUsedDate = lastUsedDate;
        this.totalUsedValue = totalUsedValue;
        this.isRefCreaterUser = isRefCreaterUser;
        this.userPromotionUseEntities = userPromotionUseEntities;
    }

   
    // Property accessors
    @GenericGenerator(name = "generator", strategy = "increment")
  	@Id
  	@GeneratedValue(generator = "generator")
  	@Column(name = "user_promotion_id", unique = true, nullable = false, precision = 9, scale = 0)
    public Integer getUserPromotionId() {
        return this.userPromotionId;
    }
    
    public void setUserPromotionId(Integer userPromotionId) {
        this.userPromotionId = userPromotionId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="promotion_codes_id")

    public PromotionCodesEntity getPromotionCodesEntity() {
        return this.promotionCodes;
    }
    
    public void setPromotionCodesEntity(PromotionCodesEntity promotionCodesEntity) {
        this.promotionCodes = promotionCodesEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="promotion_offer_to_id")

    public PromotionOfferToEntity getPromotionOfferToEntity() {
        return this.promotionOfferTo;
    }
    
    public void setPromotionOfferToEntity(PromotionOfferToEntity promotionOfferToEntity) {
        this.promotionOfferTo = promotionOfferToEntity;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="consume_user")

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
    
    @Column(name="use_start_date", length=23)

    public Timestamp getUseStartDate() {
        return this.useStartDate;
    }
    
    public void setUseStartDate(Timestamp useStartDate) {
        this.useStartDate = useStartDate;
    }
    
    @Column(name="total_value", precision=9, scale=0)

    public Integer getTotalValue() {
        return this.totalValue;
    }
    
    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }
    
    @Column(name="use_expiry_date", length=23)

    public Timestamp getUseExpiryDate() {
        return this.useExpiryDate;
    }
    
    public void setUseExpiryDate(Timestamp useExpiryDate) {
        this.useExpiryDate = useExpiryDate;
    }
    
    @Column(name="last_used_date", length=23)

    public Timestamp getLastUsedDate() {
        return this.lastUsedDate;
    }
    
    public void setLastUsedDate(Timestamp lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }
    
    @Column(name="total_used_value", precision=9, scale=0)

    public Integer getTotalUsedValue() {
        return this.totalUsedValue;
    }
    
    public void setTotalUsedValue(Integer totalUsedValue) {
        this.totalUsedValue = totalUsedValue;
    }
    
    @Column(name="is_ref_creater_user", length=1)

    public String getIsRefCreaterUser() {
        return this.isRefCreaterUser;
    }
    
    public void setIsRefCreaterUser(String isRefCreaterUser) {
        this.isRefCreaterUser = isRefCreaterUser;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="userPromotionEntity")

    public Set<UserPromotionUseEntity> getUserPromotionUseEntities() {
        return this.userPromotionUseEntities;
    }
    
    public void setUserPromotionUseEntities(Set<UserPromotionUseEntity> userPromotionUseEntities) {
        this.userPromotionUseEntities = userPromotionUseEntities;
    }

    @Column(name="is_completed", length=1)
	public String getIsCompleted() {
		return isCompleted;
	}

	
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Column(name="is_active", length=1)
	public String getIsActive() {
		return isActive;
	}

	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="promotion_info_id")
	public PromotionInfoEntity getPromotionInfo() {
		return promotionInfo;
	}


	public void setPromotionInfo(PromotionInfoEntity promotionInfo) {
		this.promotionInfo = promotionInfo;
	}

    @Column(name="use_count_value", precision=9, scale=0)
	public Integer getUseCountValue() {
		return useCountValue;
	}


	public void setUseCountValue(Integer useCountValue) {
		this.useCountValue = useCountValue;
	}

	 @Column(name="count_value", precision=9, scale=0)
	public Integer getCountValue() {
		return countValue;
	}


	public void setCountValue(Integer countValue) {
		this.countValue = countValue;
	}
   








}