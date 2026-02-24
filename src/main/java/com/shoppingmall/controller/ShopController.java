package com.shoppingmall.controller;

import com.shoppingmall.entity.*;
import com.shoppingmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public Shop createShopRequest(@RequestBody Shop shop) {
        shop.setShopStatus("PENDING");
        return shopRepository.save(shop);
    }

    @GetMapping
    public List<Shop> getAllApprovedShops() {
        return shopRepository.findByShopStatus("APPROVED");
    }

    @PostMapping("/{shopId}/items")
    public Item addItemToShop(@PathVariable Long shopId, @RequestBody Item item) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        item.setShop(shop);
        return itemRepository.save(item);
    }
}
