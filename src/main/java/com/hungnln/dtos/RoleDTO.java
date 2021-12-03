package com.hungnln.dtos;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_Roles")
public class RoleDTO {
    private int roleId;
    private String roleName;
    private Set<UserDTO> users;

    @Id
    @Column(name = "roleId")
    @GeneratedValue
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int role) {
        this.roleId = role;
    }

    @Column(name = "roleName", length = 10, nullable = false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }
}
