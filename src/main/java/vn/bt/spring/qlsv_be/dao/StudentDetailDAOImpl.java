package vn.bt.spring.qlsv_be.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.bt.spring.qlsv_be.entity.StudentDetail;

import java.util.List;


@Repository
public class StudentDetailDAOImpl implements StudentDetailDAO{
    private EntityManager entityManager;
    @Autowired
    public StudentDetailDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(StudentDetail studentDetail) {
        entityManager.persist(studentDetail);
    }

    @Override
    public StudentDetail findStudentDetailById(int id) {
        return entityManager.find(StudentDetail.class, id);
    }

    @Override
    @Transactional
    public void update(StudentDetail studentDetail) {
        entityManager.merge(studentDetail);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteStudentDetailById(int id) {
        StudentDetail studentDetail = findStudentDetailById(id);
        entityManager.remove(studentDetail);
    }

    @Override
    public List<StudentDetail> getAllStudentDetail() {
        String spql = "select s from StudentDetail s";
        return entityManager.createQuery("from StudentDetail", StudentDetail.class).getResultList();
    }
}
