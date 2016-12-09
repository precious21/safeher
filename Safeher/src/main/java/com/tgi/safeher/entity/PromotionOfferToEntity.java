package com.tgi.safeher.entity;
// default package

import com.tgi.safeher.entity.Status;
import com.tgi.safeher.entity.base.BaseEntity;

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


/**
 * PromotionOfferToEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="promotion_offer_to"
    ,schema="dbo"
    
)

public class PromotionOfferToEntity extends BaseEntity  implements java.io.Serializable {


    // Fields    

     private Integer promotionOfferToId;
     private PromotionCodesEntity promotionCodesEntity;
     private AppUserEntity appUser;
     private Status status;
     private String cellOn;
     private String emialId;
     private Timestamp offerDate;
     private Set<UserPromotionEntity> userPromotionEntities = new HashSet<UserPromotionEntity>(0);


    // Constructors

    /** default constructor */
    public PromotionOfferToEntity() {
    }

    
    /** full constructor */
    public PromotionOfferToEntity(PromotionCodesEntity promotionCodesEntity, AppUserEntity appUser, Status status, String cellOn, String emialId, Timestamp offerDate, Set<UserPromotionEntity> userPromotionEntities) {
        this.promotionCodesEntity = promotionCodesEntity;
        this.appUser = appUser;
        this.status = status;
        this.cellOn = cellOn;
        this.emialId = emialId;
        this.offerDate = offerDate;
        this.userPromotionEntities = userPromotionEntities;
    }

   
    // Property accessors
    @GenericGenerator(name = "generator", strategy = "increment")
  	@Id
  	@GeneratedValue(generator = "generator")
  	@Column(name = "promotion_offer_to_id", unique = true, nullable = false, precision = 9, scale = 0)
    public Integer getPromotionOfferToId() {
        return this.promotionOfferToId;
    }
    
    public void setPromotionOfferToId(Integer promotionOfferToId) {
        this.promotionOfferToId = promotionOfferToId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="promotion_codes_id")

    public PromotionCodesEntity getPromotionCodesEntity() {
        return this.promotionCodesEntity;
    }
    
    public void setPromotionCodesEntity(PromotionCodesEntity promotionCodesEntity) {
        this.promotionCodesEntity = promotionCodesEntity;
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
        @JoinColumn(name="offer_status")

    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Column(name="cell_on", length=20)

    public String getCellOn() {
        return this.cellOn;
    }
    
    public void setCellOn(String cellOn) {
        this.cellOn = cellOn;
    }
    
    @Column(name="emial_id", length=200)

    public String getEmialId() {
        return this.emialId;
    }
    
    public void setEmialId(String emialId) {
        this.emialId = emialId;
    }
    
    @Column(name="offer_date", length=23)

    public Timestamp getOfferDate() {
        return this.offerDate;
    }
    
    public void setOfferDate(Timestamp offerDate) {
        this.offerDate = offerDate;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionOfferToEntity")

    public Set<UserPromotionEntity> getUserPromotionEntities() {
        return this.userPromotionEntities;
    }
    
    public void setUserPromotionEntities(Set<UserPromotionEntity> userPromotionEntities) {
        this.userPromotionEntities = userPromotionEntities;
    }
   








}