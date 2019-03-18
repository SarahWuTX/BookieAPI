package com.tj.bookie.utility.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

//    private Integer bookId;
//
//    private Integer userId;

    private Integer count;
//
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order orderId;

    @JsonIgnore
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

}