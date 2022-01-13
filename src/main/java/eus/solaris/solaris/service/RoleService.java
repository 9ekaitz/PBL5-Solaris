package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.Role;

public interface RoleService {
    public Role save(Role role);
    public Role findByName(String name);
    public Object findAll();
    public Role findById(Long roleId);
}
