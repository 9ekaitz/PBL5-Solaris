package eus.solaris.solaris.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import eus.solaris.solaris.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${solaris.pagination.products.pagesize}")
    private Integer pagesize;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public PagedListHolder<Product> getPagesFromProductList(List<Product> products) {
        PagedListHolder<Product> productsPageList = new PagedListHolder<>(products);
        productsPageList.setPageSize(pagesize);
        productsPageList.setPage(0);
        return productsPageList;
    }

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<Color> getColors() {
        return colorRepository.findAll();
    }

    @Override
    public List<Size> getSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public List<Material> getMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Page<Product> getFilteredProducts(ProductFilterForm pff, Integer page) {
        List<Specification<Product>> specifications = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, pagesize);

        if (pff.getBrandsIds() != null)
            specifications.add(ProductSpecifications.findByBrandIds(pff.getBrandsIds()));

        if (pff.getColorsIds() != null)
            specifications.add(ProductSpecifications.findByColorIds(pff.getColorsIds()));

        if (pff.getMaterialsIds() != null)
            specifications.add(ProductSpecifications.findByMaterialIds(pff.getMaterialsIds()));

        if (pff.getSizesIds() != null)
            specifications.add(ProductSpecifications.findBySizeIds(pff.getSizesIds()));

        if (specifications.size() > 0) {

            Specification<Product> query = Specification.where(null);
            for (Specification<Product> spec : specifications)
                query = Specification.where(query).and(spec);

            return productRepository.findAll(query, pageable);
        }

        return productRepository.findAll(pageable);
    }
}
