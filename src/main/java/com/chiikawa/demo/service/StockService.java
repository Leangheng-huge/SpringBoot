package com.chiikawa.demo.service;

import com.chiikawa.demo.entity.Stock;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.StockModel;
import com.chiikawa.demo.model.UpdateStockModel;
import com.chiikawa.demo.model_product.BaseResponseWithDataModel;
import com.chiikawa.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("api/v1/stocks")
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseResponseWithDataModel> listStocks(){
        List<Stock> stocks = stockRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","successfully retrieved stocks",stocks));
    }

    public ResponseEntity<BaseResponseModel> createStock(StockModel stock) {
        Stock stockEntity = new Stock();

        stockEntity.setQuantity(stock.getQuantity());
        stockEntity.setProductId(stock.getProductID());
        stockEntity.setCreatedAt(LocalDateTime.now());

        stockRepository.save(stockEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created stock"));
    }

    public ResponseEntity<BaseResponseModel> adjustQuantity(Long stockId, UpdateStockModel updateStock) {
        Optional<Stock> existingStock = stockRepository.findById(stockId);

        // stock not found in DB
        if(existingStock.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","stock not found with id: " + stockId));
        }

        Stock stock = existingStock.get();

        if(updateStock.getOperationType() == 1) { // add
            int newQty = stock.getQuantity() + updateStock.getQuantity();

            stock.setQuantity(newQty);
        } else if(updateStock.getOperationType() == 2) { // remove
            // when remove amount > existing amount
            if(stock.getQuantity() < updateStock.getQuantity()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(new BaseResponseModel("fail","quantity to remove can not be exceeded than existing stock: " + stock.getQuantity()));
            }

            int newQty = stock.getQuantity() - updateStock.getQuantity();

            stock.setQuantity(newQty);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail","invalid operation type"));
        }

        stock.setUpdatedAt(LocalDateTime.now());
        stockRepository.save(stock);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully adjusted stock quantity"));
    }

    public ResponseEntity<BaseResponseModel> deleteStock(Long stockId) {
        if(!stockRepository.existsById(stockId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","stock not found with id: " + stockId));
        }

        stockRepository.deleteById(stockId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully deleted stock"));
    }

}
