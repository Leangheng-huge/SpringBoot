package com.chiikawa.demo.service;

import com.chiikawa.demo.entity.Product;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model_product.BaseResponseWithDataModel;
import com.chiikawa.demo.DTO.ProductDto;
import com.chiikawa.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<BaseResponseWithDataModel> listProduct(){
        List<Product> products = productRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "Products retrieved",products));
    }

    public ResponseEntity<BaseResponseWithDataModel> getProduct(@PathVariable("id") Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(404)
                    .body(new BaseResponseWithDataModel("Fail", "Product not found with id: " + id,null));
        }
        return ResponseEntity.status(200)
                .body(new BaseResponseWithDataModel("success", "Product retrieved",product.get()));
    }

    public ResponseEntity<BaseResponseModel> createProduct(ProductDto product){
        Product productEntity = new Product();

        productEntity.setPrice(product.getPrice());
        productEntity.setProductName( product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        productRepository.save(productEntity);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "Product created"));
    }

    public ResponseEntity<BaseResponseModel> updateProduct(Long productId, ProductDto product){
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isEmpty()){
            // if user not found response with 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "Product not found with id: " + productId));
        }
        // user found, then update it
        Product updatedProduct = existingProduct.get();

        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setProductName( product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setUpdatedAt(LocalDateTime.now());

        productRepository.save(updatedProduct);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "Product updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteProduct(Long productId){
        if (!productRepository.existsById(productId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("Fail", "Product not found with id: " + productId));
        }
        productRepository.deleteById(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "Product deleted"));
    }

    public ResponseEntity<BaseResponseWithDataModel> searchProduct(String name, Double minPrice, Double maxPrice){
        String formattedName = name != null?
                name.toLowerCase()
                :null;
        List <Product> product = productRepository.findProductWithFilters(formattedName, minPrice , maxPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "Products retrieved",product));
    }
}
