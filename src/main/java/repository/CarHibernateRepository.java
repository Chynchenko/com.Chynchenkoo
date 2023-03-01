package repository;
import config.HibernateUtil;
import model.Car;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import java.util.Optional;

public class CarHibernateRepository implements Repository<Car>{
    private static final Logger LOGGER = LoggerFactory.getLogger(CarHibernateRepository.class);
    @Override
    public void save(Car car) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        LOGGER.info("Saving {} with id {}",car.getCarType(),car.getId());
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    @Override
    public Car[] getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        LOGGER.info("Getting all cars from repository: ");
        return entityManager.createQuery("from Car", Car.class)
                .getResultList().toArray(new Car[0]);
    }
    @Override
    public Optional<Car> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        LOGGER.info("Getting car with id {}" ,id);
        return Optional.ofNullable(entityManager.find(Car.class, id));
    }

    @Override
    public void delete(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Car where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
        LOGGER.info("Deleting car with id {}", id);
    }
}