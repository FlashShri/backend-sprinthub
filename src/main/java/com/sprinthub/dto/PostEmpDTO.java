package com.sprinthub.dto;

public class PostEmpDTO {

	private String fullName;
	private String email;
	private String password;
	private long phoneNumber;
	private String city;
	public PostEmpDTO(String fullName, String email, String password, long phoneNumber, String city) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.city = city;
	}
	
	public PostEmpDTO() {
		super();
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "PostEmpDTO [fullName=" + fullName + ", email=" + email + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", city=" + city + "]";
	}
	
	
}
