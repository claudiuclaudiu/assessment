package ro.gshmedia.data.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ro.gshmedia.data.dao.OrderDao;
import ro.gshmedia.data.model.Order;
import ro.gshmedia.data.model.Status;

public class OrderDaoImpl implements OrderDao {


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ro.gshmedia.persistance");
    private EntityManager entityManager = getEntityManager();

    @Override
    public Order saveOrder(Order order) {
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        return order;
    }


    @Override
    public Order getOrder(int id) {
        Order order = entityManager.find(Order.class, id);
        entityManager.detach(order);
        return order;
    }

    @Override
    public Order updateOrderStatusById(int id, Status status) {
        Order order = getOrder(id);
        order.setStatus(status);
        return saveOrder(order);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
