package org.group5.ecomerceadmin.controller;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.payload.request.OrderFilterRequest;
import org.group5.ecomerceadmin.payload.request.OrderRequest;
import org.group5.ecomerceadmin.payload.response.OrderResponse;
import org.group5.ecomerceadmin.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/filter")
    public ResponseEntity filter(@RequestBody OrderFilterRequest request) {
        return ResponseEntity.ok(orderService.getOrdersByAccountStatusAndDate(request));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity updateStatus(@PathVariable String orderId, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, request));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity delete(@PathVariable String orderId) {
        orderService.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }


}
