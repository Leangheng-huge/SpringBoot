package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.base.PaginatedResponse;
import com.chiikawa.demo.DTO.base.Response;
import com.chiikawa.demo.DTO.order.OrderDto;
import com.chiikawa.demo.DTO.order.OrderResponseDto;
import com.chiikawa.demo.DTO.order.OrderUpdateDto;

import com.chiikawa.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/paginated")
    public ResponseEntity<Response> listOrdersPaginated(@PageableDefault(size = 10, page = 0,sort = "id",
    direction = Sort.Direction.DESC) Pageable pageable){
        PaginatedResponse<OrderResponseDto> orders = orderService.listOrdersWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success(
                                "200",
                                "success",
                                "successfully retrieved orders",
                                orders));
    }


    @GetMapping
    public ResponseEntity<Response> listOrders() {
        List<OrderResponseDto> orders = orderService.listOrders();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully retrieved orders",orders));
    }

    @PostMapping
    public ResponseEntity<Response> placeOrder(@Valid @RequestBody OrderDto payload) {
        orderService.createOrder(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("201","success","successfully created order"));
    }

    @PatchMapping("/{order_id}")
    public ResponseEntity<Response> updateOrderStatus(@PathVariable("order_id") Long orderId,@Valid @RequestBody OrderUpdateDto payload) {
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId,payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","successfully updated order",updatedOrder));
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable("order_id") Long orderId) {
        orderService.deleteOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success","successfully deleted order: " + orderId));
    }
}
