package vn.bt.spring.qlsv_be.service;

import vn.bt.spring.qlsv_be.request.SignInRequest;
import vn.bt.spring.qlsv_be.request.SignUpRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.response.JwtAuthenticationResponse;

import java.util.Map;

public interface AuthenticationService {
    Map<String,String> signup(SignUpRequest request) throws Exception;
    Map<String,String> signin(SignInRequest request) throws Exception;
}
