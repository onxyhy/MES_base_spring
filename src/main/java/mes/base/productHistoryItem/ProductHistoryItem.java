package mes.base.productHistoryItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mes.base.productHistory.ProductHistory;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productHistoryItemId;

    @ManyToOne
    @JoinColumn(name = "productHistoryId")
    private ProductHistory productHistory;

    @Column(nullable = false)
    @JsonProperty("fieldName")
    private String fieldName;

    @Column(nullable = false)
    @JsonProperty("oldValue")
    private String oldValue;

    @Column(nullable = false)
    @JsonProperty("newValue")
    private String newValue;
}
