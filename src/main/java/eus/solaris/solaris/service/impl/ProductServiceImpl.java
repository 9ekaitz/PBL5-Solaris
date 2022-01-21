package eus.solaris.solaris.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import com.google.inject.internal.util.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanelModel;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.repository.filters.BrandRepository;
import eus.solaris.solaris.repository.filters.ColorRepository;
import eus.solaris.solaris.repository.filters.MaterialRepository;
import eus.solaris.solaris.repository.filters.SizeRepository;
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
    public Set<Product> getFilteredProducts(ProductFilterForm pff) {
        Set<Product> products =  new HashSet<>();
        Set<Product> allProducts = new HashSet<>(productRepository.findAll());
        if (Stream.of(pff.getBrandsIds()).allMatch(Objects::isNull) && //If no filter are selected
            Stream.of(pff.getColorsIds()).allMatch(Objects::isNull) &&
            Stream.of(pff.getMaterialsIds()).allMatch(Objects::isNull) &&
            Stream.of(pff.getSizesIds()).allMatch(Objects::isNull)) {
                return allProducts; // return all products
        } else {
            // Filter by brand
            allProducts.forEach(product -> {
                if (pff.getBrandsIds() != null && pff.getBrandsIds().contains(product.getBrand().getId())) {
                    products.add(product);
                } else if (pff.getColorsIds() != null && pff.getColorsIds().contains(product.getColor().getId())) {
                    products.add(product);
                } else if (pff.getMaterialsIds() != null && pff.getMaterialsIds().contains(product.getMaterial().getId())) {
                    products.add(product);
                } else if (pff.getSizesIds() != null && pff.getSizesIds().contains(product.getSize().getId())) {
                    products.add(product);
                }
            });
            return products;
        }
    }
}
