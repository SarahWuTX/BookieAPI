package com.tj.bookie.utility.request;

import com.tj.bookie.utility.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputOrder {
    @NotEmpty
    private String wxId;

    @NotEmpty
    private String receiverName;

    @NotEmpty
    private String receiverPhone;

    @NotEmpty
    private List<String> receiverAddress;

    @NotEmpty
    private List<Integer> cartList;
}
