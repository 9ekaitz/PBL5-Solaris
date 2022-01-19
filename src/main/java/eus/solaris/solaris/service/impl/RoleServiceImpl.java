package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.repository.RoleRepository;
import eus.solaris.solaris.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role save(Role role) {
        return roleRepository.save(role);
    }
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
    @Override
    public Object findAll() {
        return roleRepository.findAll();
    }
    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }
}
