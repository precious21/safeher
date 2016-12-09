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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tgi.safeher.entity.base.BaseEntity;


/**
 * PromotionType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "promotion_type", schema = "dbo"

)

public class PromotionType  extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer promotionTypeId;
     private String name;
     private Set<PromotionInfoEntity> promotionInfoEntities = new HashSet<PromotionInfoEntity>(0);
     private Set<UserPromotionEntity> userPromotionEntities = new HashSet<UserPromotionEntity>(0);


    // Constructors

    /** default constructor */
    public PromotionType() {
    }

    
    /** full constructor */
    public PromotionType(String name, Set<PromotionInfoEntity> promotionInfoEntities, Set<UserPromotionEntity> userPromotionEntities) {
        this.name = name;
        this.promotionInfoEntities = promotionInfoEntities;
        this.userPromotionEntities = userPromotionEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="promotion_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getPromotionTypeId() {
        return this.promotionTypeId;
    }
    
    public void setPromotionTypeId(Integer promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionType")

    public Set<PromotionInfoEntity> getPromotionInfoEntities() {
        return this.promotionInfoEntities;
    }
    
    public void setPromotionInfoEntities(Set<PromotionInfoEntity> promotionInfoEntities) {
        this.promotionInfoEntities = promotionInfoEntities;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="promotionType")

    public Set<UserPromotionEntity> getUserPromotionEntities() {
        return this.userPromotionEntities;
    }
    
    public void setUserPromotionEntities(Set<UserPromotionEntity> userPromotionEntities) {
        this.userPromotionEntities = userPromotionEntities;
    }
   








}