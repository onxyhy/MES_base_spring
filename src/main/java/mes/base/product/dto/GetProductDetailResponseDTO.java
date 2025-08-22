package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // í´ëž˜ìŠ¤ ë ˆë²¨ì— ì ìš©
public class GetProductDetailResponseDTO {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @JsonProperty("id")
    private String id;

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

    @JsonProperty("additionalInfo")
    private AdditionalInfoDTO additionalInfo;

    @JsonProperty("id_create")
    private String idCreate;

    @JsonProperty("id_update")
    private String idUpdate;

    @JsonProperty("dt_create")
    private String dtCreate;

    @JsonProperty("dt_update")
    private String dtUpdate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AdditionalInfoDTO {
        private String description;
        private String specifications;
        private String notes;

        // ëª¨ë“  í•„ë“œê°€ nullì¸ì§€ ì²´í¬
        public boolean isEmpty() {
            return description == null && specifications == null && notes == null;
        }
    }

    public static GetProductDetailResponseDTO toResponse(Product product) {
        if (product == null) {
            return null;
        }

        // AdditionalInfoëŠ” ì‹¤ì œ ê°’ì´ ìžˆì„ ë•Œë§Œ ìƒì„±
        AdditionalInfoDTO additionalInfo = null;
        if (hasAdditionalInfo(product)) {
            additionalInfo = AdditionalInfoDTO.builder()
                    .description(product.getDescription())
                    .specifications(product.getSpecifications())
                    .notes(product.getNotes())
                    .build();
        }

        return GetProductDetailResponseDTO.builder()
                .id(product.getProductId() != null ? product.getProductId().toString() : null)
                .cdMaterial(product.getCdMaterial())
                .nmMaterial(product.getNmMaterial())
                .type(product.getType())
                .typeName(product.getType() != null ? product.getType().getDisplayName() : null)
                .category(product.getCategory())
                .unit(product.getUnit())
                .safetyStock(product.getSafetyStock())
                .isActive(product.getIsActive())
                .additionalInfo(additionalInfo)
                .idCreate(product.getIdCreate())
                .idUpdate(product.getIdUpdate())
                .dtCreate(product.getCreatedAt() != null ? product.getCreatedAt().format(ISO_FORMATTER) : null)
                .dtUpdate(product.getUpdatedAt() != null ? product.getUpdatedAt().format(ISO_FORMATTER) : null)
                .build();
    }

    private static boolean hasAdditionalInfo(Product product) {
        return product.getDescription() != null ||
                product.getSpecifications() != null ||
                product.getNotes() != null;
    }
}