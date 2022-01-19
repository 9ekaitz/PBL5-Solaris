package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Province;

public interface ProvinceService {
    public List<Province> findAll();
    public Province findById(Long id);
}
