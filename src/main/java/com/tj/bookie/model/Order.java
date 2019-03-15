package com.tj.bookie.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private Integer userId;

    @NotNull
    private Float totalPrice;

    private Integer status;

    @NotEmpty
    private String receiverName;

    @NotEmpty
    private String receiverPhone;

    @NotEmpty
    private String receiverProvince;

    @NotEmpty
    private String receiverAddress;

    @Column(insertable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp orderTime;

}
