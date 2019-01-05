package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResetPassword {
	
	@Id
	private String resetPasswordId;
	private String emailAddress;
	private int employeeId;
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Date createdDateTime;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getResetPasswordId() {
		return resetPasswordId;
	}
	public void setResetPasswordId(String resetPasswordId) {
		this.resetPasswordId = resetPasswordId;
	}
	
}
