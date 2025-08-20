package mes.base.product;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mes.base.product.dto.ProductRequestDTO;
import mes.base.product.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
//@RequiredArgsConstructor
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/api/products")
    public Page<Product> searchProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String stype,
            @RequestParam(required = false) String svalue
    ) {
        return productService.searchProducts(page, size, stype, svalue);
    }
    @PostMapping
    @Operation(summary ="상품등록", description ="검색할 상품을 등록하는 API")
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO product) {
        return productService.addProduct(product);
    }

    @GetMapping("/api/products/{id}")
    @Operation(summary = "제품 상세 조회", description = "id값을 통한 제품 상세 조회")
    public ProductResponseDTO getOneDetailProduct(@PathVariable Long id) {
        return productService.getOneDetailProduct(id);
    }
    @PutMapping("/api/products/{id}")
    @Operation(summary = "상품수정", description = "상품 1개를 수정하는 API")
    public ProductResponseDTO updateOneProduct(@PathVariable Long id,@RequestBody ProductRequestDTO product) {
        return productService.updateOneProduct(id,product);
    }

    @DeleteMapping("/api/products/{id}")
    @Operation(summary = "상품 1개 삭제 및 비활성화", description = "상품 1개를 삭제하는 API")
    public void deleteOneProduct(@PathVariable Long id) {
        productService.deleteOneProduct(id);
    }

    @GetMapping("/api/products/{id}/history")
    @Operation(summary ="제품 변경 이력 조회", description = "제품 변경 이력조회를 id로 찾는 API")
    public ProductResponseDTO getOneHistoryProduct(@PathVariable Long id) {
        return productService.getOneHistoryProduct(id);
    }



}
