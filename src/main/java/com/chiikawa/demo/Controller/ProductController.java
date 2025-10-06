package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.Product.ProductResponseDto;
import com.chiikawa.demo.DTO.base.PaginatedResponse;
import com.chiikawa.demo.DTO.base.Response;

import com.chiikawa.demo.DTO.Product.ProductDto;
import com.chiikawa.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/paginated")
    public ResponseEntity<Response> listProductsPaginated(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        PaginatedResponse<ProductResponseDto> products = productService.listProductsWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved products", products));
    }

    @GetMapping
    public ResponseEntity<Response> listProducts() {
        List<ProductResponseDto> products = productService.listProducts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieved products",products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProduct(@PathVariable("id") Long productId) {
        ProductResponseDto product = productService.getProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","product found",product));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProductsByFilters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ) {
        List<ProductResponseDto> products = productService.searchProducts(name,minPrice,maxPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieved products with filters",products));
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@Valid @RequestBody ProductDto payload) {
        productService.createProduct(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201","success","successfully created product"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable("id") Long productId,@RequestBody ProductDto payload) {
        productService.updateProduct(productId,payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully updated product"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully deleted product id: " + productId));
    }
}