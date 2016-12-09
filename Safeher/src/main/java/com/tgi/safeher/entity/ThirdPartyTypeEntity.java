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
 * ThirdPartyTypeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="third_party_type"
    ,schema="dbo"
    
)

public class ThirdPartyTypeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer thirdPartyTypeId;
     private String name;
     private Set<ThirdPartyInfoEntity> thirdPartyInfoEntities = new HashSet<ThirdPartyInfoEntity>(0);


    // Constructors

    /** default constructor */
    public ThirdPartyTypeEntity() {
    }

    
    /** full constructor */
    public ThirdPartyTypeEntity(String name, Set<ThirdPartyInfoEntity> thirdPartyInfoEntities) {
        this.name = name;
        this.thirdPartyInfoEntities = thirdPartyInfoEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="third_party_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getThirdPartyTypeId() {
        return this.thirdPartyTypeId;
    }
    
    public void setThirdPartyTypeId(Integer thirdPartyTypeId) {
        this.thirdPartyTypeId = thirdPartyTypeId;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="thirdPartyTypeEntity")

    public Set<ThirdPartyInfoEntity> getThirdPartyInfoEntities() {
        return this.thirdPartyInfoEntities;
    }
    
    public void setThirdPartyInfoEntities(Set<ThirdPartyInfoEntity> thirdPartyInfoEntities) {
        this.thirdPartyInfoEntities = thirdPartyInfoEntities;
    }
   








}