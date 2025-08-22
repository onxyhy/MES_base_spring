package mes.base.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mes.base.productHistory.ProductHistory;
import mes.base.productHistoryItem.ProductHistoryItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetProductHistoryResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("action")
    private String action;  // "CREATE", "UPDATE", "DELETE"

    @JsonProperty("changedFields")
    private List<ChangedField> changedFields;

//    @JsonProperty("userId")
//    private String userId;
//
//    @JsonProperty("userName")
//    private String userName;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("reason")
    private String reason;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class ChangedField {
        @JsonProperty("fieldName")
        private String fieldName;

        @JsonProperty("oldValue")
        private String oldValue;

        @JsonProperty("newValue")
        private String newValue;

        // ProductHistoryItem을 ChangedField로 변환
        public static ChangedField fromHistoryItem(ProductHistoryItem item) {
            return ChangedField.builder()
                    .fieldName(item.getFieldName())
                    .oldValue(item.getOldValue())
                    .newValue(item.getNewValue())
                    .build();
        }
    }

    // Entity에서 DTO로 변환
    public static GetProductHistoryResponseDTO fromEntity(ProductHistory history) {
        return GetProductHistoryResponseDTO.builder()
                .id(history.getProductHistoryId())
                .action(history.getAction().toString())
                .changedFields(
                        history.getProductHistoryItems() != null ?
                                history.getProductHistoryItems().stream()
                                        .map(ChangedField::fromHistoryItem)
                                        .collect(Collectors.toList()) : null
                )
//                .userId(history.getUserId())
//                .userName(history.getUserName())
                .timestamp(history.getCreatedAt())
                .timestamp(history.getUpdatedAt())
                .reason(history.getReason())
                .build();
    }
}