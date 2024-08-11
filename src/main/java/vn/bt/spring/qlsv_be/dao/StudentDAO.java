package vn.bt.spring.qlsv_be.dao;

import vn.bt.spring.qlsv_be.entity.Student;

import java.util.List;

public interface StudentDAO {
    public void save(Student student);
    public Student findStudentById(int id);
    public void update(Student student);
    public void deleteStudentById(int id);
    public List<Student> getAllStudent();
    public List<Student> getAllStudentWithPagingnate(int page, int limit);
    public long getTotalStudentCount();
}

