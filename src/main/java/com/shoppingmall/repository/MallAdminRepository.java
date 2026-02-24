package com.shoppingmall.repository;

import com.shoppingmall.entity.MallAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MallAdminRepository extends JpaRepository<MallAdmin, Integer> {
    MallAdmin findByUserId(int userId);
}
