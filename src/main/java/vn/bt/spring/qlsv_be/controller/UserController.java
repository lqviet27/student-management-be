package vn.bt.spring.qlsv_be.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.request.UserRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.response.UserResponse;
import vn.bt.spring.qlsv_be.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/logout/{username}")
    public ResponseEntity<ApiResponse<?>> logout(@PathVariable String username) {
        try{
            ApiResponse<?> response =  userService.logout(username);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Logout failed, " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
        try{
            ApiResponse<List<UserResponse>> response = new ApiResponse<>(0, "Get all user success", userService.getAllUser());
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
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable  int id){
        UserResponse user = userService.getUserById(id);
        if(user != null){
            ApiResponse<UserResponse> response = new ApiResponse<>(0, "Get User have id: "+ id +" Success", user);
            return ResponseEntity.ok(response);
        }else{
            ApiResponse<UserResponse> response = new ApiResponse<>(1, "No data", null);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(@ModelAttribute UserRequest userRequest )  {
        try {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            User user = userService.addUser(userRequest);
            ApiResponse<UserResponse> response = new ApiResponse<>(0, "Create User Success", new UserResponse(user.getId(), user.getUseName(), user.getPassword(), user.isActive(), user.getRole().getName()));
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<UserResponse> response = new ApiResponse<>(1, "User Name already exists", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserResponse> response = new ApiResponse<>(1, "Error occurred: " + e.getMessage(), null);
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable int id, @ModelAttribute  UserRequest userRequest){
            User user = userService.updateUser(userRequest);
            ApiResponse<UserResponse> response = new ApiResponse<>(0, "Update User Success", new UserResponse(user.getId(), user.getUseName(), user.getPassword(), user.isActive(), user.getRole().getName()));
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable int id){
        try{
            userService.deleteUser(id);
            ApiResponse<User> response = new ApiResponse<>(0, "Delete User Success", null);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Delete User failed, " + e.getMessage(), null));
        }
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<ApiResponse<?>> changePassword(@PathVariable int id, @ModelAttribute ChangePassRequest changePassRequest){
        try{
            ApiResponse<?> response = userService.changePassword(id, changePassRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Change password failed, " + e.getMessage(), null));
        }
    }


}
