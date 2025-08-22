package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;


import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetProductResponseDTO {

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("cd_material")
    private String cdMaterial;

    @JsonProperty("nm_material")
    private String nmMaterial;

    @JsonProperty("type")
    private ProductType type;

    @JsonProperty("typeName")
    private String typeName;

    @JsonProperty("category")
    private ProductCategory category;

    @JsonProperty("unit")
    private UnitOfMeasure unit;

    @JsonProperty("safetyStock")
    private Integer safetyStock;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("lastUpdated")
    private LocalDateTime lastUpdated;

    public static GetProductResponseDTO toResponse(Product product) {
        return GetProductResponseDTO.builder()
                .productId(product.getProductId().toString())
                .cdMaterial(product.getCdMaterial())
                .nmMaterial(product.getNmMaterial())
                .type(product.getType())
                .category(product.getCategory())
                .unit(product.getUnit())
                .safetyStock(product.getSafetyStock())
                .isActive(product.getIsActive())
                .lastUpdated(product.getUpdatedAt())  // updatedAt â†’ lastUpdated
                .build();
    }


    private static String getTypeDisplayName(ProductType type) {
        switch (type) {
            case FINISHED: return "완제품";
            case SEMI: return "반제품";
            case RAW: return "원자재";
            default: return type.name();
        }
    }

}