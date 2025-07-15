package org.group5.ecomerceadmin.service;

import org.group5.ecomerceadmin.entity.ProductOrder;
import org.group5.ecomerceadmin.payload.response.ProductOrderResponse;
import org.group5.ecomerceadmin.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    public List<ProductOrderResponse> getProductOrdersByOrderId(Long orderId) {
        return productOrderRepository
                .findByOrder_Id(orderId)
                .stream().map(ProductOrderResponse::new).toList();
    }
}
