package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.Product.ProductDto;
import com.chiikawa.demo.DTO.supplier.SupplierDto;
import com.chiikawa.demo.DTO.supplier.UpdateSupplierDto;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.service.ProductService;
import com.chiikawa.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listSuppliers() {
        return supplierService.listSuppliers();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createSupplier(@RequestBody SupplierDto payload) {
        return supplierService.createSupplier(payload);
    }

    @PutMapping("{supplier_id}")
    public ResponseEntity<BaseResponseModel> updateSupplier(
            @PathVariable("supplier_id") Long supplierId,
            @RequestBody UpdateSupplierDto payload
    ) {
        return supplierService.updateSuppliers(supplierId,payload);
    }

    @DeleteMapping("{supplier_id}")
    public ResponseEntity<BaseResponseModel> deleteSupplier(@PathVariable("supplier_id") Long supplierId) {
        return supplierService.deleteSuppliers(supplierId);
    }
}