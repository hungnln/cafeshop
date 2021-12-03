/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.dtos;



import javax.persistence.*;
import java.io.Serializable;

/**
 * @author SE140018
 */
@Entity()
@Table(name = "tbl_OrderDetails")
public class OrderDetailDTO implements Serializable {
    OrderDTO orderDTO;
    DrinkDTO drinkDTO;
    Integer quantity;
    String description;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(OrderDTO orderDTO, DrinkDTO drinkDTO, Integer quantity, String description) {
        this.orderDTO = orderDTO;
        this.drinkDTO = drinkDTO;
        this.quantity = quantity;
        this.description = description;
    }
    @Id
    @JoinColumn(name = "HD")
    @ManyToOne()
    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }
    @Id
    @ManyToOne()
    @JoinColumn(name = "drinkId")
    public DrinkDTO getDrinkDTO() {
        return drinkDTO;
    }

    public void setDrinkDTO(DrinkDTO drinkDTO) {
        this.drinkDTO = drinkDTO;
    }
    @Column(name = "quantity",nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "description",length = 200,nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
