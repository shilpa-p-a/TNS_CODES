package com.shoppingmall.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int shopId;
    private String shopName;
    private String shopCategory;
    private String shopStatus; // PENDING, APPROVED, REJECTED
    private String leaseStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mall_id")
    private Mall mall;

    @JsonIgnore
    @OneToOne(mappedBy = "shop", cascade = CascadeType.ALL)
    private ShopOwner shopOwner;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Employee> shopEmployees;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Item> items;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<OrderDetails> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(String leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public ShopOwner getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(ShopOwner shopOwner) {
        this.shopOwner = shopOwner;
    }

    public List<Employee> getShopEmployees() {
        return shopEmployees;
    }

    public void setShopEmployees(List<Employee> shopEmployees) {
        this.shopEmployees = shopEmployees;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
