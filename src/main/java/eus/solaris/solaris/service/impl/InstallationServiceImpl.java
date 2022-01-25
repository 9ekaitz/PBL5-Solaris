package eus.solaris.solaris.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Installation;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.InstallationRepository;
import eus.solaris.solaris.service.InstallationService;

@Service
public class InstallationServiceImpl implements InstallationService{

  @Autowired
  InstallationRepository installationRepository;

  @Override
  public List<Installation> findByInstallerAndCompleted(User user, Boolean completed) {
    return installationRepository.findByInstallerAndCompletedOrderByOrderId(user, completed);
  }

  @Override
  public Installation findById(Long id) {
    return installationRepository.findById(id).orElse(null);
  }

  @Override
  public Installation save(Installation installation) {
    return installationRepository.save(installation);
  }
}
