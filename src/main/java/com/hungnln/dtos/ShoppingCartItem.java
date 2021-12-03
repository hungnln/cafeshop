package com.hungnln.dtos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author SE140018
 */
public class ShoppingCartItem {
    private DrinkDTO drink;
    private int quantity;
    private String description;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(DrinkDTO drink, int quantity, String description) {
        this.drink = drink;
        this.quantity = quantity;
        this.description = description;
    }

    public DrinkDTO getDrink() {
        return drink;
    }

    public void setDrink(DrinkDTO drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
