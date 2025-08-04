package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.supplier.SupplierDto;
import com.chiikawa.demo.DTO.supplier.UpdateSupplierDto;
import com.chiikawa.demo.Exception.model.DuplicateResourceException;
import com.chiikawa.demo.Exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Mapper.SupplierMapper;
import com.chiikawa.demo.entity.Supplier;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listSuppliers(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel(
                        "success",
                        "successfully retrieved",
                        mapper.toDoList(suppliers)));
    }

    public ResponseEntity<BaseResponseModel> createSupplier(SupplierDto dto){
            // if duplicate supplier name, then reject
        if(supplierRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("supplier not found with name: " + dto.getName());
        }

        Supplier supplierEntity = mapper.toEntity(dto);

        supplierRepository.save(supplierEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created supplier"));
    }

    public ResponseEntity<BaseResponseModel> updateSuppliers(Long supplierId, UpdateSupplierDto dto){
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));

        mapper.updateEntityFromDto(existingSupplier,dto);
        supplierRepository.save(existingSupplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("Success","Successfully updated."));
    }

    public ResponseEntity<BaseResponseModel> deleteSuppliers (Long supplierId){
        if (!supplierRepository.existsById(supplierId)){
            throw new ResourceNotFoundException("Supplier nt found with id: " + supplierId);
        }

        supplierRepository.existsById(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("Success","Successfully deleted."));
    }

}
