package eus.solaris.solaris.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.repository.ProvinceRepository;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        List<Province> provinces = provinceRepository.findAll();
        provinces.sort(Comparator.comparing(Province::getI18n));
        return provinces;
    }

    @Override
    public Province findById(Long id) {
        return provinceRepository.findById(id).orElse(null);
    }

}
