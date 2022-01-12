package eus.solaris.solaris.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDto {
    
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private String imageUrl;

}
