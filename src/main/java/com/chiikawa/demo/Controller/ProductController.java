package com.chiikawa.demo.Controller;

import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model_product.BaseResponseModelOfProduct;
import com.chiikawa.demo.model_product.ProductModel;
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
    public ResponseEntity<BaseResponseModelOfProduct> listProduct() {
        return productService.listProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModelOfProduct> getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponseModelOfProduct> searchProductByFilters(@RequestParam(value = "name") String name) {
        return productService.searchProduct(name);
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createProduct(@RequestBody ProductModel payload) {
        return productService.createProduct(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProduct(@PathVariable("id") Long id,
                                                                    @RequestBody ProductModel payload) {
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
