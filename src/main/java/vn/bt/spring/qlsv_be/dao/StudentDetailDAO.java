package vn.bt.spring.qlsv_be.dao;

import vn.bt.spring.qlsv_be.entity.StudentDetail;

import java.util.List;

public interface StudentDetailDAO {
    public void save (StudentDetail studentDetail);
    public StudentDetail findStudentDetailById(int id);
    public void update(StudentDetail studentDetail);
    public void deleteStudentDetailById(int id);
    public List<StudentDetail> getAllStudentDetail();
}
