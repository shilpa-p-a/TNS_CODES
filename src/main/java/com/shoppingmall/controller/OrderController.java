package com.shoppingmall.controller;

import com.shoppingmall.entity.*;
import com.shoppingmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderDetailsRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShopRepository shopRepository;

    @PostMapping("/{customerId}/shop/{shopId}")
    public OrderDetails createOrder(
            @PathVariable Integer customerId,
            @PathVariable Long shopId,
            @RequestBody OrderDetails orderParams) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        orderParams.setCustomer(customer);
        orderParams.setShop(shop);
        orderParams.setDateOfPurchase(LocalDateTime.now());

        return orderRepository.save(orderParams);
    }
}
