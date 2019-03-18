package com.tj.bookie.utility.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    private Integer count;

    @Column(insertable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;

}
