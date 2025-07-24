package com.chiikawa.demo.Controller;

import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model_product.BaseResponseWithDataModel;
import com.chiikawa.demo.DTO.Product.ProductDto;
import com.chiikawa.demo.service.ProductService;
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
    public ResponseEntity<BaseResponseWithDataModel> getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseWithDataModel> searchProductByFilters(@RequestParam(value = "name")
                                                                                 String name, Double minPrice, Double maxPrice) {

        return productService.searchProduct(name, minPrice , maxPrice);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createProduct(@RequestBody ProductDto payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("id") Long id,
                                                                    @RequestBody ProductDto payload) {
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
