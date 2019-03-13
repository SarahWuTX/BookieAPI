package com.tj.bookie.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_user")
public class User {

    @Id
    @NonNull
    private Integer id;

    @NonNull
    private String wxId;

    private String name;

    private String phone;

    private Integer role;

}
