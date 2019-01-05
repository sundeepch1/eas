package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.text.WordUtils;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int employeeId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String employeeType;
	private int age;
	private String dateOfBirth;
	private String address;
	private long mobileNumber;
	private String emailAddress;
	private String password;
	@Transient
	private String confirmPassword;
	
	//Getter Setter methods for Employee class
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = WordUtils.capitalize(firstName.trim());
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = WordUtils.capitalize(middleName.trim());
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = WordUtils.capitalize(lastName.trim());
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = WordUtils.capitalize(gender.trim());
	}
	
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth.trim();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address.trim();
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.trim().toLowerCase();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password!=null?password.trim():null;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
