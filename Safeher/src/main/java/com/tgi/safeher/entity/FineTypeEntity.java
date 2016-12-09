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
 * FineTypeEntity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="fine_type"
    ,schema="dbo"
    
)

public class FineTypeEntity extends BaseEntity implements java.io.Serializable {


    // Fields    

     private Integer fineTypeId;
     private String name;
     private Set<FineEntity> fineEntities = new HashSet<FineEntity>(0);


    // Constructors

    /** default constructor */
    public FineTypeEntity() {
    }

    
    /** full constructor */
    public FineTypeEntity(String name, Set<FineEntity> fineEntities) {
        this.name = name;
        this.fineEntities = fineEntities;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="fine_type_id", unique=true, nullable=false, precision=9, scale=0)

    public Integer getFineTypeId() {
        return this.fineTypeId;
    }
    
    public void setFineTypeId(Integer fineTypeId) {
        this.fineTypeId = fineTypeId;
    }
    
    @Column(name="name", length=20)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="fineTypeEntity")

    public Set<FineEntity> getFineEntities() {
        return this.fineEntities;
    }
    
    public void setFineEntities(Set<FineEntity> fineEntities) {
        this.fineEntities = fineEntities;
    }
   








}