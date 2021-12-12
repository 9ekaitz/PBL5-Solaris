package eus.solaris.solaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.repository.RoleRepository;

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
}
