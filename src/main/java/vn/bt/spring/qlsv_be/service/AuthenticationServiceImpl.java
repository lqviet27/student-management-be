package vn.bt.spring.qlsv_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bt.spring.qlsv_be.dao.RoleDAOImpl;
import vn.bt.spring.qlsv_be.dao.UserDAOImpl;
import vn.bt.spring.qlsv_be.entity.User;
import vn.bt.spring.qlsv_be.request.SignInRequest;
import vn.bt.spring.qlsv_be.request.SignUpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserDAOImpl userDAO;
    private final RoleDAOImpl roleDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Constructor for dependency injection
    @Autowired
    public AuthenticationServiceImpl(UserDAOImpl userDAO, RoleDAOImpl roleDAO,PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public Map<String,String> signup(SignUpRequest request) throws IllegalArgumentException {
        User user = new User();
        user.setUseName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleDAO.getRoleByName("ROLE_USER"));
        user.setActive(false);
        try{
            userDAO.addUser(user);
            String jwt = jwtService.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user_name", user.getUseName());
            response.put("role", user.getRole().getName());
            return response;
        }catch (Exception e){
            throw new IllegalArgumentException("Username already exists.");
        }

    }

    @Override
    public Map<String,String> signin(SignInRequest request) throws IllegalArgumentException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );

            User user = userDAO.findUserByUsername(request.getUserName());
            user.setActive(true);
            userDAO.updateUser(user);
            String jwt = jwtService.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user_name", user.getUseName());
            response.put("role", user.getRole().getName());
            return response;
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid user name or password.");
        }
    }
}
