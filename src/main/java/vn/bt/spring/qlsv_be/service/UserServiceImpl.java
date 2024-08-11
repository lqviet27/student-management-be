package vn.bt.spring.qlsv_be.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.UserDAOImpl;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.ChangePassRequest;
import vn.bt.spring.qlsv_be.response.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAOImpl userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
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
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    @Override
    public Map<String, Object> getAllUserWithPagingnate(int page, int limit) {
        List<User> users = userDAO.getAllUserWithPagingnate(page, limit);
        long totalRows = userDAO.getTotalUserCount();
        long totalPages = (long) Math.ceil((double) totalRows / limit);
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        data.put("totalRows", totalRows);
        data.put("totalPages", totalPages);
        return data;
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUser(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        try{
            userDAO.addUser(user);
        }catch (DataIntegrityViolationException e){
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public ApiResponse<?> changePassword(String username, ChangePassRequest changePassRequest) {
        User user = userDAO.findUserByUsername(username);
        if (passwordEncoder.matches(changePassRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassRequest.getNewPassword()));
            userDAO.updateUser(user);
            return new ApiResponse<>(0, "Change password successfully", null);
        }else{
            return new ApiResponse<>(1, "Old password is incorrect", null);
        }
    }

}
