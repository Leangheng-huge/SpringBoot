package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.OrderDto;
import com.chiikawa.demo.Mapper.OrderMapper;
import com.chiikawa.demo.entity.Order;
import com.chiikawa.demo.model.BaseResponseModel;
import com.chiikawa.demo.model.BaseResponseWithDataModel;
import com.chiikawa.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<BaseResponseModel> createOrder(OrderDto payload){
        Order order = mapper.toEntity(payload);

        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created order"));
    }
}
