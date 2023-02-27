package repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import config.HibernateUtil;
import model.Order;
import javax.persistence.EntityManager;
import java.util.Optional;

public class OrderHibernateRepository implements Repository<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHibernateRepository.class);
    @Override
    public void save(Order order) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        LOGGER.info("Saving order..." + order.getOrderId());
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public Order[] getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        LOGGER.info("Getting all orders from repository: ");
        return entityManager.createQuery("from Order", Order.class)
                .getResultList().toArray(new Order[0]);
    }

    @Override
    public Optional<Order> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        LOGGER.info("Getting order with id: " + id);
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public void delete(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Order where id = :orderId")
                .setParameter("orderId", id)
                .executeUpdate();
        LOGGER.info("Deleting order with id: " + id);
        entityManager.getTransaction().commit();

    }

}