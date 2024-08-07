package vn.bt.spring.qlsv_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.service.StudentService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudent(){
        ApiResponse<List<Student>> response = new ApiResponse<>(0, "Get List Students Success", studentService.getAllStudent());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable  int id){
        Student student = studentService.getStudentById(id);
        if(student != null){
            ApiResponse<Student> response = new ApiResponse<>(0, "Get Student have id: "+ id +" Success", student);
            return ResponseEntity.ok(response);
        }else{
            ApiResponse<Student> response = new ApiResponse<>(1, "No data", null);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Student>> addStudent(@ModelAttribute  Student student, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile)  {
        student.setId(0);
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                studentService.addStudent(student, imageFile);
            } else {
                // Xử lý khi không có imageFile (có thể là không làm gì, hoặc lưu mà không có ảnh)
                studentService.addStudent(student, null);
            }
            ApiResponse<Student> response = new ApiResponse<>(0, "Create Student Success", student);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<Student> response = new ApiResponse<>(1, "Email already exists", null);
            return ResponseEntity.ok(response); // Sử dụng mã trạng thái HTTP 409 (Conflict)
        } catch (Exception e) {
            ApiResponse<Student> response = new ApiResponse<>(1, "Error occurred: " + e.getMessage(), null);
            return ResponseEntity.ok(response); // Sử dụng mã trạng thái HTTP 500 (Internal Server Error)
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student){
        Student exits = studentService.getStudentById(id);
        if(exits != null){
            int idDetailExits = exits.getStudentDetail().getId();
            exits.setHoDem(student.getHoDem());
            exits.setTen(student.getTen());
            exits.setEmail(student.getEmail());
            exits.setStudentDetail(student.getStudentDetail());
            studentService.updateStudent(exits);
            studentService.deleteStudentDetailById(idDetailExits);
            return ResponseEntity.ok(exits);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
//    @DeleteMapping("/student_detail/{id}")
//    public ResponseEntity<StudentDetail> deleteStudentById(@PathVariable int id){
//        StudentDetail exits = studentService.getStudentDetailById(id);
//        if(exits != null){
//            studentService.deleteStudentById(id);
//            return ResponseEntity.ok(exits);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
//    }
}
