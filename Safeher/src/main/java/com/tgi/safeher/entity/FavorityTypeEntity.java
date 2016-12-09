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

/**
 * FavorityType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "favority_type", schema = "dbo")
public class FavorityTypeEntity implements java.io.Serializable {

	// Fields

	private Integer favorityTypeId;
	private String name;
	private Set<UserFavoritiesEntity> userFavoritieses = new HashSet<UserFavoritiesEntity>(
			0);

	// Constructors

	/** default constructor */
	public FavorityTypeEntity() {
	}

	/** full constructor */
	public FavorityTypeEntity(String name, Set<UserFavoritiesEntity> userFavoritieses) {
		this.name = name;
		this.userFavoritieses = userFavoritieses;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "favority_type_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getFavorityTypeId() {
		return this.favorityTypeId;
	}

	public void setFavorityTypeId(Integer favorityTypeId) {
		this.favorityTypeId = favorityTypeId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "favorityType")
	public Set<UserFavoritiesEntity> getUserFavoritieses() {
		return this.userFavoritieses;
	}

	public void setUserFavoritieses(Set<UserFavoritiesEntity> userFavoritieses) {
		this.userFavoritieses = userFavoritieses;
	}

}