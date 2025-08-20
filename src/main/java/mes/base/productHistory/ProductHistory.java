package mes.base.productHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mes.base.product.Product;
import mes.base.product.ProductCategory;
import mes.base.product.ProductType;
import mes.base.product.UnitOfMeasure;
import mes.base.productHistoryItem.ProductHistoryItem;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productHistoryId;

    @OneToMany(mappedBy = "productHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductHistoryItem> productHistoryItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    @JsonProperty("action")
    private ActionType action;

    @Column(nullable = false)
    @JsonProperty("userId")
    private String userId;

    @Column(nullable = false)
    @JsonProperty("userName")
    private String userName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @JsonProperty("reason")
    private String reason;
}
