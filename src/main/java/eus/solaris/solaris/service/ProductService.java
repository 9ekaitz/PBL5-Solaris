package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.Size;
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
	public Page<Product> getFilteredProducts(ProductFilterForm pff, Integer page);
}