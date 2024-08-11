package vn.bt.spring.qlsv_be.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.request.UserRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDetailsService userDetailsService();
    ApiResponse<?> logout(String username);
    public List<UserResponse> getAllUser();
    public Map<String,Object> getAllUserWithPagingnate(int page, int limit);
    public UserResponse getUserById(int id);
    public UserResponse getUserByUsername(String username);
    public User addUser(UserRequest userRequest);
    public User updateUser(UserRequest userRequest);
    public void deleteUser(int id);
    public ApiResponse<?> changePassword(int id, ChangePassRequest changePassRequest);
}
