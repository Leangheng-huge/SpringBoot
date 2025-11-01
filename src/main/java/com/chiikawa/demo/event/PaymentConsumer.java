package com.chiikawa.demo.event;

import com.chiikawa.demo.common.enums.OrderStatus;
import com.chiikawa.demo.entity.Order;
import com.chiikawa.demo.event.model.PaymentEvent;
import com.chiikawa.demo.repository.OrderRepository;
import com.chiikawa.demo.service.kafka.ProducerService;
import com.chiikawa.demo.service.mail.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PaymentConsumer {
    @Autowired
    private ProducerService<PaymentEvent> producerService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(
            topics = "payment.event",
            groupId = "payment-group",
            containerFactory = "paymentContainerFactory"
    )
    public void consume(PaymentEvent event) {
        log.info("[PAYMENT_EVENT] Processing payment event: {}", event);

        try {
            Optional<Order> existingOrder = orderRepository.findById(event.getOrderId());

            if (existingOrder.isEmpty()) {
                log.error("Order not found: {}", event.getOrderId());
                return;
            }

            Order order = existingOrder.get();

            // Update order status based on payment status
            if (event.getStatus()) {
                order.setStatus(OrderStatus.SUCCESS.name());
                log.info("Order {} marked as SUCCESS", event.getOrderId());
            } else {
                order.setStatus(OrderStatus.FAILED.name());
                log.info("Order {} marked as FAILED due to payment failure", event.getOrderId());
            }

            orderRepository.save(order);

            // Optional: Send notification about order status change
            // notificationService.sendOrderStatusUpdate(order);

        } catch (Exception e) {
            log.error("Error processing payment event for order {}: {}", event.getOrderId(), e.getMessage(), e);
            // Optional: You might want to send the event to a dead letter queue here
        }
    }
}
