package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Role;

public interface RoleService {
    public Role save(Role role);
    public Role findByName(String name);
    public List<Role> findAll();
    public Role findById(Long roleId);
}
