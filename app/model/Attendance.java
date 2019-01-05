package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long attendanceId;
	private String attendanceDate;
	private String arrivalHours;
	private String arrivalMinutes;
	private String arrivalAmPm;
	private String departureHours;
	private String departureMinutes;
	private String departureAmPm;
	private String durationHours;
	private String durationMinutes;
	private String present;
	private String approved;
	private int employeeId;
	
	public long getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
	}
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate.trim();
	}
	public String getArrivalHours() {
		return arrivalHours;
	}
	public void setArrivalHours(String arrivalHours) {
		this.arrivalHours = arrivalHours;
	}
	public String getArrivalMinutes() {
		return arrivalMinutes;
	}
	public void setArrivalMinutes(String arrivalMinutes) {
		this.arrivalMinutes = arrivalMinutes;
	}
	public String getArrivalAmPm() {
		return arrivalAmPm;
	}
	public void setArrivalAmPm(String arrivalAmPm) {
		this.arrivalAmPm = arrivalAmPm.toUpperCase().trim();
	}
	public String getDepartureHours() {
		return departureHours;
	}
	public void setDepartureHours(String departureHours) {
		this.departureHours = departureHours;
	}
	public String getDepartureMinutes() {
		return departureMinutes;
	}
	public void setDepartureMinutes(String departureMinutes) {
		this.departureMinutes = departureMinutes;
	}
	public String getDepartureAmPm() {
		return departureAmPm;
	}
	public void setDepartureAmPm(String departureAmPm) {
		this.departureAmPm = departureAmPm.toUpperCase().trim();
	}
	
	public String getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}
	public String getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(String durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}