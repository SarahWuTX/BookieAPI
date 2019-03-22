package com.tj.bookie.utility.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User userId;

    private Float totalPrice;

    private Integer status;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private Float deliveryFee;

    @Column(insertable = false)
    private Timestamp orderTime;

    @OneToMany(mappedBy = "orderId")
    private Collection<History> orderList;

    @Transient
    private Integer totalCount;

    @Transient
    private String orderId;

}
