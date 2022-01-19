package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.support.PagedListHolder;

import eus.solaris.solaris.domain.Product;

public interface ProductService {
    public void save(Product product);
    public Product findById(Long id);
    public List<Product> findAll();
    public PagedListHolder<Product> getPagesFromProductList(List<Product> products);
}
