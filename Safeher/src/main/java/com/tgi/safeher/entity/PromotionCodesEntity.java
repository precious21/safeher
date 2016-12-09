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

import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * PromotionCodesEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="promotion_codes"
    ,schema="dbo"
    
)

public class PromotionCodesEntity  extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer promotionCodesId;
     private PromotionInfoEntity promotionInfoEntity;
     private String codeValue;
     private String isUsed;
     private Integer usedCount;
     private AppUserEntity appUser;
     private String shareType;
     private Set<PromotionOfferToEntity> promotionOfferToEntities = new HashSet<PromotionOfferToEntity>(0);
     private Set<UserPromotionEntity> userPromotionEntities = new HashSet<UserPromotionEntity>(0);


    // Constructors

    /** default constructor */
    public PromotionCodesEntity() {
    }

    
    /** full constructor */
    public PromotionCodesEntity(PromotionInfoEntity promotionInfoEntity, String codeValue, String isUsed, Integer usedCount, Set<PromotionOfferToEntity> promotionOfferToEntities, Set<UserPromotionEntity> userPromotionEntities) {
        this.promotionInfoEntity = promotionInfoEntity;
        this.codeValue = codeValue;
        this.isUsed = isUsed;
        this.usedCount = usedCount;
        this.promotionOfferToEntities = promotionOfferToEntities;
        this.userPromotionEntities = userPromotionEntities;
    }

   
    // Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "promotion_codes_id", unique = true, nullable = false, precision = 9, scale = 0)
    public Integer getPromotionCodesId() {
        return this.promotionCodesId;
    }
    
    public void setPromotionCodesId(Integer promotionCodesId) {
        this.promotionCodesId = promotionCodesId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="promotion_info_id")

    public PromotionInfoEntity getPromotionInfoEntity() {
        return this.promotionInfoEntity;
    }
    
    public void setPromotionInfoEntity(PromotionInfoEntity promotionInfoEntity) {
        this.promotionInfoEntity = promotionInfoEntity;
    }
    
    @Column(name="code_value", length=20)

    public String getCodeValue() {
        return this.codeValue;
    }
    
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    
    @Column(name="is_used", length=1)

    public String getIsUsed() {
        return this.isUsed;
    }
    
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
    
    @Column(name="used_count", precision=6, scale=0)

    public Integer getUsedCount() {
        return this.usedCount;
    }
    
    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ref_app_user_id")
	public AppUserEntity getAppUser() {
	    return this.appUser;
	}
	
	public void setAppUser(AppUserEntity appUser) {
	    this.appUser = appUser;
	}
	
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionCodesEntity")

    public Set<PromotionOfferToEntity> getPromotionOfferToEntities() {
        return this.promotionOfferToEntities;
    }
    
    public void setPromotionOfferToEntities(Set<PromotionOfferToEntity> promotionOfferToEntities) {
        this.promotionOfferToEntities = promotionOfferToEntities;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionCodesEntity")

    public Set<UserPromotionEntity> getUserPromotionEntities() {
        return this.userPromotionEntities;
    }
    
    public void setUserPromotionEntities(Set<UserPromotionEntity> userPromotionEntities) {
        this.userPromotionEntities = userPromotionEntities;
    }

    @Column(name="share_type", length=1)
	public String getShareType() {
		return shareType;
	}


	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
   








}