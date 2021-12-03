/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.dtos;

import javax.persistence.*;

/**
 * @author SE140018
 */
@Entity
@Table(name = "tbl_Drinks", uniqueConstraints = @UniqueConstraint(columnNames = {"drinkId"}))
public class DrinkDTO {
    private String id;
    private String url;
    private String name;
    private String description;
    private Long price;
    private Integer quantity;
    private Boolean status;

    public DrinkDTO() {
    }

    public DrinkDTO(String id, String url, String name, String description, Long price, Integer quantity, Boolean status) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    @Id
    @Column(name = "drinkId", length = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "url", length = 200, nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "drinkName", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 200, nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price", nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "status")
    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
