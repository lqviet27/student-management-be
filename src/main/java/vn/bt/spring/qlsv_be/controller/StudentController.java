package vn.bt.spring.qlsv_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;
import vn.bt.spring.qlsv_be.service.StudentService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @GetMapping("/students")
    public List<Student> getAllStudent(){
        return studentService.getAllStudent();
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable  int id){
        Student student = studentService.getStudentById(id);
        if(student != null){
            return ResponseEntity.ok(student);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/student")
    public ResponseEntity<Student> addStudent(@ModelAttribute  Student student, @RequestParam("imageFile") MultipartFile imageFile) throws IOException, IOException {
        student.setId(0);
        studentService.addStudent(student, imageFile);
        return ResponseEntity.ok(student);
    }


    @PutMapping("/student/{id}")
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
