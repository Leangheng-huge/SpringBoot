package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.order.OrderDto;
import com.chiikawa.demo.DTO.order.OrderUpdateDto;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listOrders(){
        return orderService.listOrders();
    }

    @PostMapping
    public ResponseEntity<BaseResponseModel> placeOrder(@Valid @RequestBody OrderDto payload){
        return orderService.createOrder(payload);
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<BaseResponseModel> updateOrderStatus(@PathVariable("order_id") Long orderId,
                                                               @Valid @RequestBody OrderUpdateDto payload){
        return orderService.updateOrderStatus(orderId,payload);
    }

    public ResponseEntity<BaseResponseModel> deleteOrder(@PathVariable("order_id") Long orderId){
        return orderService.deleteOrder(orderId);
    }
}
