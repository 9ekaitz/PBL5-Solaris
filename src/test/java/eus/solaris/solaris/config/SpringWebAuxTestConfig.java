package eus.solaris.solaris.config;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import eus.solaris.solaris.domain.Privilege;
import eus.solaris.solaris.domain.Role;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.security.CustomUserDetails;

@TestConfiguration
public class SpringWebAuxTestConfig {

    Role ROLE_ADMIN = new Role(1L, "ROLE_ADMIN", true, null, Stream
            .of(new Privilege(1L, "AUTH_INSTALL_READ", "auth.install.view", true, null, 1))
            .collect(Collectors.toSet()), 1);

    Role ROLE_TECHNICIAN = new Role(1L, "ROLE_TECHNICIAN", true, null, Stream
            .of(new Privilege(1L, "AUTH_INSTALL_READ", "auth.install.view", true, null, 1), new Privilege(2L, "AUTH_INSTALL_WRITE", "auth.install.write", true, null, 1))
            .collect(Collectors.toSet()), 1);

    Role ROLE_USER = new Role(1L, "ROLE_USER", true, null, Collections.emptySet(), 1);

    User basicUser = new User(1L, "testyUser", "testy@foo", "foo123", "Testy", "Tester", "User", true, ROLE_USER, null,
            1);

    User technicianUser = new User(2L, "testyTechnician", "testy@foo", "foo123", "Testy", "Tester", "Technician", true,
            ROLE_TECHNICIAN, null, 1);

    User supervisorUser = new User(3L, "testySupervisor", "testy@foo", "foo123", "Testy", "Tester", "Supervisor", true,
            null, null, 1);

    User adminUser = new User(4L, "testyAdmin", "testy@foo", "foo123", "Testy", "Tester", "Admin", true, ROLE_ADMIN,
            null, 1);

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) {
                CustomUserDetails userDetails;
                switch (username) {
                    case "testyAdmin":
                        userDetails = new CustomUserDetails(adminUser);
                        break;
                    case "testySupervisor":
                        userDetails = new CustomUserDetails(supervisorUser);
                        break;
                    case "testyTechnician":
                        userDetails = new CustomUserDetails(technicianUser);
                        break;
                    case "testyUser":
                        userDetails = new CustomUserDetails(basicUser);
                        break;
                    default:
                        userDetails = new CustomUserDetails(basicUser);
                        break;
                }
                return userDetails;
            }
        };
        return userDetailsService;
    }
}