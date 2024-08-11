package vn.bt.spring.qlsv_be.dao;

import vn.bt.spring.qlsv_be.entity.User;

import java.util.List;

public interface UserDAO {
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public User getUser(int id);
    public List<User> getAllUser();
    public List<User> getAllUserWithPagingnate(int page, int limit);
    public long getTotalUserCount();
    public User findUserByUsername(String username);
}
