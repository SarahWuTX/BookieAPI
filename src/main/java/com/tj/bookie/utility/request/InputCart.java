package com.tj.bookie.utility.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputCart {
    @NotEmpty
    private String wx_id;

    @NotNull
    private Integer bookId;

    @NotNull
    @Range(min = 1)
    private Integer count;
}
