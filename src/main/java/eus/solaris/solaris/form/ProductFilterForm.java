package eus.solaris.solaris.form;

import java.util.List;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Generated
public class ProductFilterForm {
    private List<Long> brandsIds;
    private List<Long> colorsIds;
    private List<Long> sizesIds;
    private List<Long> materialsIds;
}