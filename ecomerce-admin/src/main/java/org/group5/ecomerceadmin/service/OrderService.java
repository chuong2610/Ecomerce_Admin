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
        System.out.println("=== ORDER SERVICE CREATE ORDER ===");
        System.out.println("Request: " + request);
        System.out.println("Account ID: " + request.getAccountId());
        System.out.println("Items: " + request.getItems());

        Account customer = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account customer not found"));
        System.out.println("Found customer: " + customer.getFullName());

        Order order = new Order();
        order.setAccount(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(request.getStatus());
        order.setActive(true);
        System.out.println("Created order object with status: " + request.getStatus());

        double total = 0;
        List<ProductOrder> productOrders = new ArrayList<>();

        for (OrderItemRequest item : request.getItems()) {
            System.out.println("Processing item - Product ID: " + item.getProductId() + ", Quantity: " + item.getQuantity());

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            System.out.println("Found product: " + product.getName() + " (Stock: " + product.getQuantity() + ")");

            // Kiểm tra số lượng tồn kho
            if (item.getQuantity() > product.getQuantity()) {
                String errorMsg = "Không đủ hàng tồn kho cho sản phẩm '" + product.getName() +
                    "'. Yêu cầu: " + item.getQuantity() + ", Tồn kho: " + product.getQuantity();
                System.out.println("INVENTORY ERROR: " + errorMsg);
                throw new RuntimeException(errorMsg);
            }

            double itemPrice = product.getPrice() * item.getQuantity();
            total += itemPrice;
            System.out.println("Item price: " + itemPrice + ", Running total: " + total);

            ProductOrder po = new ProductOrder(product, order, item.getQuantity(), itemPrice);
            productOrders.add(po);
        }

        order.setTotalPrice(total);
        order.setProductOrders(productOrders);
        System.out.println("Saving order with total: " + total + " and " + productOrders.size() + " items");

        Order saved = orderRepository.save(order);
        System.out.println("Order saved with ID: " + saved.getId());

        // Cập nhật số lượng tồn kho sau khi tạo đơn hàng thành công
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow();
            int newQuantity = product.getQuantity() - item.getQuantity();
            System.out.println("Updating product " + product.getName() + " quantity from " + product.getQuantity() + " to " + newQuantity);
            product.setQuantity(newQuantity);
            productRepository.save(product);
        }

        OrderResponse result = new OrderResponse(saved);
        System.out.println("=== ORDER SERVICE COMPLETED ===");
        return result;
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

    public OrderResponse updateOrder(String orderId, OrderRequest request) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Update status
        order.setStatus(request.getStatus());

        // Update product quantities if items are provided
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            // Clear existing product orders
            order.getProductOrders().clear();

            // Add updated product orders
            double totalPrice = 0.0;
            for (var itemRequest : request.getItems()) {
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                ProductOrder productOrder = new ProductOrder();
                productOrder.setOrder(order);
                productOrder.setProduct(product);
                productOrder.setQuantity(itemRequest.getQuantity());
                productOrder.setPrice(itemRequest.getPrice().doubleValue());

                order.getProductOrders().add(productOrder);
                totalPrice += itemRequest.getPrice().doubleValue() * itemRequest.getQuantity();
            }
            order.setTotalPrice(totalPrice);
        }

        return new OrderResponse(orderRepository.save(order));
    }

    public void deleteById(String orderId) {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setActive(false);
        orderRepository.save(order);
    }
}
