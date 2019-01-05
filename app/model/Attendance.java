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
	private int arrivalHours;
	private int arrivalMinutes;
	private String arrivalAmPm;
	private int departureHours;
	private int departureMinutes;
	private String departureAmPm;
	private int durationHours;
	private int durationMinutes;
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
	public int getArrivalHours() {
		return arrivalHours;
	}
	public void setArrivalHours(int arrivalHours) {
		this.arrivalHours = arrivalHours;
	}
	public int getArrivalMinutes() {
		return arrivalMinutes;
	}
	public void setArrivalMinutes(int arrivalMinutes) {
		this.arrivalMinutes = arrivalMinutes;
	}
	public String getArrivalAmPm() {
		return arrivalAmPm;
	}
	public void setArrivalAmPm(String arrivalAmPm) {
		this.arrivalAmPm = arrivalAmPm.toUpperCase().trim();
	}
	public int getDepartureHours() {
		return departureHours;
	}
	public void setDepartureHours(int departureHours) {
		this.departureHours = departureHours;
	}
	public int getDepartureMinutes() {
		return departureMinutes;
	}
	public void setDepartureMinutes(int departureMinutes) {
		this.departureMinutes = departureMinutes;
	}
	public String getDepartureAmPm() {
		return departureAmPm;
	}
	public void setDepartureAmPm(String departureAmPm) {
		this.departureAmPm = departureAmPm.toUpperCase().trim();
	}
	
	public int getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(int durationHours) {
		this.durationHours = durationHours;
	}
	public int getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(int durationMinutes) {
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