package com.tj.bookie.utility.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    private String name;

    //(mappedBy="categories")
    @ManyToMany(targetEntity = com.tj.bookie.utility.model.Book.class)
    @JoinTable(
            name = "t_book_category",
            joinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private List<Book> books;
}
