package vn.bt.spring.qlsv_be.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.bt.spring.qlsv_be.entity.Role;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{
    private EntityManager entityManager;
    @Autowired
    public RoleDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        entityManager.merge(role);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteRole(int id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    public Role getRole(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> getAllRole() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("from Role where name = :name", Role.class).setParameter("name", name).getSingleResult();
    }
}
