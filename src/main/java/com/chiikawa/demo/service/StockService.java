package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.Stock.StockDto;
import com.chiikawa.demo.DTO.Stock.StockResponseDto;
import com.chiikawa.demo.DTO.Stock.UpdateStockDto;
import com.chiikawa.demo.DTO.base.PaginatedResponse;
import com.chiikawa.demo.common.config.ApplicationConfiguration;
import com.chiikawa.demo.exception.model.ResourceNotFoundException;
import com.chiikawa.demo.exception.model.UnprocessableEntityException;
import com.chiikawa.demo.Mapper.StockMapper;
import com.chiikawa.demo.entity.Product;
import com.chiikawa.demo.entity.Stock;
import com.chiikawa.demo.repository.ProductRepository;
import com.chiikawa.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMapper mapper;

    @Autowired
    private ApplicationConfiguration appConfig;

    public PaginatedResponse listStockWithPagination( Pageable pageable) {

        Page<Stock> stockPages = stockRepository.findAll(pageable);
        Page<StockResponseDto> stockPagesDto = stockPages.map(stock -> mapper.toDto(stock));

        return PaginatedResponse.from(stockPagesDto,appConfig.getPagination().getUrlByResource("stock"));
    }

    public List<StockResponseDto> listStocks() {
        List<Stock> stocks = stockRepository.findAll();

        return mapper.toDtoList(stocks);
    }

    public StockResponseDto getStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));

        return mapper.toDto(stock);
    }

    public void createStock(StockDto stock) {
        Product existingProduct = productRepository.findById(stock.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("product not found: " + stock.getProductId()));

        Stock stockEntity = mapper.toEntity(stock,existingProduct);

        stockRepository.save(stockEntity);
    }

    public void adjustQuantity(Long stockId, UpdateStockDto updateStock) {
        Stock existingStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("stock not found with id: " + stockId));

        if(updateStock.getOperationType() == 1) { // add
            int newQty = existingStock.getQuantity() + updateStock.getQuantity();

            existingStock.setQuantity(newQty);
        } else if(updateStock.getOperationType() == 2) { // remove
            // when remove amount > existing amount
            if(existingStock.getQuantity() < updateStock.getQuantity()) {
                throw new UnprocessableEntityException("quantity to remove can not be exceeded than existing stock: " + existingStock.getQuantity());
            }

            int newQty = existingStock.getQuantity() - updateStock.getQuantity();

            existingStock.setQuantity(newQty);
        } else {
            throw new IllegalArgumentException("invalid operation type");
        }

        stockRepository.save(existingStock);
    }

    public void deleteStock(Long stockId) {
        if(!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("stock is not found: " + stockId);
        }

        stockRepository.deleteById(stockId);
    }
}