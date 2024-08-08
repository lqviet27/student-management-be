package vn.bt.spring.qlsv_be.service;

import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    public List<Student> getAllStudent();
    public Student getStudentById(int id);
    public void addStudent(Student student, MultipartFile file) throws Exception;
    public Student updateStudent(Student student, MultipartFile file) throws Exception;
    public void deleteStudentById(int id);

    public List<StudentDetail> getAllStudentDetail();
    public StudentDetail getStudentDetailById(int id);
    public void addStudentDetail(StudentDetail studentDetail);
    public void updateStudentDetail(StudentDetail studentDetail);
    public void deleteStudentDetailById(int id);

}
