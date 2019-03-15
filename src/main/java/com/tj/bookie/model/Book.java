package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String isbn;

    @NotEmpty
    private String name;

    @NotNull
    private Float price;

    private String coverUrl;

    private Float discount;

    private String author;

    private String publisher;

    private String description;

}
