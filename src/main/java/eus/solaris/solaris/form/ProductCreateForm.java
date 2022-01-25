package eus.solaris.solaris.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class ProductCreateForm {
    @NotEmpty(message = "{alert.createproduct.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{alert.createproduct.subtitle.notEmpty}")
    private String subtitle;

    private Long brandId;

    @NotNull(message = "{alert.createproduct.price.notEmpty}")
    @Min(value = 0, message = "{alert.createproduct.price.min}")
    private Double price;

    private Long materialId;
    private Long modelId;
    private Long sizeId;
    private Long colorId;

    @NotEmpty(message = "{alert.createproduct.description.notEmpty}")
    private String description;

    @NotEmpty(message = "{alert.createproduct.imagePath.notEmpty}")
    private String imagePath;
    
    private Long languageId;
}
