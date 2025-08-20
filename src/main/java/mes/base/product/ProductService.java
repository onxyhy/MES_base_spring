package mes.base.product;

import jakarta.transaction.Transactional;
import mes.base.product.dto.ProductRequestDTO;
import mes.base.product.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // DTO -> Entity 변환
    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setCdMaterial(dto.getCdMaterial());
        product.setNmMaterial(dto.getNmMaterial());
        product.setType(dto.getType());
        product.setCategory(dto.getCategory());
        product.setUnit(dto.getUnit());
        product.setSafetyStock(dto.getSafetyStock());
        product.setIsActive(dto.getIsActive());
        return product;
    }

    // Entity -> DTO 변환
    private ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getCdMaterial(),
                product.getNmMaterial(),
                product.getType(),
                product.getCategory(),
                product.getUnit(),
                product.getSafetyStock(),
                product.getIsActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO dto) {
        Product entity = toEntity(dto);
        Product saved = productRepository.save(entity);
        return toResponse(saved);
    }

    public Page<Product> searchProducts(int page, int size, String stype, String svalue) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (svalue == null || svalue.isEmpty()) {
            return productRepository.findAll(pageRequest);
        }
        String keyword = "%" + svalue + "%";
        switch (stype) {
            case "name":
                return productRepository.findByNmMaterialLike(keyword, pageRequest);
            case "category":
                ProductCategory category = ProductCategory.valueOf(svalue.toUpperCase());
                return productRepository.findByCategory(category, pageRequest);
            default:
                return productRepository.findAll(pageRequest);
        }
    }

    public ProductResponseDTO getOneDetailProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(this::toResponse).orElse(null);
    }

    @Transactional
    public void deleteOneProduct(long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public ProductResponseDTO updateOneProduct(Long id, ProductRequestDTO dto) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            // 필요한 필드만 업데이트
            product.setCdMaterial(dto.getCdMaterial());
            product.setNmMaterial(dto.getNmMaterial());
            product.setType(dto.getType());
            product.setCategory(dto.getCategory());
            product.setUnit(dto.getUnit());
            product.setSafetyStock(dto.getSafetyStock());
            product.setIsActive(dto.getIsActive());
            Product saved = productRepository.save(product);
            return toResponse(saved);
        } else {
            return null;
        }
    }

    public ProductResponseDTO getOneHistoryProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(this::toResponse).orElse(null);
    }
}
