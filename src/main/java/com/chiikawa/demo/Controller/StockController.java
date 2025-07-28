package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.Stock.StockDto;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.UpdateStockModel;
import com.chiikawa.demo.model_product.BaseResponseWithDataModel;
import com.chiikawa.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel>listStocks(){
        return stockService.listStocks();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> createStock(@RequestBody StockDto payload){
        return stockService.createStock(payload);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BaseResponseModel> adjustQuantity(@PathVariable("id") Long stockId,@RequestBody UpdateStockModel payload) {
        return stockService.adjustQuantity(stockId,payload);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponseModel> deleteStock(@PathVariable("id") Long stockId) {
        return stockService.deleteStock(stockId);
    }
}
