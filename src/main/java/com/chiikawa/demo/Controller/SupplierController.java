package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.base.Response;
import com.chiikawa.demo.DTO.supplier.SupplierDto;
import com.chiikawa.demo.DTO.supplier.SupplierResponseDto;
import com.chiikawa.demo.DTO.supplier.UpdateSupplierDto;

import com.chiikawa.demo.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<Response> listSuppliers() {
        List<SupplierResponseDto> suppliers = supplierService.listSuppliers();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success(
                                "200",
                                "success",
                                "successfully retrieved suppliers",
                                suppliers
                        )
                );
    }

    @PostMapping
    public ResponseEntity<Response> createSupplier(@Valid @RequestBody SupplierDto payload) {
        supplierService.createSupplier(payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("201","success","successfully created supplier"));
    }

    @PutMapping("{supplier_id}")
    public ResponseEntity<Response> updateSupplier(
            @PathVariable("supplier_id") Long supplierId,
            @Valid @RequestBody UpdateSupplierDto payload
    ) {
        supplierService.updateSupplier(supplierId,payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully updated supplier"));
    }

    @DeleteMapping("{supplier_id}")
    public ResponseEntity<Response> deleteSupplier(@PathVariable("supplier_id") Long supplierId) {
        supplierService.deleteSupplier(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully deleted supplier"));
    }
}