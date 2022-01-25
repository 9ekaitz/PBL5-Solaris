package eus.solaris.solaris.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
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
import eus.solaris.solaris.domain.ProductDescription;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanelModel;
import eus.solaris.solaris.form.ProductCreateForm;
import eus.solaris.solaris.form.ProductFilterForm;
import eus.solaris.solaris.repository.ProductDescriptionRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.repository.filters.BrandRepository;
import eus.solaris.solaris.repository.filters.ColorRepository;
import eus.solaris.solaris.repository.filters.MaterialRepository;
import eus.solaris.solaris.repository.filters.ModelRepository;
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
    private ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private LanguageServiceImpl languageService;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Boolean delete(Product product) {
        try {
            for (ProductDescription pDescription : product.getDescriptions()){
                productDescriptionRepository.delete(pDescription);
            }
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
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
    public List<SolarPanelModel> getModels() {
        return modelRepository.findAll();
    }

    @Override
    public ProductDescription getProductDescription(Product product, Locale locale) {
        for (ProductDescription pDescription : product.getDescriptions()) {
            if (pDescription.getLanguage().getCode().equals(locale.getLanguage())) {
                return pDescription;
            }
        }
        // if no description found with the user locale, return the first description
        return product.getDescriptions().iterator().next();
    }

    @Override
    public Boolean create(ProductCreateForm pcf) {
        try {
            ProductDescription productDescription = new ProductDescription();
            productDescription.setName(pcf.getName());
            productDescription.setSubtitle(pcf.getSubtitle());
            productDescription.setDescription(pcf.getDescription());
            productDescription.setLanguage(languageService.findById(pcf.getLanguageId()));
            Product product = new Product();
            product.setModel(modelRepository.findById(pcf.getModelId()).orElse(null));
            product.setImagePath(pcf.getImagePath());
            product.getModel().setBrand(brandRepository.findById(pcf.getBrandId()).orElse(null));
            product.setPrice(pcf.getPrice());
            product.getModel().setColor(colorRepository.findById(pcf.getColorId()).orElse(null));
            product.getModel().setMaterial(materialRepository.findById(pcf.getMaterialId()).orElse(null));
            product.getModel().setSize(sizeRepository.findById(pcf.getSizeId()).orElse(null));
            this.save(product);
            productDescription.setProduct(product);
            productDescriptionRepository.save(productDescription);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean update(Product product, ProductCreateForm pcf, Locale locale) {
        try {
            product.setModel(modelRepository.findById(pcf.getModelId()).orElse(null));
            product.setImagePath(pcf.getImagePath());
            product.getModel().setBrand(brandRepository.findById(pcf.getBrandId()).orElse(null));
            product.setPrice(pcf.getPrice());
            product.getModel().setColor(colorRepository.findById(pcf.getColorId()).orElse(null));
            product.getModel().setMaterial(materialRepository.findById(pcf.getMaterialId()).orElse(null));
            product.getModel().setSize(sizeRepository.findById(pcf.getSizeId()).orElse(null));
            this.save(product);
            boolean descriptionUpdated = false;
            for (ProductDescription pDescription : product.getDescriptions()) {
                if (pDescription.getLanguage().getCode().equals(locale.getLanguage())) {
                    pDescription.setName(pcf.getName());
                    pDescription.setSubtitle(pcf.getSubtitle());
                    pDescription.setDescription(pcf.getDescription());
                    pDescription.setLanguage(languageService.findById(pcf.getLanguageId()));
                    productDescriptionRepository.save(pDescription);
                    pDescription.setProduct(product);
                    productDescriptionRepository.save(pDescription);
                    descriptionUpdated = true;
                }
            }
            if(!descriptionUpdated) {
                ProductDescription productDescription = new ProductDescription();
                productDescription.setName(pcf.getName());
                productDescription.setSubtitle(pcf.getSubtitle());
                productDescription.setDescription(pcf.getDescription());
                productDescription.setLanguage(languageService.findById(pcf.getLanguageId()));
                productDescription.setProduct(product);
                productDescriptionRepository.save(productDescription);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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

        if (!specifications.isEmpty()) {

            Specification<Product> query = Specification.where(null);
            for (Specification<Product> spec : specifications)
                query = Specification.where(query).and(spec);

            return productRepository.findAll(query, pageable);
        }

        return productRepository.findAll(pageable);
    }
}
