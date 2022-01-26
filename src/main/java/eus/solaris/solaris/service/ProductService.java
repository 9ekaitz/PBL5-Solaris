package eus.solaris.solaris.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.support.PagedListHolder;

import org.springframework.data.domain.Page;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.ProductDescription;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanelModel;
import eus.solaris.solaris.form.ProductCreateForm;
import eus.solaris.solaris.form.ProductFilterForm;

public interface ProductService {
    public Product save(Product product);
    public Product findById(Long id);
    public List<Product> findAll();
    public PagedListHolder<Product> getPagesFromProductList(List<Product> products);
    public List<Brand> getBrands();
    public List<Color> getColors();
    public List<Size> getSizes();
    public List<Material> getMaterials();
	public List<SolarPanelModel> getModels();
	public ProductDescription getProductDescription(Product product, Locale locale);
    public Product create(ProductCreateForm pcf) throws IOException;
    public Boolean delete(Product product);
	public Product update(Product product, ProductCreateForm pcf, Locale locale) throws IOException;
	public Page<Product> getFilteredProducts(ProductFilterForm pff, Integer page);
}
