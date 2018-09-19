package com.shawtravel.booking.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="t_booking_details")
public class BookingDetails implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "booking_id", nullable = false)
	private Long id;
	
	@NotNull
	@Column(name="user_id")
	private Long user_id;
	
	@NotEmpty
	@Column(name="start_location")
	private String startLocation;
	
	@Column(name="start_location_lat")
	private String startLocationLatitude;
	
	@Column(name="start_location_long")
	private String startLocationLong;
	
	@NotEmpty
	@Column(name="end_location")
	private String endLocation;
	
	@Column(name="end_location_lat")
	private String endLocationLatitude;
	
	@Column(name="end_location_long")
	private String endLocationLong;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="booking_start_date")
	private Date bookingCreationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="booking_end_date")
	private Date bookingEndDate;
	
	@NotNull
	@JsonProperty
	@Column(name="is_return_requested",columnDefinition="tinyint(1) default 0")
	private int isReturn;
	
	@NotNull
	@Column(name="no_of_traveller",columnDefinition="int(11) default 1")
	private int noOfTraveller;
	
	@NotNull
	@Column(name="no_of_days",columnDefinition="int(11) default 1")	
	private int noOfDays;
	
	@NotNull
	@JsonProperty
	@Column(name="share_ride",columnDefinition="tinyint(1) default 0")
	private int shareRide;
	
	@Column(name="booking_status")
	private String bookingStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdOn;
	
	@Column(name="created_by",columnDefinition="varchar(255) default 'Admin'")
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_on")
	private Date modifiedOn;
	
	@Column(name="modified_by")
	private String modifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getStartLocationLatitude() {
		return startLocationLatitude;
	}

	public void setStartLocationLatitude(String startLocationLatitude) {
		this.startLocationLatitude = startLocationLatitude;
	}

	public String getStartLocationLong() {
		return startLocationLong;
	}

	public void setStartLocationLong(String startLocationLong) {
		this.startLocationLong = startLocationLong;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getEndLocationLatitude() {
		return endLocationLatitude;
	}

	public void setEndLocationLatitude(String endLocationLatitude) {
		this.endLocationLatitude = endLocationLatitude;
	}

	public String getEndLocationLong() {
		return endLocationLong;
	}

	public void setEndLocationLong(String endLocationLong) {
		this.endLocationLong = endLocationLong;
	}

	public Date getBookingCreationDate() {
		return bookingCreationDate;
	}

	public void setBookingCreationDate(Date bookingCreationDate) {
		this.bookingCreationDate = bookingCreationDate;
	}

	public Date getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(Date bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

	public int getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(int isReturn) {
		this.isReturn = isReturn;
	}

	public int getShareRide() {
		return shareRide;
	}

	public int getNoOfTraveller() {
		return noOfTraveller;
	}

	public void setNoOfTraveller(int noOfTraveller) {
		this.noOfTraveller = noOfTraveller;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int isShareRide() {
		return shareRide;
	}

	public void setShareRide(int shareRide) {
		this.shareRide = shareRide;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
