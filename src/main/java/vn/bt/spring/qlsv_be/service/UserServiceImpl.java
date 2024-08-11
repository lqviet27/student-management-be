package vn.bt.spring.qlsv_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.UserDAOImpl;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.response.ApiResponse;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAOImpl userDAO;
    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
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
    public ApiResponse<?> logout(String username) {
        User user = userDAO.findUserByUsername(username);
        user.setActive(false);
        userDAO.updateUser(user);
        return new ApiResponse<>(0, "Logout successfully", null);
    }

}
