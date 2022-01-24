package eus.solaris.solaris.form;

import eus.solaris.solaris.domain.Brand;
import eus.solaris.solaris.domain.Color;
import eus.solaris.solaris.domain.Language;
import eus.solaris.solaris.domain.Material;
import eus.solaris.solaris.domain.Size;
import eus.solaris.solaris.domain.SolarPanelModel;
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
