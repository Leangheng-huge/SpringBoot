package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.order.OrderDto;

import com.chiikawa.demo.DTO.order.OrderResponseDto;
import com.chiikawa.demo.DTO.order.OrderUpdateDto;
import com.chiikawa.demo.Exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Mapper.OrderMapper;
import com.chiikawa.demo.entity.Order;

import com.chiikawa.demo.repository.OrderRepository;
import com.chiikawa.demo.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockManagementService stockManagementService;

    public List<OrderResponseDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return mapper.toResponseDtoList(orders);
    }

    @Transactional
    public void createOrder(OrderDto payload) {
        // reserve stock for order
        stockManagementService.reserveStockForOrder(payload.getOrderItems());

        // create order entity
        Order order = mapper.toEntity(payload);

        orderRepository.save(order);
    }

    public OrderResponseDto updateOrderStatus(Long orderId, OrderUpdateDto payload) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("order not found with id: " + orderId);
                });

        mapper.updateEntityFromDto(existingOrder,payload);
        orderRepository.save(existingOrder);

        return mapper.toResponseDto(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        if(!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("order not found with id: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }
}