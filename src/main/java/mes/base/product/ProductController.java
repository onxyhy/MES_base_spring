package mes.base.product;

import io.swagger.v3.oas.annotations.Operation;
import mes.base.common.dto.CommonResponse;
import mes.base.product.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
//@RequiredArgsConstructor
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping
    @Operation(summary = "상품전체조회", description = "상품 전체를 조회하는 API")
    public CommonResponse<Page<GetProductResponseDTO>> searchProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String stype,
            @RequestParam(required = false) String svalue
    ) {
        try {
            Page<GetProductResponseDTO> products = productService.searchProducts(page, size, stype, svalue);
            return CommonResponse.success(products, "상품 조회 성공");
        } catch (Exception e) {
            return CommonResponse.error("SEARCH_ERROR", "상품 조회 중 오류가 발생했습니다.", e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "상품등록", description = "검색할 상품을 등록하는 API")
    public CommonResponse<CreateProductResponseDTO> addProduct(@RequestBody CreateProductRequestDTO request) {
        try {
            // 서비스 호출해서 DTO 획득
            CreateProductResponseDTO response = productService.createProduct(request);
            return CommonResponse.success(
                    response,
                    "제품 정보가 성공적으로 등록되었습니다. (제품코드: " + response.getProductCode() + ")"
            );
        } catch (Exception e) {
            // 확장된 에러 응답 적용
            return CommonResponse.error(
                    "CREATE_ERROR",
                    "제품 등록 중 오류가 발생했습니다.",
                    e.getMessage()
            );
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "제품 상세 조회", description = "id값을 통한 제품 상세 조회")
    public GetProductDetailResponseDTO getOneDetailProduct(@PathVariable Long id) {
        return productService.getOneDetailProduct(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "상품수정", description = "상품 1개를 수정하는 API")
    public void updateOneProduct(@PathVariable Long id,@RequestBody UpdateProductRequestDTO request) {
        productService.updateOneProduct(id,request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "상품 1개 삭제 및 비활성화", description = "상품 1개를 삭제하는 API")
    public CommonResponse<Void> deleteOneProduct(@PathVariable Long id) {
        productService.deleteOneProduct(id);
        return CommonResponse.success(null, "삭제되었습니다");
    }

    @GetMapping("/{id}/history")
    @Operation(summary ="제품 변경 이력 조회", description = "제품 변경 이력조회를 id로 찾는 API")
    public List<GetProductHistoryResponseDTO> getAllHistoryProduct(@PathVariable Long id) {
        return productService.getAllHistoryProduct(id);
    }



}