package vn.bt.spring.qlsv_be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.qlsv_be.request.SignInRequest;
import vn.bt.spring.qlsv_be.request.SignUpRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.service.AuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String,String>>> signup(@ModelAttribute SignUpRequest request) {
        try{
            Map<String,String> response = authenticationService.signup(request);
            return ResponseEntity.ok(new ApiResponse<>(0, "Sign up successfully", response));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Sign up failed, " + e.getMessage(), null));
        }


    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Map<String,String>>> signin(@ModelAttribute  SignInRequest request) {
        try {
            Map<String,String> data = authenticationService.signin(request);
            ApiResponse<Map<String,String>> response = new ApiResponse<>(0, "Sign in successfully", data);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse<>(1, "Sign in failed, " + e.getMessage(), null));
        }
    }


}