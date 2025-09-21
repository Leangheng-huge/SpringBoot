package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.supplier.SupplierDto;
import com.chiikawa.demo.DTO.supplier.SupplierResponseDto;
import com.chiikawa.demo.DTO.supplier.UpdateSupplierDto;
import com.chiikawa.demo.exception.model.DuplicateResourceException;
import com.chiikawa.demo.exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Mapper.SupplierMapper;
import com.chiikawa.demo.entity.Supplier;

import com.chiikawa.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public List<SupplierResponseDto> listSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return mapper.toDoList(suppliers);
    }

    public void createSupplier(SupplierDto dto) {
        // if duplicate supplier name , then reject
        if(supplierRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("supplier already existed with name: " + dto.getName());
        }

        Supplier supplier = mapper.toEntity(dto);

        supplierRepository.save(supplier);
    }

    public void updateSupplier(Long supplierId, UpdateSupplierDto dto) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("supplier not found with id: " + supplierId));

        mapper.updateEntityFromDto(existingSupplier,dto);

        supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("supplier not found with id: " + supplierId);
        }

        supplierRepository.deleteById(supplierId);
    }

}