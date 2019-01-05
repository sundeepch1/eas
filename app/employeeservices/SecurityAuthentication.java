package employeeservices;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Employee;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecurityAuthentication extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {

		// Do Authentication here
		// Returning NULL means request in not authorized
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		// Response for unauthenticated request
		return ok("unauthoriosed");
	}

	public String getEmployeeEmail(Context ctx) {
		return ctx.session().get("email");
	}
	
	public String getEmployeeId(Context ctx) {
		return ctx.session().get("employeeId");
	}


	public boolean isValidEmployee(String emailString, String password) {
		
		boolean flag = false;
		EntityManagerDatabase entityManagerDatabase = new EntityManagerDatabase();
		EntityManager entityManager = entityManagerDatabase.getEntityManager();

		Query query = entityManager.createQuery("Select e from Employee e where e.emailAddress = :emailString");
		query.setParameter("emailString", emailString);
		Employee employee = (Employee) query.getSingleResult();

		entityManagerDatabase.closeEntityManager(entityManager);
		Encryption encryption = new Encryption();
		
		if (employee.getPassword().isEmpty()) {
			return false;
		} else {
			String encryptPassword=encryption.getEncryptedPassword(password, password);
			if (encryptPassword.equals(employee.getPassword())) {
				return true;
			}
		}
		return flag;
	}
}