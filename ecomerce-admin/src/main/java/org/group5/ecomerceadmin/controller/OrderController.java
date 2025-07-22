package org.group5.ecomerceadmin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.payload.request.OrderFilterRequest;
import org.group5.ecomerceadmin.payload.request.OrderRequest;
import org.group5.ecomerceadmin.payload.response.OrderResponse;
import org.group5.ecomerceadmin.service.OrderService;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.service.ProductService;
import org.group5.ecomerceadmin.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import org.group5.ecomerceadmin.payload.request.OrderItemRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final AccountRepository accountRepository;
    private final ProductService productService;

    @GetMapping("/order-add")
    public String showOrderAddPage(Model model) {
        List<Account> customers = accountRepository.findByRole(Role.CUSTOMER);
        List<ProductDTO> products = productService.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        return "order-add";
    }

    @GetMapping("/order-list")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order-list";
    }

    @GetMapping("/test-create")
    public String testCreateOrder() {
        // Test method to create a sample order
        try {
            System.out.println("=== MANUAL TEST CREATE ORDER ===");
            OrderRequest testRequest = new OrderRequest();
            testRequest.setAccountId(1L); // Assuming account ID 1 exists
            testRequest.setStatus(org.group5.ecomerceadmin.enums.OrderStatus.PENDING);

            // Create a test product item
            OrderItemRequest testItem = new OrderItemRequest();
            testItem.setProductId("PRODUCT001"); // Assuming this product exists
            testItem.setQuantity(1);

            List<OrderItemRequest> items = new ArrayList<>();
            items.add(testItem);
            testRequest.setItems(items);

            OrderResponse result = orderService.createOrder(testRequest);
            System.out.println("Test order created successfully: " + result);
            return "redirect:/orders/order-list";
        } catch (Exception e) {
            System.out.println("Test order creation failed: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/orders/order-add";
        }
    }

    @PostMapping("/order-add")
    public String createOrder(
        @ModelAttribute OrderRequest request,
        @RequestParam("selectedProducts") String selectedProductsJson,
        Model model
    ) throws Exception {
        // Debug: log dữ liệu nhận được
        System.out.println("=== CREATE ORDER DEBUG ===");
        System.out.println("OrderRequest: " + request);
        System.out.println("Account ID: " + request.getAccountId());
        System.out.println("Order Status: " + request.getStatus());
        System.out.println("selectedProductsJson: " + selectedProductsJson);

        if (selectedProductsJson == null || selectedProductsJson.trim().isEmpty()) {
            System.out.println("ERROR: selectedProductsJson is null or empty");
            model.addAttribute("error", "Vui lòng thêm sản phẩm vào đơn hàng!");
            return showOrderAddPage(model);
        }

        List<OrderItemRequest> items;
        try {
            items = new ObjectMapper().readValue(selectedProductsJson, new TypeReference<List<OrderItemRequest>>() {});
            System.out.println("Parsed items: " + items);
            System.out.println("Items count: " + (items != null ? items.size() : 0));
        } catch (Exception ex) {
            System.out.println("ERROR parsing JSON: " + ex.getMessage());
            model.addAttribute("error", "Dữ liệu sản phẩm không hợp lệ!");
            ex.printStackTrace();
            return showOrderAddPage(model);
        }

        if (items == null || items.isEmpty()) {
            System.out.println("ERROR: items is null or empty after parsing");
            model.addAttribute("error", "Vui lòng thêm sản phẩm vào đơn hàng!");
            return showOrderAddPage(model);
        }

        request.setItems(items);
        System.out.println("Final OrderRequest before service call: " + request);

        try {
            OrderResponse result = orderService.createOrder(request);
            System.out.println("Order created successfully: " + result);
        } catch (Exception ex) {
            System.out.println("ERROR creating order: " + ex.getMessage());
            model.addAttribute("error", "Không thể tạo đơn hàng: " + ex.getMessage());
            ex.printStackTrace();
            return showOrderAddPage(model);
        }

        System.out.println("=== CREATE ORDER SUCCESS - REDIRECTING ===");
        return "redirect:/orders/order-list";
    }

    @GetMapping("/filter")
    public String filter(@ModelAttribute OrderFilterRequest request, Model model) {
        model.addAttribute("orders", orderService.getOrdersByAccountStatusAndDate(request));
        return "order-list";
    }

    @PostMapping("/update/{orderId}")
    public String updateOrder(@PathVariable String orderId, @ModelAttribute OrderRequest request) {
        orderService.updateOrder(orderId, request);
        return "redirect:/orders/order-list";
    }

    @GetMapping("/delete/{orderId}")
    public String delete(@PathVariable String orderId) {
        orderService.deleteById(orderId);
        return "redirect:/orders/order-list";
    }


}
