package mes.base.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mes.base.product.dto.*;
import mes.base.productHistory.ActionType;
import mes.base.productHistory.ProductHistory;
import mes.base.productHistory.ProductHistoryRepository;
import mes.base.productHistoryItem.ProductHistoryItem;
import mes.base.productHistoryItem.ProductHistoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductHistoryItemRepository productHistoryItemRepository;
    @Autowired
    private ProductHistoryRepository productHistoryRepository;


    @Transactional
    public CreateProductResponseDTO createProduct(CreateProductRequestDTO dto) {
        // DTO -> Entity 변환 (DTO의 static 메서드 사용)
        Product entity = CreateProductRequestDTO.toEntity(dto);

        // 제품코드 자동 생성
        String productCode = generateProductCode(entity.getType());
        entity.setCdMaterial(productCode);

        // 저장
        Product product = productRepository.save(entity);

        ProductHistory productHistory = new ProductHistory();
        productHistory.setProduct(product);
        productHistory.setAction(ActionType.CREATE);
        productHistory.setReason("신규 제품 등록");
//        productHistory.setUserId(dto.getIdCreate());
//        productHistory.setUserName(dto.getIdCreate());
        productHistoryRepository.save(productHistory);

        // Entity -> DTO 변환 (DTO의 static 메서드 사용)
        return CreateProductResponseDTO.toResponse(product);
    }

    // 제품코드 생성 로직
    private String generateProductCode(ProductType type) {
        String prefix = switch (type) {
            case FINISHED -> "FG";
            case SEMI -> "SF";
            case RAW -> "RM";
        };

        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8, 12);
        return prefix + timestamp;
    }

    public Page<GetProductResponseDTO> searchProducts(int page, int size, String stype, String svalue) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products;

        if (svalue == null || svalue.isEmpty()) {
            products = productRepository.findAll(pageRequest);
        } else {
            String keyword = "%" + svalue + "%";
            switch (stype) {
                case "name":
                    products = productRepository.findByNmMaterialLike(keyword, pageRequest);
                    break;
                case "category":
                    ProductCategory category = ProductCategory.valueOf(svalue.toUpperCase());
                    products = productRepository.findByCategory(category, pageRequest);
                    break;
                default:
                    products = productRepository.findAll(pageRequest);
                    break;
            }
        }

        // 엔티티 → DTO 변환
        return products.map(GetProductResponseDTO::toResponse);
    }

    public GetProductDetailResponseDTO getOneDetailProduct(Long id) {  // 반환 타입 변경
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(GetProductDetailResponseDTO::toResponse).orElse(null);
    }

    @Transactional
    public void deleteOneProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("제품을 찾을 수 없습니다"));

        ProductHistory productHistory = new ProductHistory();
        productHistory.setProduct(product);
        productHistory.setAction(ActionType.DELETE);
        productHistoryRepository.save(productHistory);

        productRepository.deleteById(productId);
    }


    @Transactional
    public void updateOneProduct(Long id, UpdateProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("제품을 찾을 수 없습니다"));

        ProductHistory productHistory = new ProductHistory();
        productHistory.setProduct(product);
        productHistory.setAction(ActionType.UPDATE);
        productHistory.setReason("제품 정보 수정");
//        productHistory.setUserName(product.get);
//        productHistory.setUserId(product.getIdCreate());
        productHistory = productHistoryRepository.save(productHistory);

        ProductHistoryItem productHistoryItem = null;

        if (request.getNmMaterial() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("NmMaterial");
            productHistoryItem.setOldValue(product.getNmMaterial());
            productHistoryItem.setNewValue(request.getNmMaterial());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getType() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Type");
            productHistoryItem.setOldValue(product.getType() != null ? product.getType().toString() : null);
            productHistoryItem.setNewValue(request.getType());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getCategory() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Category");
            productHistoryItem.setOldValue(product.getCategory() != null ? product.getCategory().toString() : null);
            productHistoryItem.setNewValue(request.getCategory());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getUnit() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Unit");
            productHistoryItem.setOldValue(product.getUnit() != null ? product.getUnit().toString() : null);
            productHistoryItem.setNewValue(request.getUnit());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getSafetyStock() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("SafetyStock");
            productHistoryItem.setOldValue(product.getSafetyStock() != null ? product.getSafetyStock().toString() : null);
            productHistoryItem.setNewValue(request.getSafetyStock().toString());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }

        request.updateEntity(product);
        productRepository.save(product);

        if (request.getIsActive() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("IsActive");
            productHistoryItem.setOldValue(product.getIsActive() != null ? product.getIsActive().toString() : null);
            productHistoryItem.setNewValue(request.getIsActive().toString());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getDescription() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Description");
            productHistoryItem.setOldValue(product.getDescription());
            productHistoryItem.setNewValue(request.getDescription());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getSpecifications() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Specifications");
            productHistoryItem.setOldValue(product.getSpecifications());
            productHistoryItem.setNewValue(request.getSpecifications());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }
        request.updateEntity(product);
        productRepository.save(product);

        if (request.getNotes() != null) {
            productHistoryItem = new ProductHistoryItem();
            productHistoryItem.setFieldName("Notes");
            productHistoryItem.setOldValue(product.getNotes());
            productHistoryItem.setNewValue(request.getNotes());
            productHistoryItem.setProductHistory(productHistory);
            productHistoryItemRepository.save(productHistoryItem);
        }

        request.updateEntity(product);
        productRepository.save(product);
    }

    private ProductHistoryItem createHistoryItem(String fieldName, String oldValue, String newValue, ProductHistory history) {
        ProductHistoryItem item = new ProductHistoryItem();
        item.setFieldName(fieldName);
        item.setOldValue(oldValue);
        item.setNewValue(newValue);
        item.setProductHistory(history);
        return item;
    }


    public List<GetProductHistoryResponseDTO> getAllHistoryProduct(Long id) {
        // 1. 제품 찾기
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("제품을 찾을 수 없습니다"));

        List<ProductHistory> productHistories = productHistoryRepository.findByProductProductIdOrderByCreatedAtDesc(id);
        if (productHistories.isEmpty()) {
            throw new EntityNotFoundException("제품 이력을 찾을 수 없습니다");
        }
        List<GetProductHistoryResponseDTO> result = new ArrayList<>();

        for (ProductHistory history : productHistories) {
            // 각 이력의 변경 항목들 조회
            List<ProductHistoryItem> items = productHistoryItemRepository.findByProductHistory(history);
            history.setProductHistoryItems(items);

            // DTO 변환 후 리스트에 추가
            result.add(GetProductHistoryResponseDTO.fromEntity(history));
        }

        return result;
    }

}
