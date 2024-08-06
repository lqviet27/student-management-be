package vn.bt.spring.qlsv_be.dao;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.bt.spring.qlsv_be.entity.Student;

import java.util.List;


@Repository
public class StudentDAOImpl implements StudentDAO{
    private EntityManager entityManager;
    @Autowired
    public StudentDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findStudentById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        Student student = findStudentById(id);
        entityManager.remove(student);
    }

    @Override
    public List<Student> getAllStudent() {
        String spql = "select s from Student s";
        return entityManager.createQuery("from Student", Student.class).getResultList();
    }
}
