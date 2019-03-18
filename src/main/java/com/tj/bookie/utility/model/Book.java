package com.tj.bookie.utility.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    private String isbn;

    private String name;

    private String coverUrl;

    private Float price;

    private Float discount;

    private Integer stock;

    private String author;

    private String publisher;

    private String description;

    private Integer sales;

    @Transient
    private String category;

//    @OneToMany(mappedBy = "bookId")
//    private Collection<Cart> carts;
//
//    @OneToMany(mappedBy = "bookId")
//    @JsonIgnore
//    private Collection<History> histories;

//    @ManyToMany(targetEntity = com.tj.bookie.utility.model.Category.class)
//    @JoinTable(
//            name = "t_book_category",
//            joinColumns = @JoinColumn(
//                    name = "book_id",
//                    referencedColumnName = "id"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "category_id",
//                    referencedColumnName = "id"
//            )
//    )
//    @JsonIgnore
//    private List<Category> categories;

}
