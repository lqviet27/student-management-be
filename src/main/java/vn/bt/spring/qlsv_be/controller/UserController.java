package vn.bt.spring.qlsv_be.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.qlsv_be.entity.Student;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/logout/{username}")
    @Transactional
    public ResponseEntity<ApiResponse<?>> logout(@PathVariable String username) {
        try{
            ApiResponse<?> response =  userService.logout(username);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Logout failed, " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUser() {
        try{
            ApiResponse<List<User>> response = new ApiResponse<>(0, "Get all user success", userService.getAllUser());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Get all user failed, " + e.getMessage(), null));
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllStudentWithPaginate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit
    ) {
        Map<String, Object> students = userService.getAllUserWithPagingnate(page, limit);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>(0, "Get List users Success", students);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable  int id){
        User user = userService.getUserById(id);
        if(user != null){
            ApiResponse<User> response = new ApiResponse<>(0, "Get USer have id: "+ id +" Success", user);
            return ResponseEntity.ok(response);
        }else{
            ApiResponse<User> response = new ApiResponse<>(1, "No data", null);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("")
    @Transactional
    public ResponseEntity<ApiResponse<User>> addUser(@ModelAttribute  User user )  {
        user.setId(0);
        try {
            userService.addUser(user);
            ApiResponse<User> response = new ApiResponse<>(0, "Create User Success", user);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<User> response = new ApiResponse<>(1, "User Name already exists", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<User> response = new ApiResponse<>(1, "Error occurred: " + e.getMessage(), null);
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable int id, @ModelAttribute  User user){
            userService.updateUser(user);
            ApiResponse<User> response = new ApiResponse<>(0, "Update User Success", user);
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable int id){
        try{
            userService.deleteUser(id);
            ApiResponse<User> response = new ApiResponse<>(0, "Delete User Success", null);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Delete User failed, " + e.getMessage(), null));
        }
    }

    @PutMapping("/change-password")
    @Transactional
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestParam String username, @ModelAttribute ChangePassRequest changePassRequest){
            return ResponseEntity.ok(userService.changePassword(username, changePassRequest));
    }

}
