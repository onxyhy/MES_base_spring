package mes.base.productHistory;

import mes.base.product.Product;
import mes.base.productHistoryItem.ProductHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    Optional<ProductHistory> findById(Long id);
    // ProductHistoryRepository.java
    List<ProductHistory> findByProductProductIdOrderByCreatedAtDesc(Long productId);
}

