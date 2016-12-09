package com.tgi.safeher.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.tgi.safeher.entity.base.BaseEntity;

/**
 * GiftType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gift_type", schema = "dbo")
public class GiftTypeEntity extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer giftTypeId;
	private String name;
	private Set<RideGiftEntity> rideGifts = new HashSet<RideGiftEntity>(0);

	// Constructors

	/** default constructor */
	public GiftTypeEntity() {
	}

	/** full constructor */
	public GiftTypeEntity(String name, Set<RideGiftEntity> rideGifts) {
		this.name = name;
		this.rideGifts = rideGifts;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "gift_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getGiftTypeId() {
		return this.giftTypeId;
	}

	public void setGiftTypeId(Integer giftTypeId) {
		this.giftTypeId = giftTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "giftType")
	public Set<RideGiftEntity> getRideGifts() {
		return this.rideGifts;
	}

	public void setRideGifts(Set<RideGiftEntity> rideGifts) {
		this.rideGifts = rideGifts;
	}

}