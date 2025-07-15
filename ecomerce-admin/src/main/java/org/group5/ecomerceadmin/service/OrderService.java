package org.group5.ecomerceadmin.service;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.entity.Product;
import org.group5.ecomerceadmin.entity.ProductOrder;
import org.group5.ecomerceadmin.enums.OrderStatus;
import org.group5.ecomerceadmin.payload.request.OrderFilterRequest;
import org.group5.ecomerceadmin.payload.request.OrderItemRequest;
import org.group5.ecomerceadmin.payload.request.OrderRequest;
import org.group5.ecomerceadmin.payload.response.OrderResponse;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.group5.ecomerceadmin.repository.OrderRepository;
import org.group5.ecomerceadmin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest request) {
        Account customer = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account customer not found"));

        Order order = new Order();
        order.setAccount(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setActive(true);

        double total = 0;
        List<ProductOrder> productOrders = new ArrayList<>();
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double itemPrice = product.getPrice() * item.getQuantity();
            total += itemPrice;

            ProductOrder po = new ProductOrder(product, order, item.getQuantity(), itemPrice);
            productOrders.add(po);
        }

        order.setTotalPrice(total);
        order.setProductOrders(productOrders);
        Order saved = orderRepository.save(order);
        return new OrderResponse(saved);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findByIsActiveTrue().stream().map(OrderResponse::new).toList();
    }

    public List<OrderResponse> getOrdersByAccountStatusAndDate(OrderFilterRequest request) {
        LocalDate date = request.getDate();
        Long accountId = request.getAccountId();
        OrderStatus status = request.getStatus();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        return orderRepository.findByAccountIdAndIsActiveTrueAndStatusAndOrderDateBetween(accountId, status, start, end)
                .stream().map(OrderResponse::new).toList();
    }

    public OrderResponse updateStatus(String orderId, OrderRequest request) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(request.getStatus());
        return new OrderResponse(orderRepository.save(order));
    }

    public void deleteById(String orderId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setActive(false);
        orderRepository.save(order);
    }
}
