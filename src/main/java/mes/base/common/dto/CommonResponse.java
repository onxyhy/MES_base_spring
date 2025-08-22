package mes.base.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private T data;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorDetail error; // 에러 발생 시 세부 정보 포함

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetail {
        @JsonProperty("code")
        private String code;

        @JsonProperty("message")
        private String message;

        @JsonProperty("details")
        private Object details;
    }

    // 성공 응답 생성 메서드
    public static <T> CommonResponse<T> success(T data, String message) {
        return CommonResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 기존 에러 응답(메시지 1개만)
    public static <T> CommonResponse<T> error(String message) {
        return CommonResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 확장된 에러 응답(코드, 메시지, 상세정보)
    public static <T> CommonResponse<T> error(String code, String message, Object details) {
        return CommonResponse.<T>builder()
                .success(false)
                .error(new ErrorDetail(code, message, details))
                .timestamp(LocalDateTime.now())
                .build();
    }
}