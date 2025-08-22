package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateProductRequestDTO {

    @JsonProperty("nm_material")
    private String nmMaterial;

    @JsonProperty("type")
    private String type;

    @JsonProperty("category")
    private String category;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("safetyStock")
    private Integer safetyStock;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("description")
    private String description;

    @JsonProperty("specifications")
    private String specifications;

    @JsonProperty("notes")
    private String notes;


    public void updateEntity(Product product) {
        if (nmMaterial != null) product.setNmMaterial(nmMaterial);
        if (type != null) product.setType(ProductType.valueOf(type));
        if (category != null) product.setCategory(ProductCategory.valueOf(category));
        if (unit != null) product.setUnit(UnitOfMeasure.valueOf(unit));
        if (safetyStock != null) product.setSafetyStock(safetyStock);
        if (isActive != null) product.setIsActive(isActive);
        if (description != null) product.setDescription(description);
        if (specifications != null) product.setSpecifications(specifications);
        if (notes != null) product.setNotes(notes);
    }
}