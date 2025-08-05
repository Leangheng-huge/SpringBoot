package com.chiikawa.demo.Controller;

import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.DTO.Product.ProductDto;
import com.chiikawa.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listProduct() {
        return productService.listProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseWithDataModel> getProduct(@PathVariable("id") Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseWithDataModel> searchProductsByFilters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ) {
        return productService.searchProduct(name,minPrice,maxPrice);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createProduct(@Valid @RequestBody ProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("id") Long id,
                                                                    @Valid @RequestBody ProductDto payload) {
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
