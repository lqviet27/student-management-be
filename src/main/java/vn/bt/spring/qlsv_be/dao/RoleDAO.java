package vn.bt.spring.qlsv_be.dao;

import vn.bt.spring.qlsv_be.entity.Role;

import java.util.List;

public interface RoleDAO {
    public void addRole(Role role);
    public void updateRole(Role role);
    public void deleteRole(int id);
    public Role getRole(int id);
    public List<Role> getAllRole();
    public Role getRoleByName(String name);
}
