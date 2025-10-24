package com.chiikawa.demo.service;

import com.chiikawa.demo.DTO.base.PaginatedResponse;
import com.chiikawa.demo.DTO.order.OrderDto;

import com.chiikawa.demo.DTO.order.OrderResponseDto;
import com.chiikawa.demo.DTO.order.OrderUpdateDto;
import com.chiikawa.demo.common.config.ApplicationConfiguration;
import com.chiikawa.demo.exception.model.ResourceNotFoundException;
import com.chiikawa.demo.Mapper.OrderMapper;
import com.chiikawa.demo.entity.Order;

import com.chiikawa.demo.repository.OrderRepository;
import com.chiikawa.demo.repository.StockRepository;
import com.chiikawa.demo.service.mail.NotificationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockManagementService stockManagementService;

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private NotificationService notificationService;

    public PaginatedResponse listOrdersWithPagination(Pageable pageable) {
        Page<Order> orderPages = orderRepository.findAll(pageable);
        Page<OrderResponseDto> orderPagesDto = orderPages.map(order -> mapper.toResponseDto(order));

        return PaginatedResponse.from(orderPagesDto,appConfig.getPagination().getUrlByResource("order"));
    }

    public List<OrderResponseDto> listOrders() {
        List<Order> orders = orderRepository.findAll();

        return mapper.toResponseDtoList(orders);
    }

    @Transactional
    public void createOrder(OrderDto payload) {
        String threadName = Thread.currentThread().getName();

        log.info("[SYNC-ORDER] Creating new order | Thread: {}", threadName);
        // reserve stock for order
        stockManagementService.reserveStockForOrder(payload.getOrderItems());

        // create order entity
        Order order = mapper.toEntity(payload);

        orderRepository.save(order);

        log.info("[SYNC-ORDER] Order created successfully with Order: {} | Thread: {}",order.getId(), threadName);
        log.info("[SYNC-ORDER] Trigger send notification asynchronously for Order: {} | Thread: {}",order.getId(), threadName );

        notificationService.sendOrderConfirmationNotification(order.getId(),"Your order has been completed");

        log.info("[SYNC-ORDER-COMPLETED] Completed order and triggered send notification");
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