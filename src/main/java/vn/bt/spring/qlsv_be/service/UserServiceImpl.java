package vn.bt.spring.qlsv_be.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.RoleDAOImpl;
import vn.bt.spring.qlsv_be.dao.UserDAOImpl;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.request.UserRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;
import vn.bt.spring.qlsv_be.response.UserResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAOImpl userDAO;
    private final RoleDAOImpl roleDAO;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO, RoleDAOImpl roleDAO, @Lazy  PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return userDAO.findUserByUsername(username);
            }
        };
    }

    @Override
    @Transactional
    public ApiResponse<?> logout(String username) {
        User user = userDAO.findUserByUsername(username);
        user.setActive(false);
        userDAO.updateUser(user);
        return new ApiResponse<>(0, "Logout successfully", null);
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userDAO.getAllUser();
        List<UserResponse> userResponses = users.stream().map(user ->{
            return new UserResponse(user.getId(), user.getUseName(),user.getPassword(), user.isActive(), user.getRole().getName());
        }).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public Map<String, Object> getAllUserWithPagingnate(int page, int limit) {
        List<User> users = userDAO.getAllUserWithPagingnate(page, limit);
        List<UserResponse> userResponses = users.stream().map(user ->{
            return new UserResponse(user.getId(), user.getUseName(),user.getPassword(), user.isActive(), user.getRole().getName());
        }).collect(Collectors.toList());
        long totalRows = userDAO.getTotalUserCount();
        long totalPages = (long) Math.ceil((double) totalRows / limit);
        Map<String, Object> data = new HashMap<>();
        data.put("users", userResponses);
        data.put("totalRows", totalRows);
        data.put("totalPages", totalPages);
        return data;
    }

    @Override
    public UserResponse getUserById(int id) {
        User user = userDAO.getUser(id); // Lấy đối tượng User từ DAO
        if (user == null) {
            return null; // Xử lý trường hợp không tìm thấy user (tùy theo yêu cầu của bạn)
        }
        // Chuyển đổi User thành UserResponse
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUseName(),
                user.getPassword(),
                user.isActive(),
                user.getRole().getName()
        );
        return userResponse;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userDAO.findUserByUsername(username); // Lấy đối tượng User từ DAO
        if (user == null) {
            return null; // Xử lý trường hợp không tìm thấy user (tùy theo yêu cầu của bạn)
        }
        // Chuyển đổi User thành UserResponse
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUseName(), // Giả sử tên thuộc tính là getUseName() thay vì getUsername()
                user.getPassword(),
                user.isActive(),
                user.getRole().getName()
        );
        return userResponse;
    }

    @Override
    @Transactional
    public User addUser(UserRequest userRequest) {
        try{
            User user = new User();
            user.setUseName(userRequest.getUseName());
            user.setPassword(userRequest.getPassword());
            user.setRole(roleDAO.getRoleByName(userRequest.getRole()));
            user.setActive(false);
            userDAO.addUser(user);
            return user;
        }catch (DataIntegrityViolationException e){
            throw e;
        }
    }

    @Override
    @Transactional
    public User updateUser(UserRequest userRequest) {
        User user = userDAO.findUserByUsername(userRequest.getUseName());
        user.setRole(roleDAO.getRoleByName(userRequest.getRole()));
        userDAO.updateUser(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public ApiResponse<?> changePassword(int id, ChangePassRequest changePassRequest) {
        User user = userDAO.getUser(id);
        if(user == null){
            return new ApiResponse<>(1, "User not found", null);
        }
        if(passwordEncoder.matches(changePassRequest.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePassRequest.getNewPassword()));
            userDAO.updateUser(user);
            return new ApiResponse<>(0, "Change password successfully", null);
        }else{
            return new ApiResponse<>(1, "Old password is incorrect", null);
        }
    }


}
