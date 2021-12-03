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
@Table(name = "tbl_Users", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId"})})
public class UserDTO {
    private String id;
    private String password;
    private String fullName;
    private RoleDTO role;
    private String phone;
    private String address;

    public UserDTO() {
    }
    public UserDTO(String id, String password, String fullName, RoleDTO role) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public UserDTO(String id, String password, String fullName, RoleDTO role, String phone, String address) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }

    @Column(name="phone",length = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name = "address",length = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Id
    @Column(name = "userId")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "password", length = 50, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "fullName", length = 200, nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
