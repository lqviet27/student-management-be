package vn.bt.spring.qlsv_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.UserDAOImpl;
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
}
