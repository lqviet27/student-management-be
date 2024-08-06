package vn.bt.spring.qlsv_be.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.StudentDAOImpl;
import vn.bt.spring.qlsv_be.dao.StudentDetailDAOImpl;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService{
    private StudentDAOImpl studentDAO;
    private StudentDetailDAOImpl studentDetailDAO;

    @Autowired
    public StudentServiceImpl(StudentDAOImpl studentDAO, StudentDetailDAOImpl studentDetailDAO) {
        this.studentDAO = studentDAO;
        this.studentDetailDAO = studentDetailDAO;
    }
    @Override
    public List<Student> getAllStudent() {
        return studentDAO.getAllStudent();
    }

    @Override
    public Student getStudentById(int id) {
        return studentDAO.findStudentById(id);
    }

    @Override
    @Transactional
    public void addStudent(Student student) {
        studentDAO.save(student);
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        studentDAO.deleteStudentById(id);
    }
    //--------------------------------------------------------------------------------
    @Override
    public List<StudentDetail> getAllStudentDetail() {
        return studentDetailDAO.getAllStudentDetail();
    }

    @Override
    public StudentDetail getStudentDetailById(int id) {
        return studentDetailDAO.findStudentDetailById(id);
    }

    @Override
    @Transactional
    public void addStudentDetail(StudentDetail studentDetail) {
        studentDetailDAO.save(studentDetail);
    }

    @Override
    @Transactional
    public void updateStudentDetail(StudentDetail studentDetail) {
        studentDetailDAO.update(studentDetail);
    }

    @Override
    @Transactional
    public void deleteStudentDetailById(int id) {
        studentDetailDAO.deleteStudentDetailById(id);
    }
}
