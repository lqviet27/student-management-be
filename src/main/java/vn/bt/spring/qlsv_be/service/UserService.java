package vn.bt.spring.qlsv_be.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.response.ApiResponse;

public interface UserService {
    UserDetailsService userDetailsService();
    ApiResponse<?> logout(String username);
}
