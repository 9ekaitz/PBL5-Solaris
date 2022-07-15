package eus.solaris.solaris.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class ProductCreateForm {

    public interface CreateProduct {

    }

    public interface EditProduct {

    }

    @NotEmpty(message = "{alert.createproduct.name.notEmpty}", groups = { CreateProduct.class, EditProduct.class })
    private String name;

    @NotEmpty(message = "{alert.createproduct.subtitle.notEmpty}", groups = { CreateProduct.class, EditProduct.class })
    private String subtitle;

    private Long brandId;

    @NotNull(message = "{alert.createproduct.price.notEmpty}", groups = { CreateProduct.class, EditProduct.class })
    @Min(value = 0, message = "{alert.createproduct.price.min}", groups = { CreateProduct.class, EditProduct.class })
    private Double price;

    private Long materialId;
    private Long modelId;
    private Long sizeId;
    private Long colorId;

    @NotEmpty(message = "{alert.createproduct.description.notEmpty}", groups = { CreateProduct.class, EditProduct.class })
    private String description;
    
    @NotNull(message = "{alert.createproduct.imagePath.notEmpty}", groups = { CreateProduct.class })
    private MultipartFile image;

    private Long languageId;
}
