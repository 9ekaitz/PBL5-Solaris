package eus.solaris.solaris.repository.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import eus.solaris.solaris.domain.Product;

public class ProductSpecifications {

    public static Specification<Product> findByBrandIds(List<Long> brandIds) {
        return (product, cq, cb) -> product.get("brand").get("id").in(brandIds);
    }

    public static Specification<Product> findByMaterialIds(List<Long> materialIds) {
        return (product, cq, cb) -> product.get("material").get("id").in(materialIds);
    }

    public static Specification<Product> findByColorIds(List<Long> colorId) {
        return (product, cq, cb) -> product.get("color").get("id").in(colorId);
    }

    public static Specification<Product> findBySizeIds(List<Long> sizeIds) {
        return (product, cq, cb) -> product.get("size").get("id").in(sizeIds);
    }
}
