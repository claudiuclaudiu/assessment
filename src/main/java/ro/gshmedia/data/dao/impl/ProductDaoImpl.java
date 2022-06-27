package ro.gshmedia.data.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ro.gshmedia.data.dao.ProductDao;
import ro.gshmedia.data.model.Product;


public class ProductDaoImpl implements ProductDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ro.gshmedia.persistance");
    private EntityManager entityManager = getEntityManager();

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public Product saveProduct(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        return product;
    }


    @Override
    public Product updateProduct(Product product) {

        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        em.persist(product);
        em.getTransaction().commit();
        return product;
    }


    @Override
    public Product getProduct(int id) {
        Product product = entityManager.find(Product.class, id);
        entityManager.detach(product);
        return product;

    }

    @Override
    public Product updateProductById(int quantity, int id) {
        Product product = getProduct(id);
        product.setStock(quantity);
        return saveProduct(product);
    }
}
