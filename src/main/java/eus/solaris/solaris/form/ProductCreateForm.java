package eus.solaris.solaris.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductCreateForm {
    private String name;
    private String subtitle;
    private Long brandId;
    private Double price;
    private Long materialId;
    private Long modelId;
    private Long sizeId;
    private Long colorId;
    private String description;
    private String imagePath;
    private Long languageId;
}
