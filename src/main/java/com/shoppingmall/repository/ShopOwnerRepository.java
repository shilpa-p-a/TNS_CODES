package com.shoppingmall.repository;

import com.shoppingmall.entity.ShopOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOwnerRepository extends JpaRepository<ShopOwner, Integer> {
    ShopOwner findByUserId(int userId);
}
