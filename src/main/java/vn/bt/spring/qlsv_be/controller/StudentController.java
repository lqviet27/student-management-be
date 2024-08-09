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
import java.util.Map;


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

    @GetMapping("")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllStudentWithPaginate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit) {

        Map<String, Object> students = studentService.getAllStudentWithPagingnate(page, limit);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>(0, "Get List Students Success", students);
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
    public ResponseEntity<ApiResponse<Student>> addStudent(@ModelAttribute  Student student, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile)  {//requestbody Student khong dung dc
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
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable int id, @ModelAttribute  Student student, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile){//requestbody Student khong dung dc
            try{
                Student updatedStudent = studentService.updateStudent(student,imageFile);
                ApiResponse<Student> response = new ApiResponse<>(0, "Update Student Success", updatedStudent);
                return ResponseEntity.ok(response);
            } catch (DataIntegrityViolationException e) {
                ApiResponse<Student> response = new ApiResponse<>(1, "Email already exists", null);
                return ResponseEntity.ok(response); // Sử dụng mã trạng thái HTTP 409 (Conflict)
            } catch (Exception e) {
                ApiResponse<Student> response = new ApiResponse<>(1, "Error occurred: " + e.getMessage(), null);
                return ResponseEntity.ok(response); // Sử dụng mã trạng thái HTTP 500 (Internal Server Error)
            }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> deleteStudentById(@PathVariable int id){
        StudentDetail exits = studentService.getStudentDetailById(id);
        if(exits != null){
            studentService.deleteStudentById(id);
            ApiResponse<Student> response = new ApiResponse<>(0, "Delete Student Success", null);
            return ResponseEntity.ok(response);
        }else{
            ApiResponse<Student> response = new ApiResponse<>(1, "Student Not Found", null);
            return ResponseEntity.ok(response);
        }
    }
}
