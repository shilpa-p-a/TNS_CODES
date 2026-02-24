package com.shoppingmall.controller;

import com.shoppingmall.entity.*;
import com.shoppingmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private GuestBookEntryRepository guestBookRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ShopOwnerRepository ownerRepository;

    @GetMapping("/shops/pending")
    public List<Shop> getPendingShops() {
        return shopRepository.findByShopStatus("PENDING");
    }

    @PutMapping("/shops/{shopId}/approve")
    public String approveShop(@PathVariable Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        shop.setShopStatus("APPROVED");
        shopRepository.save(shop);
        return "Shop Approved";
    }

    @PutMapping("/shops/{shopId}/reject")
    public String rejectShop(@PathVariable Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        shop.setShopStatus("REJECTED");
        shopRepository.save(shop);
        return "Shop Rejected";
    }

    @PostMapping("/guestbook")
    public GuestBookEntry addGuestBookEntry(@RequestBody GuestBookEntry entry) {
        entry.setTimestamp(LocalDateTime.now());
        return guestBookRepository.save(entry);
    }

    @GetMapping("/guestbook")
    public List<GuestBookEntry> viewGuestBook() {
        return guestBookRepository.findAll();
    }

    @PostMapping("/notifications/{shopId}")
    public Notification sendNotification(@PathVariable Long shopId, @RequestBody String message) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        Notification note = new Notification();
        note.setShop(shop);
        note.setMessage(message);
        note.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(note);
    }
}
