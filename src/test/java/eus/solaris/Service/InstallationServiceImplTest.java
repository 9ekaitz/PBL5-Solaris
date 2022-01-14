package eus.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.InstallationRepository;
import eus.solaris.solaris.service.InstallationServiceImpl;

public class InstallationServiceImplTest {

    @InjectMocks
    InstallationServiceImpl installationServiceImpl;

    @Mock
    InstallationRepository installationRepository;

    // @Test
    // void findByInstallerAndCompletedTest() {
    //     User user = new User();
    //     user.setId(1L);
    //     user.setUsername("testy");
    //     boolean completed = true;
    //     Installation[] installations = {
    //         new Installation(1L, "Install_Name 1", "Install_Desc 1", true, null, user, null, 1),
    //         new Installation(2L, "Install_Name 2", "Install_Desc 2", true, null, user, null, 1),
    //         new Installation(3L, "Install_Name 3", "Install_Desc 3", false, null, user, null, 1),
    //         new Installation(4L, "Install_Name 4", "Install_Desc 4", false, null, user, null, 1),
    //         new Installation(5L, "Install_Name 5", "Install_Desc 5", true, null, user, null, 1) };

    //     List<Installation> lst = Stream.of(installations[0], installations[1], installations[4]).collect(Collectors.toList());
        
    //     when(installationRepository.findByInstallerAndCompleted(user, completed))
    //         .thenReturn(Stream.of(installations[0], installations[1], installations[4]).collect(Collectors.toList()));

    //     assertEquals(lst, installationServiceImpl.findByInstallerAndCompleted(user, completed));
    // }
    
}
