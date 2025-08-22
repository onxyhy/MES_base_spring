package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequestDTO {
    @JsonProperty("nm_material")
    private String nmMaterial;     // 제품명 (필수)

    @JsonProperty("type")
    private ProductType type;      // 제품 유형 (필수)

    @JsonProperty("category")
    private ProductCategory category; // 카테고리 정보 (필수)

    @JsonProperty("unit")
    private UnitOfMeasure unit;    // 단위 정보 (필수)

    @JsonProperty("safetyStock")
    private Integer safetyStock;   // 안전재고 (필수, 0 이상)

    @JsonProperty("isActive")
    private Boolean isActive;      // 활성화 상태 (필수)

    @JsonProperty("additionalInfo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AdditionalInfo additionalInfo; // 추가 정보 (선택)

    @JsonProperty("id_create")
    private String idCreate;       // 생성자 ID (필수)

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalInfo {
        @JsonProperty("description")
        private String description;

        @JsonProperty("specifications")
        private String specifications;

        @JsonProperty("notes")
        private String notes;
    }

    // DTO -> Entity 변환 (static 메서드)
    public static Product toEntity(CreateProductRequestDTO dto) {
        Product product = new Product();

        product.setNmMaterial(dto.getNmMaterial());
        product.setType(dto.getType());
        product.setCategory(dto.getCategory());
        product.setUnit(dto.getUnit());
        product.setSafetyStock(dto.getSafetyStock());
        product.setIsActive(dto.getIsActive());
        product.setIdCreate(dto.getIdCreate());

        return product;
    }
}