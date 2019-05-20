package com.weltec.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;



import javax.persistence.*;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-02 13:13
 */
@Entity
@Data
@Table(name = "user")
public class User {
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    private String userID;
    @Column(name="user_name")
    private String username;
    private String password;
    private String email;
    private Integer userType; // 1 as admin 0 as customer

}

