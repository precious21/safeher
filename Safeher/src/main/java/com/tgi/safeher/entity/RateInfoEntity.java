package com.tgi.safeher.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * RateInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "rate_info", schema = "dbo")
public class RateInfoEntity implements java.io.Serializable {

	// Fields

	private Integer rateInfoId;
	private WeatherInfoEntity weatherInfo;
	private StateProvinceEntity stateProvince;
	private SurgeInfoEntity surgeInfo;
	private CityEntity city;
	private TimeStabEntity timeStab;
	private CountryEntity country;
	private Integer baseRate;
	private Integer ratePerKm;
	private Integer ratePerMint;
	private Integer minimumRate;
	private Timestamp startDate;
	private Timestamp endDate;
	private String isAcrtive;
	private Timestamp addedDate;
	private String addedUser;

	// Constructors

	/** default constructor */
	public RateInfoEntity() {
	}

	/** full constructor */
	public RateInfoEntity(WeatherInfoEntity weatherInfo, StateProvinceEntity stateProvince,
			SurgeInfoEntity surgeInfo, CityEntity city, TimeStabEntity timeStab, CountryEntity country,
			Integer baseRate, Integer ratePerKm, Integer ratePerMint,
			Integer minimumRate, Timestamp startDate, Timestamp endDate,
			String isAcrtive, Timestamp addedDate, String addedUser) {
		this.weatherInfo = weatherInfo;
		this.stateProvince = stateProvince;
		this.surgeInfo = surgeInfo;
		this.city = city;
		this.timeStab = timeStab;
		this.country = country;
		this.baseRate = baseRate;
		this.ratePerKm = ratePerKm;
		this.ratePerMint = ratePerMint;
		this.minimumRate = minimumRate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isAcrtive = isAcrtive;
		this.addedDate = addedDate;
		this.addedUser = addedUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "rate_info_id", unique = true, nullable = false, precision = 9, scale = 0)
	public Integer getRateInfoId() {
		return this.rateInfoId;
	}

	public void setRateInfoId(Integer rateInfoId) {
		this.rateInfoId = rateInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "weather_info_id")
	public WeatherInfoEntity getWeatherInfo() {
		return this.weatherInfo;
	}

	public void setWeatherInfo(WeatherInfoEntity weatherInfo) {
		this.weatherInfo = weatherInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	public StateProvinceEntity getStateProvince() {
		return this.stateProvince;
	}

	public void setStateProvince(StateProvinceEntity stateProvince) {
		this.stateProvince = stateProvince;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "surge_info_id")
	public SurgeInfoEntity getSurgeInfo() {
		return this.surgeInfo;
	}

	public void setSurgeInfo(SurgeInfoEntity surgeInfo) {
		this.surgeInfo = surgeInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	public CityEntity getCity() {
		return this.city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "time_stab_id")
	public TimeStabEntity getTimeStab() {
		return this.timeStab;
	}

	public void setTimeStab(TimeStabEntity timeStab) {
		this.timeStab = timeStab;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	@Column(name = "base_rate", precision = 9, scale = 0)
	public Integer getBaseRate() {
		return this.baseRate;
	}

	public void setBaseRate(Integer baseRate) {
		this.baseRate = baseRate;
	}

	@Column(name = "rate_per_km", precision = 9, scale = 0)
	public Integer getRatePerKm() {
		return this.ratePerKm;
	}

	public void setRatePerKm(Integer ratePerKm) {
		this.ratePerKm = ratePerKm;
	}

	@Column(name = "rate_per_mint", precision = 9, scale = 0)
	public Integer getRatePerMint() {
		return this.ratePerMint;
	}

	public void setRatePerMint(Integer ratePerMint) {
		this.ratePerMint = ratePerMint;
	}

	@Column(name = "minimum_rate", precision = 9, scale = 0)
	public Integer getMinimumRate() {
		return this.minimumRate;
	}

	public void setMinimumRate(Integer minimumRate) {
		this.minimumRate = minimumRate;
	}

	@Column(name = "start_date", length = 23)
	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", length = 23)
	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name = "is_acrtive", length = 20)
	public String getIsAcrtive() {
		return this.isAcrtive;
	}

	public void setIsAcrtive(String isAcrtive) {
		this.isAcrtive = isAcrtive;
	}

	@Column(name = "added_date", length = 23)
	public Timestamp getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}

	@Column(name = "added_user", length = 20)
	public String getAddedUser() {
		return this.addedUser;
	}

	public void setAddedUser(String addedUser) {
		this.addedUser = addedUser;
	}

}