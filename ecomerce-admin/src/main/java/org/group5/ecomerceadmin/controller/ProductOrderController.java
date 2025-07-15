package org.group5.ecomerceadmin.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.service.ProductOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequiredArgsConstructor
@RequestMapping("/api/product-orders")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity getByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(productOrderService.getProductOrdersByOrderId(orderId));
    }

}
