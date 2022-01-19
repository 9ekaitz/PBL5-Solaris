package eus.solaris.solaris.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${solaris.pagination.products.pagesize}")
	private Integer pagesize;

    @Autowired
    private ProductRepository productRepository;

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

}

