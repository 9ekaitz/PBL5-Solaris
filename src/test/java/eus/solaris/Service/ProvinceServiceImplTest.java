package eus.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.repository.ProvinceRepository;
import eus.solaris.solaris.service.impl.ProvinceServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProvinceServiceImplTest {

    @InjectMocks
    private ProvinceServiceImpl provinceServiceImpl;

    @Mock
    private ProvinceRepository provinceRepository;

    @Test
    void findAllTest() {
        List<Province> provinces = new ArrayList<>();
        provinces.add(new Province(1L, "PROVINCE_ALACA", "province.alava", 1));
        provinces.add(new Province(2L, "PROVINCE_BIZKAIA", "province.bizkaia", 2));

        when(provinceRepository.findAll()).thenReturn(provinces);
        assertEquals(provinces, provinceServiceImpl.findAll());
    }

    @Test
    void findByIdTest() {
        Province province = new Province(1L, "PROVINCE_ALACA", "province.alava", 1);

        when(provinceRepository.findById(1L)).thenReturn(java.util.Optional.of(province));
        assertEquals(province, provinceServiceImpl.findById(1L));
    }
    
}
