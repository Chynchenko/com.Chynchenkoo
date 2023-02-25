package repository;

import config.HibernateUtil;
import model.Order;

import javax.persistence.EntityManager;
import java.util.Optional;

public class OrderHibernateRepository implements Repository<Order> {
    @Override
    public void save(Order order) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public Order[] getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("from Order", Order.class)
                .getResultList().toArray(new Order[0]);
    }

    @Override
    public Optional<Order> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public void delete(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Order where id = :orderId")
                .setParameter("orderId", id)
                .executeUpdate();
        entityManager.getTransaction().commit();

    }

}