package employeeservices;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerDatabase {

	public EntityManager getEntityManager() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public void closeEntityManager(EntityManager entityManager) {
		entityManager.close();
	}
}
