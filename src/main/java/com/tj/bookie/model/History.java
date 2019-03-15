package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

    @NotNull
    private Integer count;

    @NotNull
    private Integer orderId;

}