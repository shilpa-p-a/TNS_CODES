package com.shoppingmall.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Mall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mallName;
    private String location;
    private String categories;

    @OneToOne(mappedBy = "mall", cascade = CascadeType.ALL)
    private MallAdmin mallAdmin;

    @OneToMany(mappedBy = "mall", cascade = CascadeType.ALL)
    private List<Shop> shops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public MallAdmin getMallAdmin() {
        return mallAdmin;
    }

    public void setMallAdmin(MallAdmin mallAdmin) {
        this.mallAdmin = mallAdmin;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
