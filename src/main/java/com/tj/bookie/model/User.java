package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@Entity
@Table(name= "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String wxId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    private Date birthday;

    private String province;

    public User() {
        this.birthday = null;
    }

    public User(String wxId, String name, String phone, Date birthday, String province) {
        this.wxId = wxId;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.province = province;
    }

}
