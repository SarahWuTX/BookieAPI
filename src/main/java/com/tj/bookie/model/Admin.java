package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

}
