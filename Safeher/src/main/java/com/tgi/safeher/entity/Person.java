package com.tgi.safeher.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Person implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	  private String id;
	  private String firstname;
	  private String lastname;
	  public Person(){
		  this.firstname="awais";
		  this.lastname="Haider";
	  }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + "]";
	}
	  

	  // … getters and setters omitted
	}