package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import model.*;
import play.libs.Json;
import play.mvc.*;
import employeeservices.*;

public class ApplicationController extends Controller {

	public Result register() {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);

			if ((!employee.getFirstName().isEmpty()) && (!employee.getLastName().isEmpty())
					&& (!employee.getGender().isEmpty()) && (!(Integer.toString(employee.getAge()).isEmpty()))
					&& (!employee.getEmployeeType().isEmpty()) && (!employee.getDateOfBirth().isEmpty())
					&& (!employee.getAddress().isEmpty()) && (!(Long.toString(employee.getMobileNumber()).isEmpty()))
					&& (!employee.getEmailAddress().isEmpty()) && (!employee.getPassword().isEmpty())
					&& (!employee.getConfirmPassword().isEmpty())) {

				if (employee.getPassword().equals(employee.getConfirmPassword())) {

					EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
					EntityManager entityManager = entityManagerDatabase.getEntityManager();

					String emailString = employee.getEmailAddress();
					Query query = entityManager
							.createQuery("Select count(*) from Employee e where e.emailAddress=:emailString");
					query.setParameter("emailString", emailString);
					long count = (long) query.getSingleResult();

					if (count < 1) {
						Encryption encryption = new Encryption();
						employee.setPassword(
								encryption.getEncryptedPassword(employee.getPassword(), employee.getPassword()));
						entityManager.getTransaction().begin();
						entityManager.persist(employee);
						entityManager.getTransaction().commit();

						entityManagerDatabase.closeEntityManager(entityManager);
						result = "s";
					} else {
						return ok("a");
					}
				} else {
					return ok("m");
				}
			} else {
				return ok("w");
			}

		} catch (JsonParseException e) {
			result = "Error occured try again " + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again Exception" + e.getMessage();
			// e.printStackTrace();
		}

		return ok(result);
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result addEmployee() {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);

			if ((!employee.getFirstName().isEmpty()) && (!employee.getLastName().isEmpty())
					&& (!employee.getGender().isEmpty()) && (!(Integer.toString(employee.getAge()).isEmpty()))
					&& (!employee.getEmployeeType().isEmpty()) && (!employee.getDateOfBirth().isEmpty())
					&& (!employee.getAddress().isEmpty()) && (!(Long.toString(employee.getMobileNumber()).isEmpty()))
					&& (!employee.getEmailAddress().isEmpty()) && (!employee.getPassword().isEmpty())
					&& (!employee.getConfirmPassword().isEmpty())) {

				if (employee.getPassword().equals(employee.getConfirmPassword())) {

					EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
					EntityManager entityManager = entityManagerDatabase.getEntityManager();

					String emailString = employee.getEmailAddress();
					Query query = entityManager
							.createQuery("Select count(*) from Employee e where e.emailAddress=:emailString");
					query.setParameter("emailString", emailString);
					long count = (long) query.getSingleResult();

					if (count < 1) {
						Encryption encryption = new Encryption();
						employee.setPassword(
								encryption.getEncryptedPassword(employee.getPassword(), employee.getPassword()));
						entityManager.getTransaction().begin();
						entityManager.persist(employee);
						entityManager.getTransaction().commit();
						result = "s";
					} else {
						return ok("a");
					}
				} else {
					return ok("m");
				}
			} else {
				return ok("w");
			}

		} catch (JsonParseException e) {
			result = "Error occured try again " + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again Exception" + e.getMessage();
			// e.printStackTrace();
		}

		return ok(result);
	}

	public Result adminLogin() {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			Query query = entityManager
					.createQuery("Select count(*) from Employee e where e.emailAddress=:emailString");
			query.setParameter("emailString", employee.getEmailAddress());
			long count = (long) query.getSingleResult();

			if (count == 1) {

				SecurityAuthentication securityAuthentication = new SecurityAuthentication();
				if (securityAuthentication.getEmployeeEmail(ctx()) != employee.getEmailAddress()) {
					if (securityAuthentication.isValidEmployee(employee.getEmailAddress(), employee.getPassword())) {
						session().clear();
						query = entityManager.createQuery("Select e from Employee e where e.emailAddress=:emailString");
						query.setParameter("emailString", employee.getEmailAddress());
						employee = (Employee) query.getSingleResult();
						session("email", employee.getEmailAddress());
						session("employeeId", String.valueOf(employee.getEmployeeId()));
						result = "s";// "Successfully logined";
					} else {
						result = "i";// invalid credentials
					}
				} else {
					result = "a";// "You have already logined";
				}
			} else {
				result = "n"; // "This email address is not registered";
			}
			/*
			 * if ((!employee.getEmailAddress().isEmpty()) &&
			 * (!employee.getPassword().isEmpty())) {
			 * 
			 * EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			 * EntityManager entityManager = entityManagerDatabase.getEntityManager();
			 * 
			 * String emailString = employee.getEmailAddress(); Query query = entityManager
			 * .createQuery("Select count(*) from Employee e where e.emailAddress=:emailString"
			 * ); query.setParameter("emailString", emailString); long count = (long)
			 * query.getSingleResult();
			 * 
			 * if (count == 1) { query = entityManager.
			 * createQuery("Select e from Employee e where e.emailAddress = :emailString");
			 * query.setParameter("emailString", emailString); Employee employeeInfo =
			 * (Employee) query.getSingleResult();
			 * 
			 * entityManagerDatabase.closeEntityManager(entityManager);
			 * 
			 * if (employeeInfo.getPassword().isEmpty()) { return ok("c"); } else { if
			 * (employee.getPassword().equals(employeeInfo.getPassword())) { return
			 * ok(Json.toJson(employeeInfo)); } } } else { result = "u"; } } else { return
			 * ok("w"); }
			 */
		} catch (JsonParseException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		}
		return ok(result);
	}

	public Result employeeLogin() {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			Query query = entityManager
					.createQuery("Select count(*) from Employee e where e.emailAddress=:emailString");
			query.setParameter("emailString", employee.getEmailAddress());
			long count = (long) query.getSingleResult();

			if (count == 1) {

				SecurityAuthentication securityAuthentication = new SecurityAuthentication();
				if (securityAuthentication.getEmployeeEmail(ctx()) != employee.getEmailAddress()) {
					if (securityAuthentication.isValidEmployee(employee.getEmailAddress(), employee.getPassword())) {
						session().clear();
						query = entityManager.createQuery("Select e from Employee e where e.emailAddress=:emailString");
						query.setParameter("emailString", employee.getEmailAddress());
						employee = (Employee) query.getSingleResult();
						session("email", employee.getEmailAddress());
						session("employeeId", String.valueOf(employee.getEmployeeId()));
						result = "s";// "Successfully logined";
					} else {
						result = "i";// invalid credentials
					}
				} else {
					result = "a";// "You have already logined";
				}
			} else {
				result = "n"; // "This email address is not registered";
			}
			/*
			 * if ((!employee.getEmailAddress().isEmpty()) &&
			 * (!employee.getPassword().isEmpty())) {
			 * 
			 * EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			 * EntityManager entityManager = entityManagerDatabase.getEntityManager();
			 * 
			 * String emailString = employee.getEmailAddress(); Query query = entityManager
			 * .createQuery("Select count(*) from Employee e where e.emailAddress=:emailString"
			 * ); query.setParameter("emailString", emailString); long count = (long)
			 * query.getSingleResult();
			 * 
			 * if (count == 1) { query = entityManager.
			 * createQuery("Select e from Employee e where e.emailAddress = :emailString");
			 * query.setParameter("emailString", emailString); Employee employeeInfo =
			 * (Employee) query.getSingleResult();
			 * 
			 * entityManagerDatabase.closeEntityManager(entityManager);
			 * 
			 * if (employeeInfo.getPassword().isEmpty()) { return ok("c"); } else { if
			 * (employee.getPassword().equals(employeeInfo.getPassword())) { return
			 * ok(Json.toJson(employeeInfo)); } } } else { result = "u"; } } else { return
			 * ok("w"); }
			 */
		} catch (JsonParseException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		}
		return ok(result);
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result logout() {
		session().clear();
		return ok("ok");
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result viewEmployees() {

		EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
		EntityManager entityManager = entityManagerDatabase.getEntityManager();

		Query query = entityManager.createQuery("Select e from Employee e");
		//query.setParameter("employeeType", "Administration");
		List<Employee> employees = query.getResultList();

		entityManagerDatabase.closeEntityManager(entityManager);

		employees.forEach(employee -> {
			employee.setPassword(null);
		});
		return ok(Json.toJson(employees));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result employeeDetails(Integer employeeId) {

		EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
		EntityManager entityManager = entityManagerDatabase.getEntityManager();

		Employee employee = entityManager.find(Employee.class, employeeId);

		entityManagerDatabase.closeEntityManager(entityManager);

		return ok(Json.toJson(employee));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result searchEmployee() {

		String jsonSearch = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> employees = null;
		try {

			Search search = objectMapper.readValue(jsonSearch, Search.class);
			
			/*if((!search.getSearchBy().isEmpty()) && (!search.getSearchText().isEmpty())) {
				return ok("w");
			}*/
			
			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();
			Query query = null;
			
			if (search.getSearchBy().equals("All")) {
				query = entityManager.createQuery("Select e from Employee e");
			}else if(search.getSearchBy().equals("Mobile Number")) {
				query = entityManager.createQuery("Select e from Employee e where str(e.mobileNumber) like :mobileNumber");
				query.setParameter("mobileNumber", "%"+search.getSearchText()+"%");
			}else if(search.getSearchBy().equals("Gender")) {
				query = entityManager.createQuery("Select e from Employee e where e.gender like :gender");
				query.setParameter("gender", "%"+search.getSearchText()+"%");
			}else if(search.getSearchBy().equals("Age")) {
				query = entityManager.createQuery("Select e from Employee e where str(e.age) like :age");
				query.setParameter("age", "%"+search.getSearchText()+"%");
			}else if(search.getSearchBy().equals("Email Address")) {
				query = entityManager.createQuery("Select e from Employee e where e.emailAddress like :emailAddress");
				query.setParameter("emailAddress", "%"+search.getSearchText()+"%");
			}else if(search.getSearchBy().equals("Name")) {
				query = entityManager.createQuery("Select e from Employee e where e.firstName like :name or e.middleName like :name or e.lastName like :name");
				query.setParameter("name", "%"+search.getSearchText()+"%");
			}
			
			employees = query.getResultList();
			
			if(employees == null) {
				return ok("null");
			}
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (JsonParseException e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		} catch (Exception e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}

		return ok(Json.toJson(employees));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result employeeEdit(Integer employeeId) {

		EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
		EntityManager entityManager = entityManagerDatabase.getEntityManager();

		Employee employee = entityManager.find(Employee.class, employeeId);
		employee.setPassword(null);
		entityManagerDatabase.closeEntityManager(entityManager);

		return ok(Json.toJson(employee));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result employeeSave() {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);
			Employee oldEmployeeDetails = entityManager.find(Employee.class, employee.getEmployeeId());

			employee.setPassword(oldEmployeeDetails.getPassword());
			entityManager.getTransaction().begin();
			entityManager.merge(employee);
			entityManager.getTransaction().commit();

			entityManagerDatabase.closeEntityManager(entityManager);

			result = "Successfully Saved";

		} catch (JsonParseException e) {
			result = "Error occured try again " + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again Exception" + e.getMessage();
			// e.printStackTrace();
		}

		return ok(result);
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result employeeDelete(Integer employeeId) {

		EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
		EntityManager entityManager = entityManagerDatabase.getEntityManager();

		entityManager.getTransaction().begin();
		Employee employee = entityManager.find(Employee.class, employeeId);
		entityManager.remove(employee);
		entityManager.getTransaction().commit();

		entityManagerDatabase.closeEntityManager(entityManager);

		return ok("Ok");
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result forgetPassword() {
		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			String emailString = employee.getEmailAddress();
			Query query = entityManager.createQuery("Select e from Employee e where e.emailAddress = :emailString");
			query.setParameter("emailString", emailString);
			Employee employeeSec = (Employee) query.getSingleResult();

			if (employeeSec != null) {

				String employeeName = employeeSec.getFirstName();

				ResetPassword resetPassword = new ResetPassword();
				String token = UUID.randomUUID().toString();
				resetPassword.setResetPasswordId(token);
				resetPassword.setEmailAddress(employeeSec.getEmailAddress());
				resetPassword.setEmployeeId((int) employeeSec.getEmployeeId());

				entityManager.getTransaction().begin();
				entityManager.persist(resetPassword);
				entityManager.flush();
				String resetPassword_Id = resetPassword.getResetPasswordId();
				entityManager.getTransaction().commit();

				SendEmail sendEmail = new SendEmail();
				result = sendEmail.sendEmailToEmployee(emailString, employeeName, resetPassword_Id);

				entityManagerDatabase.closeEntityManager(entityManager);
			} else {
				result = "Invalid email";
			}
		} catch (JsonParseException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (JsonMappingException e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		} catch (Exception e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		}

		return ok(result);
	}

	public Result resetPassword(String resetPasswordId) {

		String result = "";
		String jsonEmployee = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			ResetPassword resetPassword = (ResetPassword) entityManager.find(ResetPassword.class, resetPasswordId);

			Calendar calender = Calendar.getInstance();
			long diff = calender.getTime().getTime() - resetPassword.getCreatedDateTime().getTime();

			if (resetPassword != null && diff <= 5 * 60 * 1000) {

				Encryption encryption = new Encryption();
				Employee employee = objectMapper.readValue(jsonEmployee, Employee.class);
				Employee employeeSec = entityManager.find(Employee.class, resetPassword.getEmployeeId());
				employeeSec.setPassword(encryption.getEncryptedPassword(employee.getPassword(), employee.getPassword()));

				entityManager.getTransaction().begin();
				entityManager.merge(employeeSec);
				entityManager.getTransaction().commit();

				entityManager.getTransaction().begin();
				entityManager.remove(resetPassword);
				entityManager.getTransaction().commit();

				entityManagerDatabase.closeEntityManager(entityManager);
				result = "T";
			}
		} catch (Exception e) {
			result = "Error occured try again" + e.getMessage();
			// e.printStackTrace();
		}
		return ok(result);
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceDate() {

		Attendance attendance = new Attendance();
		attendance.setAttendanceDate(LocalDate.now().toString());

		return ok(Json.toJson(attendance));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceSave() {

		String jsonAttendance = request().body().asJson().toString();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();
			SecurityAuthentication securityAuthentication = new SecurityAuthentication();
			Attendance attendance = objectMapper.readValue(jsonAttendance, Attendance.class);
			attendance.setAttendanceDate(LocalDate.now().toString());
			attendance.setPresent("Present");
			attendance.setApproved("Unapproved");
			attendance.setEmployeeId(Integer.valueOf(securityAuthentication.getEmployeeId(ctx())));
			entityManager.getTransaction().begin();
			entityManager.persist(attendance);
			entityManager.getTransaction().commit();

			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok("Successfully attendance saved");
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceDetails(Integer employeeId) {

		List<Attendance> attendances = null;
		try {

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();
			SecurityAuthentication securityAuthentication = new SecurityAuthentication();
			Query query = entityManager.createQuery("Select a from Attendance a where a.employeeId = :employeeId");
			query.setParameter("employeeId", employeeId);
			attendances = query.getResultList();
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok(Json.toJson(attendances));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceAdminDetails(Integer employeeId) {

		List<Attendance> attendances = null;
		try {

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			Query query = entityManager.createQuery("Select a from Attendance a where a.employeeId = :employeeId");
			query.setParameter("employeeId", employeeId);
			attendances = query.getResultList();
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok(Json.toJson(attendances));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceEmpDetails() {

		List<Attendance> attendances = null;
		try {

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();
		
			SecurityAuthentication securityAuthentication = new SecurityAuthentication();
			int a = Integer.parseInt(securityAuthentication.getEmployeeId(ctx()));
			Query query = entityManager.createQuery("Select a from Attendance a where a.employeeId = :employeeId");
			query.setParameter("employeeId", Integer.parseInt(securityAuthentication.getEmployeeId(ctx())));
			attendances = query.getResultList();
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok(Json.toJson(attendances));
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceEditSave() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonAttendance = request().body().asJson().toString();
		/*
		 * JsonNode json = objectMapper.readValue(jsonAttendance, JsonNode.class);
		 * JsonNode attendanceArr = json.get("attendances");
		 */

		try {
			List<Attendance> asList = objectMapper.readValue(jsonAttendance, new TypeReference<List<Attendance>>() {
			});

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			entityManager.getTransaction().begin();

			for (Attendance attendance : asList) {

				int arrivalTotalHours = attendance.getArrivalHours();
				int departureTotalHours = attendance.getDepartureHours();
				int arrivalTotalMinutes = attendance.getArrivalMinutes();
				int departureTotalMinutes = attendance.getDepartureMinutes();

				if (attendance.getArrivalAmPm().equals("PM"))
					arrivalTotalHours = arrivalTotalHours + 12;
				if (attendance.getDepartureAmPm().equals("PM"))
					departureTotalHours = departureTotalHours + 12;

				int totalDurationHours = departureTotalHours - arrivalTotalHours;

				int totalDurationMinutes = 0;

				if (arrivalTotalMinutes < departureTotalMinutes) {
					totalDurationMinutes = arrivalTotalMinutes + 60 - departureTotalMinutes;
					totalDurationHours = totalDurationHours - 1;
				} else {
					totalDurationMinutes = arrivalTotalMinutes - departureTotalMinutes;
				}

				if (totalDurationHours < 0) {
					totalDurationHours = 24 - arrivalTotalHours;
				}
				attendance.setDurationHours(totalDurationHours);
				attendance.setDurationMinutes(totalDurationMinutes);

				entityManager.merge(attendance);
			}

			entityManager.getTransaction().commit();
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			e.printStackTrace();
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok("ok");
	}

	@Security.Authenticated(SecurityAuthentication.class)
	public Result attendanceApproved() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonAttendance = request().body().asJson().toString();

		try {

			List<Attendance> asList = objectMapper.readValue(jsonAttendance, new TypeReference<List<Attendance>>() {
			});

			EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
			EntityManager entityManager = entityManagerDatabase.getEntityManager();

			entityManager.getTransaction().begin();

			for (Attendance attendance : asList) {

				attendance.setApproved("Approved");
				entityManager.merge(attendance);
			}

			entityManager.getTransaction().commit();
			entityManagerDatabase.closeEntityManager(entityManager);
		} catch (Exception e) {
			e.printStackTrace();
			return ok("Error occured try again" + e.getMessage());
			// e.printStackTrace();
		}
		return ok("ok");
	}
}