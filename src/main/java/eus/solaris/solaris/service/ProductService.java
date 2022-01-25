package eus.solaris.solaris.service;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.support.PagedListHolder;

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
    public void save(Product product);
    public Product findById(Long id);
    public List<Product> findAll();
    public PagedListHolder<Product> getPagesFromProductList(List<Product> products);
    public List<Brand> getBrands();
    public List<Color> getColors();
    public List<Size> getSizes();
    public List<Material> getMaterials();
	public Set<Product> getFilteredProducts(ProductFilterForm pff);
	public List<SolarPanelModel> getModels();
	public ProductDescription getProductDescription(Product product, Locale locale);
    public Boolean create(ProductCreateForm pcf);
    public Boolean delete(Product product);
	public Boolean update(Product product, ProductCreateForm pcf, Locale locale);
}
