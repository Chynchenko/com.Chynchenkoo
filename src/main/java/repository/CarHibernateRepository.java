package repository;
import config.HibernateUtil;
import model.Car;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CarHibernateRepository implements Repository<Car>{
    @Override
    public void save(Car car) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Car[] getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("from Car", Car.class)
                .getResultList().toArray(new Car[0]);
    }

    @Override
    public Optional<Car> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
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
    }
}