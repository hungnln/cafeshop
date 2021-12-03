/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.dtos;


import javax.persistence.*;
import java.util.Set;

/**
 * @author SE140018
 */
@Entity
@Table(name = "tbl_Orders", uniqueConstraints = @UniqueConstraint(columnNames = {"HD"}))
public class OrderDTO {
    private String HD;
    private String date;
    private long total;
    private int status;
    private UserDTO userDTO;
    private Set<OrderDetailDTO> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(String  HD, String date, long total, UserDTO userDTO, int status) {
        this.HD = HD;
        this.date = date;
        this.total = total;
        this.userDTO = userDTO;
        this.status = status;
    }
    //    public OrderDTO(int HD, int quantity, String date, String description, long total, UserDTO userDTO, DrinkDTO drinkDTO) {
//        this.HD = HD;
//        this.quantity = quantity;
//        this.date = date;
//        this.description = description;
//        this.total = total;
//        this.userDTO = userDTO;
//        this.drinkDTO = drinkDTO;
//    }

    @Id
    @Column(name = "HD")
    public String getHD() {
        return HD;
    }

    public void setHD(String HD) {
        this.HD = HD;
    }

    @Column(name = "date", nullable = false)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "total", nullable = false)
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


    @OneToMany(mappedBy = "orderDTO", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }


    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "HD=" + HD +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", status=" + status +
                ", userDTO=" + userDTO +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
