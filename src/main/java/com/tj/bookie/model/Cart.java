package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer count;

    @Column(insertable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;

}
