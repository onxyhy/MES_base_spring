package mes.base.productHistoryItem;

import mes.base.productHistory.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductHistoryItemRepository extends JpaRepository<ProductHistoryItem, Long> {
    Optional<ProductHistoryItem> findById(Long id);

    List<ProductHistoryItem> findByProductHistory(ProductHistory latestHistory);
}
