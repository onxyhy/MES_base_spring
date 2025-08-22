package mes.base.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    FINISHED("완제품"),
    SEMI("반제품"),
    RAW("원자재");

    private final String displayName;
}