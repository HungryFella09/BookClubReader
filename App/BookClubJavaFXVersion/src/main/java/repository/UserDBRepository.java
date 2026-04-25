package repository;

import Exceptions.RepositoryException;
import domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

import java.util.List;

public class UserDBRepository implements UserRepositoryInterface {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();


    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User save(User entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
        return entity;
    }

    @Override
    public User delete(Long aLong) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users =
                    em.createQuery("SELECT u from User u where username= :usr", User.class)
                    .setParameter("usr", username)
                    .getResultList();
            if (users.isEmpty()) {
                throw new RepositoryException("User not found");
            }
            return users.get(0);
        } finally { em.close(); }
    }
}
