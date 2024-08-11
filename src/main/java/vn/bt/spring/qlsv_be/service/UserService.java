package vn.bt.spring.qlsv_be.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDetailsService userDetailsService();
    ApiResponse<?> logout(String username);
    public List<User> getAllUser();
    public Map<String,Object> getAllUserWithPagingnate(int page, int limit);
    public User getUserById(int id);
    public User getUserByUsername(String username);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public ApiResponse<?> changePassword(String username, ChangePassRequest changePassRequest);
}
