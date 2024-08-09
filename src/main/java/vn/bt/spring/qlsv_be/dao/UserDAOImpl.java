package vn.bt.spring.qlsv_be.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.bt.spring.qlsv_be.entity.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;
    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUser() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findUserByUsername(String username) {
        try {
            User user = entityManager.createQuery("from User where useName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Invalid username or password.", e);
        }
    }
}
