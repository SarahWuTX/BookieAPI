package com.tj.bookie.utility.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.Date;


@Data
@AllArgsConstructor
@Entity
@Table(name= "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    private String wxId;

    private String name;

    private String phone;

    private Date birthday = null;

    private String province = null;

    private String city = null;

    private String district = null;

    private String address = null;

//    @OneToMany(mappedBy = "userId")
//    @JsonIgnore
//    private Collection<Cart> carts;
//
//    @OneToMany(mappedBy = "userId")
//    @JsonIgnore
//    private Collection<History> histories;

    public User() {
        this.birthday = null;
    }

    public User(String wxId, String name, String phone, Date birthday, String province, String city, String district, String address) {
        this.wxId = wxId;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
    }

}
