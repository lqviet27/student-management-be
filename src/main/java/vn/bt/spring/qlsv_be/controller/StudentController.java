package vn.bt.spring.qlsv_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.StudentDetail;
import vn.bt.spring.qlsv_be.model.dto.StudentDTO;
import vn.bt.spring.qlsv_be.model.mapper.StudentMapper;
import vn.bt.spring.qlsv_be.service.StudentService;

import java.util.List;

import static vn.bt.spring.qlsv_be.model.mapper.StudentMapper.convertToDTO;

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
//    @GetMapping("/student/{id}")
//    public ResponseEntity<Student> getStudentById(@PathVariable  int id){
//        Student student = studentService.getStudentById(id);
//        if(student != null){
//            return ResponseEntity.ok(student);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            StudentDTO studentDTO = convertToDTO(student);
            return  ResponseEntity.ok(studentDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/student")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = StudentMapper.convertToEntity(studentDTO);
        studentService.addStudent(student);
//        StudentDTO savedStudentDTO = StudentMapper.convertToDTO(savedStudent);
        return  ResponseEntity.ok(studentDTO);
    }








//    @PostMapping("/student")
//    public ResponseEntity<Student> addStudent(@RequestBody Student student){
//        student.setId(0);
//        studentService.addStudent(student);
//        return ResponseEntity.ok(student);
//    }
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
