package vn.bt.spring.qlsv_be.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.qlsv_be.dao.StudentDAOImpl;
import vn.bt.spring.qlsv_be.dao.StudentDetailDAOImpl;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> getAllStudentWithPagingnate(int page, int limit) {
        List<Student> students = studentDAO.getAllStudentWithPagingnate(page, limit);
        long totalRows = studentDAO.getTotalStudentCount();
        long totalPages = (long) Math.ceil((double) totalRows / limit);
        Map<String, Object> data = new HashMap<>();
        data.put("totalRows", totalRows);
        data.put("totalPages", totalPages);
        data.put("students", students);
        return data;
    }

    @Override
    public Student getStudentById(int id) {
        return studentDAO.findStudentById(id);
    }

    @Override
    @Transactional
    public void addStudent(Student student, MultipartFile file) throws DataIntegrityViolationException,IOException {
        try{
            if(file == null){
                student.getStudentDetail().setAvatar("");
            }else{
                student.getStudentDetail().setAvatar(Base64.getEncoder().encodeToString(file.getBytes()));
            }
            studentDAO.save(student);
        }catch (DataIntegrityViolationException | IOException e){
            throw e;
        }
    }

    @Override
    @Transactional
    public Student updateStudent(Student student, MultipartFile file) throws DataIntegrityViolationException,IOException {
//        studentDAO.update(student);
        try {
            // Lấy student hiện tại từ database
            Student existingStudent = studentDAO.findStudentById(student.getId());

            if (existingStudent != null) {
                // Cập nhật các trường thông tin sinh viên
                int idDetailExits = existingStudent.getStudentDetail().getId();
                existingStudent.setHoDem(student.getHoDem());
                existingStudent.setTen(student.getTen());
                existingStudent.setEmail(student.getEmail());
                // Các trường khác cần cập nhật...

                // Xử lý avatar
                if (file != null && !file.isEmpty()) {
                    existingStudent.getStudentDetail().setAvatar(Base64.getEncoder().encodeToString(file.getBytes()));
                } else {
                    // Giữ nguyên avatar cũ nếu không có file mới được tải lên
                    existingStudent.getStudentDetail().setAvatar(existingStudent.getStudentDetail().getAvatar());
                }

                // Cập nhật lại thông tin trong database
                studentDAO.update(existingStudent);
                deleteStudentDetailById(idDetailExits);
                return existingStudent;
            } else {
                throw new EntityNotFoundException("Student not found with ID: " + student.getId());
            }
        } catch (DataIntegrityViolationException | IOException e) {
            throw e;
        }
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
