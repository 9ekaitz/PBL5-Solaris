package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.InstallationRepository;

@Service
public class InstallationServiceImpl implements InstallationService{

  @Autowired
  InstallationRepository installationRepository;

  @Override
  public List<Installation> findByInstallerAndCompleted(User user, Boolean completed) {
    return installationRepository.findByInstallerAndCompleted(user, completed);
  }
  
}
