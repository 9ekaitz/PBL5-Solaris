package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.repository.RoleRepository;
import eus.solaris.solaris.service.impl.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void saveTest(){
        Role role = new Role(1L, "ROLE_ADMIN", true, null, "role.admin", null, 1);

        when(roleRepository.save(role)).thenReturn(role);
        assertEquals(role, roleServiceImpl.save(role));
    }
    
    @Test
    void findByIdTest(){
        Role role = new Role(1L, "ROLE_ADMIN", true, null, "role.admin", null, 1);

        when(roleRepository.findByName(role.getName())).thenReturn(role);
        assertEquals(role, roleServiceImpl.findByName(role.getName()));
    }
}
