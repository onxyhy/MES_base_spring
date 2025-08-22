package mes.base.product;

import mes.base.product.dto.GetProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // 기존 메서드들 Entity 반환 유지하기
    Page<Product> findByNmMaterialLike(String nmMaterial, Pageable pageable);
    Page<Product> findByCategory(ProductCategory category, Pageable pageable);
}