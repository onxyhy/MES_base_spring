package mes.base.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import mes.base.productHistory.ProductHistory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductHistory> productHistory;

    @Column(nullable = false, unique = true)
    @JsonProperty("cd_material")
    private String cdMaterial;

    @Column(nullable = false)
    @JsonProperty("nm_material")
    private String nmMaterial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitOfMeasure unit;

    @Column(nullable = false)
    private Integer safetyStock;

    @Column(nullable = false)
    private Boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String specifications;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = true)
    private String idCreate;

    @Column(nullable = true)
    private String idUpdate;
}