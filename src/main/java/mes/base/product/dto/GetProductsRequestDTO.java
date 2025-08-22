// GetProductsRequestDTO.java
package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetProductsRequestDTO {

    @Builder.Default
    @JsonProperty("page")
    private int page = 1;                    // 기본값 1

    @Builder.Default
    @JsonProperty("pageSize")
    private int pageSize = 20;               // 기본값 20

    @JsonProperty("searchKeyword")
    private String searchKeyword;            // 검색 키워드 (제품명, 제품코드)

    @JsonProperty("type")
    private String type;                     // 제품 유형 필터 (기존 stype과 호환)

    @JsonProperty("categoryCode")
    private String categoryCode;             // 카테고리 코드 필터

    @JsonProperty("isActive")
    private Boolean isActive;                // 활성 상태 필터

    @JsonProperty("safetyStockMin")
    private Integer safetyStockMin;          // 최소 안전재고

    @JsonProperty("safetyStockMax")
    private Integer safetyStockMax;          // 최대 안전재고

    // 기존 stype, svalue와의 호환성을 위한 메서드
    public String getStype() {
        return type;
    }

    public String getSvalue() {
        return searchKeyword;
    }


    // DTO -> Entity 변환(최소한의 코드만)
    public Product toEntity() {
        return Product.builder()
                .nmMaterial(searchKeyword)
                .type(ProductType.valueOf(type))
                .category(ProductCategory.valueOf(categoryCode))
                .isActive(isActive)
                .safetyStock(safetyStockMin) // 필요 상황에 따라 safetyStockMin/Max 중 선택
                .build();
    }
}

