package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @JsonProperty("cd_material")
    private String cdMaterial;

    @JsonProperty("nm_material")
    private String nmMaterial;

    private ProductType type;
    private ProductCategory category;
    private UnitOfMeasure unit;
    private Integer safetyStock;
    private Boolean isActive;
}