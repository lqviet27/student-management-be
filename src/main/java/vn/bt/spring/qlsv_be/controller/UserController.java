package vn.bt.spring.qlsv_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.service.UserService;

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
    public ResponseEntity<ApiResponse<?>> logout(@PathVariable String username) {
        try{
            ApiResponse<?> response =  userService.logout(username);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Logout failed, " + e.getMessage(), null));
        }
    }
}
