package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.repository.filters.BrandRepository;
import eus.solaris.solaris.repository.filters.ColorRepository;
import eus.solaris.solaris.repository.filters.MaterialRepository;
import eus.solaris.solaris.repository.filters.SizeRepository;
import eus.solaris.solaris.repository.specifications.ProductSpecifications;
import eus.solaris.solaris.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Test
    void saveTest(){
        Product product = new Product(1L, 200D, null, null, null, 1);
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, productServiceImpl.save(product));
    }

    @Test
    void findByIdTest(){
        Product product = new Product(1L, 200D, null, null, null, 1);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        assertEquals(product, productServiceImpl.findById(1L));
    }

    @Test
    void findAllTest(){
        List<Product> products = Stream
            .of(new Product(1L, 200D, null, null, null, 1), new Product(2L, 200D, null, null, null, 1))
            .collect(Collectors.toList());
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(products, productServiceImpl.findAll());
    }

    @Test
    void getPagesFromProductListTest(){
        ReflectionTestUtils.setField(productServiceImpl, "pagesize", 20);

        List<Product> products = Stream
            .of(new Product(1L, 200D, null, null, null, 1),
                new Product(2L, 200D, null, null, null, 1)).collect(Collectors.toList());

        assertEquals(products, productServiceImpl.getPagesFromProductList(products).getPageList());
    }

    @Test
    void getBrandsTest(){
        List<Brand> brands = Stream
            .of(new Brand(1L, "brand1"), new Brand(2L, "brand2")).collect(Collectors.toList());

        when(brandRepository.findAll()).thenReturn(brands);
        assertEquals(brands, productServiceImpl.getBrands());
    }

    @Test
    void getColorsTest(){
        List<Color> colors = Stream
            .of(new Color(1L, "color1"), new Color(2L, "color2")).collect(Collectors.toList());
        when(colorRepository.findAll()).thenReturn(colors);

        assertEquals(colors, productServiceImpl.getColors());
    }

    @Test
    void getSizesTest(){
        List<Size> sizes = Stream
            .of(new Size(1L, 10, 10), new Size(2L, 10, 10)).collect(Collectors.toList());
        when(sizeRepository.findAll()).thenReturn(sizes);

        assertEquals(sizes, productServiceImpl.getSizes());
    }

    @Test
    void getMaterialsTest(){
        List<Material> materials = Stream
            .of(new Material(1L, "material1"), new Material(2L, "material2")).collect(Collectors.toList());
        when(materialRepository.findAll()).thenReturn(materials);

        assertEquals(materials, productServiceImpl.getMaterials());
    }

    @Test
    void getFilteredProductsTest(){
        ReflectionTestUtils.setField(productServiceImpl, "pagesize", 20);

        ProductFilterForm productFilterForm = new ProductFilterForm();
        List<Long> ids = Stream.of(1L).collect(Collectors.toList());
        productFilterForm.setBrandsIds(ids);
        productFilterForm.setColorsIds(ids);
        productFilterForm.setMaterialsIds(ids);
        productFilterForm.setSizesIds(ids);

        assertEquals(null, productServiceImpl.getFilteredProducts(productFilterForm, 1));
    }

    @Test
    void getFilteredProductsTest2(){
        ReflectionTestUtils.setField(productServiceImpl, "pagesize", 20);

        ProductFilterForm productFilterForm = new ProductFilterForm();

        assertEquals(null, productServiceImpl.getFilteredProducts(productFilterForm, 1));
    }
    
}
