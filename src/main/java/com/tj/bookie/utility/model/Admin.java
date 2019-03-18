package com.tj.bookie.utility.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;
import org.hibernate.annotations.NaturalId;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    private String name;

    private String password;

}
