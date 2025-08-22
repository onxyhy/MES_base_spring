package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.Product;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateProductResponseDTO {

    @JsonProperty("productId")
    private String productId;                 // 생성된 제품 ID

    @JsonProperty("productCode")
    private String productCode;               // 자동 생성된 제품코드

    // Entity -> DTO 변환 (static 메서드)
    public static CreateProductResponseDTO toResponse(Product product) {
        return CreateProductResponseDTO.builder()
                .productId(product.getProductId().toString())
                .productCode(product.getCdMaterial())
                .build();
    }
}